package com.nobugs.parthenon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.UsuarioFirebase;
import com.nobugs.parthenon.model.Usuários.Usuario;


public class LoginActivity extends AppCompatActivity {

    private Button logarUsuarioButton, verificarCPFbutton;
    private TextInputEditText cpfUsuarioLogin, senhaUsuarioLogin;
    private FirebaseAuth autenticar;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private AlertDialog alerta;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuarioLogado();
        avisoEntrada();
        inicializarComponentes();
        autenticar = ConfiguracaoFirebase.getFirebaseAutenticacao();
        progressBar = findViewById(R.id.progressBar);



        verificarCPFbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cpfInserido = cpfUsuarioLogin.getText().toString();


                DatabaseReference valorCPF = databaseReference.child("listaCPFs").child(cpfUsuarioLogin.getText().toString());
                valorCPF.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        String retorno = dataSnapshot.getValue().toString().substring(8, dataSnapshot.getValue().toString().length()-1);

                        if(retorno.equals("0")){
                            Intent permitirCadastro = new Intent(LoginActivity.this, CadastroActivity.class).putExtra("cpf", cpfInserido);
                            startActivity(permitirCadastro);    }

                        else if(retorno.equals("3")){
                            //Para staff, palestrantes ou organizadores. Tava dando erro no if aí tive que dividir
                            //Separei o valor em 3 e 1 para facilitar visualização, mas da no mesmo.
                            Intent permitirCadastro = new Intent(LoginActivity.this, CadastroActivity.class).putExtra("cpf", "3");
                            startActivity(permitirCadastro);    }

                        else if(retorno.equals("1" )){
                            Toast.makeText(LoginActivity.this, "O CPF já foi cadastrado.", Toast.LENGTH_SHORT).show();
                            logarUsuarioButton.setVisibility(View.VISIBLE);
                            senhaUsuarioLogin.setVisibility(View.VISIBLE);
                            logarUsuarioButton.setVisibility(View.VISIBLE);
                            verificarCPFbutton.setVisibility(View.GONE);    }

                        else if (retorno.equals("2")){
                            // código escolar. A diferença é que não carrega o cpf
                                Intent permitirCadastro = new Intent(LoginActivity.this, CadastroActivity.class).putExtra("cpf", "");
                                startActivity(permitirCadastro);    }
                        else {
                            //Se não teve retorno, filho, fecha essa activity
                            finish();
                        }



                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        logarUsuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                logarUsuarioButton.setVisibility(View.INVISIBLE);
                processoLogin();
                progressBar.setVisibility(View.INVISIBLE);
                logarUsuarioButton.setVisibility(View.VISIBLE);

            }});


    }


    // Primeira fase do login: Recupera o email e chama o método de login de verdade.
    public void processoLogin(){
        if(!cpfUsuarioLogin.getText().toString().isEmpty()){
            if(!senhaUsuarioLogin.getText().toString().isEmpty()){

                DatabaseReference usuarioTentandoLogar = databaseReference.child("usuarios").child(cpfUsuarioLogin.getText().toString());
                usuarioTentandoLogar.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String retorno = dataSnapshot.getValue().toString();
                        String[] atributos = retorno.split(",");
                        String email = atributos[5].substring(7);
                        String status = atributos[6].substring(8, atributos[6].length()-1);
                        String senha = senhaUsuarioLogin.getText().toString();

                        Usuario usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setStatus(status);
                        usuario.setSenha(senha);
                        validacaoUsuario(usuario);
                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }}); }
            else{ Toast.makeText(LoginActivity.this, "Preencha a senha para efetuar Login.", Toast.LENGTH_SHORT).show(); }
        }else{ Toast.makeText(LoginActivity.this, "Preencha o CPF para efetuar Login.", Toast.LENGTH_SHORT).show(); }
    }

    //Segunda fase do login, faz o login propriamente dito.
    public void validacaoUsuario(final Usuario usuario){
        autenticar.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){
                    if(usuario.getStatus().equals("Visitante")){
                    startActivity( new Intent(LoginActivity.this, NavigationScreen.class) );
                    finish();   }
                    if(usuario.getStatus().equals("Organizador")){
                        startActivity( new Intent(LoginActivity.this, NavigationScreenAdmin.class) );
                    finish();   }

                    }
                else { String excecao = "";
                    try { throw task.getException(); }
                    catch ( FirebaseAuthInvalidUserException e ) { excecao = "Usuário não está cadastrado."; }
                    catch ( FirebaseAuthInvalidCredentialsException e ){ excecao = "E-mail e senha não correspondem a um usuário cadastrado"; }
                    catch (Exception e){ excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace(); }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show(); } }}); }//fecha o método e o else



    public void usuarioLogado() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            startActivity(new Intent(LoginActivity.this, NavigationScreen.class));
            finish();
        }
    }

    public void inicializarComponentes(){
        logarUsuarioButton = findViewById(R.id.buttonLogin);
        cpfUsuarioLogin = findViewById(R.id.cpfUsuarioLogin);
        senhaUsuarioLogin = findViewById(R.id.senhaUsuarioLogin);
        verificarCPFbutton = findViewById(R.id.verificarCPFbutton);

    }

    private void avisoEntrada() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Em Rede: Conheça a UFV.");

        builder.setMessage("Apenas aqueles que cadastraram podem acessar esse aplicativo. Caso seu cpf não tenha sido cadastrado, " +
                "o aplicativo será fechado.");
        builder.setPositiveButton("Entendido!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(LoginActivity.this, "Seja bem vindo!", Toast.LENGTH_SHORT).show();

            }
        });

        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

}
