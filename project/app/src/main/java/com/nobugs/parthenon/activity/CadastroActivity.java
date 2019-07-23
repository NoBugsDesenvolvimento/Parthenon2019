package com.nobugs.parthenon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.model.Usuários.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText editCPF, editEmail, editNome, editTelefone, editNascimento, editSenha, codigoValidar;
    private Usuario usuario;
    private FirebaseAuth autenticar;
    private Button btnCadastro;
    private TextInputLayout layoutValidar;
    private RadioButton radioStaff, radioPalestrante, radioOrganizador;
    private AlertDialog alerta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        avisoEntrada();

        inicializarComponentes();

        Intent intent = getIntent();
        final String cpf = intent.getStringExtra("cpf");
        editCPF.setText(cpf);


        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCadastro();
            }
        });


    }

    public void cadastrarUsuario(final Usuario usuario, final String cpf) {
        autenticar = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticar.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    try {

                        //Salvar dados no firebase
                        String idUsuario = task.getResult().getUser().getEmail();
                        usuario.setId(idUsuario);
                        usuario.salvar();

                        Toast.makeText(CadastroActivity.this, "Cadastro com sucesso", Toast.LENGTH_SHORT).show();

                        if (cpf != null) {
                            DatabaseReference referencia = FirebaseDatabase.getInstance().getReference().child("listaCPFs").child(cpf).child("estado");
                            referencia.setValue("1");
                        }

                        startActivity(new Intent(getApplicationContext(), NavigationScreen.class));
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "Por favor, digite um e-mail válido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Este conta já foi cadastrada";
                    } catch (Exception e) {
                        erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();


                }

            }
        });


    }

    public void verificarCadastro() {

        String cpf = editCPF.getText().toString();
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String telefone = editTelefone.getText().toString();
        String nascimento = editNascimento.getText().toString();
        String senha = editSenha.getText().toString();

        if (!nome.isEmpty()) {//dentro de nome
            if (!cpf.isEmpty()) { //dentro de cpf
                if (!email.isEmpty()) {//dentro de email
                    if (nascimento != null) {//dentro de nascimento
                        if (!senha.isEmpty()) {//Dentro de senha // Todos campos obrigatórios preenchidos
                            usuario = new Usuario();
                            usuario.setNome(nome);
                            usuario.setNomePesquisa(nome.toUpperCase());
                            usuario.setEmail(email);
                            usuario.setSenha(senha);
                            usuario.setCPF(cpf);
                            usuario.setStatus("Visitante");
                            usuario.setNascimento(nascimento);

                            if (telefone != null) {
                                usuario.setTelefone(telefone);
                            } //Termina cadastro de atributos do usuario e salva o telefone se ele estiver preenchido
                            // salva no banco de dados

                            if (radioOrganizador.isChecked() || radioPalestrante.isChecked() || radioStaff.isChecked()) {
                                if (radioOrganizador.isChecked()) {
                                    usuario.setStatus("Organizador");
                                } else if (radioStaff.isChecked()) {
                                    usuario.setStatus("STAFF");
                                } else {
                                    usuario.setStatus("Palestrante");
                                }

                                boolean verificacao = verificarKey(codigoValidar.getText().toString());
                                if (verificacao) {
                                    cadastrarUsuario(usuario, cpf);
                                } else {
                                    Toast.makeText(this, "Código de verificação inválido.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                cadastrarUsuario(usuario, cpf);
                            } // chama o método de caadastro
                        }//fecha o if de cadastro
                        else {
                            Toast.makeText(this, "Insira uma senha válida.", Toast.LENGTH_SHORT).show();
                        } //senha vazia

                    }//fecha o if nascimento
                    else {
                        Toast.makeText(this, "Preencha a data de nascimento", Toast.LENGTH_SHORT).show();
                    }//Nascimento vazio
                }//fecha o email
                else {
                    Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
                } //Email vazio.
            }//fecha o if nascimento
            else {
                Toast.makeText(this, "Preencha o cpf", Toast.LENGTH_SHORT).show();
            } //CPF vazio
        }//fecha o if do nome
        else {
            Toast.makeText(this, "Preencha o nome para efetuar cadastro", Toast.LENGTH_SHORT).show();
        } //Nome vazio
    }//Fecha o método de cadastro


    public boolean verificarKey(String codigoValidar) {
        boolean resultado = false;
        if (radioOrganizador.isChecked() && codigoValidar.equals(getString(R.string.key_organizador))) {
            resultado = true;
        }
        if (radioStaff.isChecked() && codigoValidar.equals(getString(R.string.key_staff))) {
            resultado = true;
        }
        if (radioPalestrante.isChecked() && codigoValidar.equals(getString(R.string.key_palestrante))) {
            resultado = true;
        }
        return resultado;
    }


    public void revelarCodigo(View view) {
        layoutValidar.setVisibility(View.VISIBLE);
    }

    public void inicializarComponentes() {
        editCPF = findViewById(R.id.editCpfCadastro);
        editEmail = findViewById(R.id.editEmailCadastro);
        editNascimento = findViewById(R.id.editNascimentoCadastro);
        editNome = findViewById(R.id.editNomeCadastro);
        editTelefone = findViewById(R.id.editTelefoneCadastro);
        editSenha = findViewById(R.id.editSenhaCadastro);
        btnCadastro = findViewById(R.id.buttonCadastrar);
        codigoValidar = findViewById(R.id.codigoValidar);
        layoutValidar = findViewById(R.id.layoutValidar);
        radioOrganizador = findViewById(R.id.radioOrganizador);
        radioPalestrante = findViewById(R.id.radioPalestrante);
        radioStaff = findViewById(R.id.radioStaff);
        editCPF.requestFocus();
    }

    private void avisoEntrada() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção ao Cadastro");
        builder.setMessage("Visitantes, não marquem nenhuma das opções do menu cinza. São apenas para a Equipe colaboradora do evento."
        );
        builder.setPositiveButton("Entendido!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(CadastroActivity.this, "Seja bem vindo!", Toast.LENGTH_SHORT).show();

            }
        });

        alerta = builder.create();
        //Exibe
        alerta.show();
    }
}


