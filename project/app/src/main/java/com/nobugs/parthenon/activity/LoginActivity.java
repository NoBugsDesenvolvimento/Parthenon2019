package com.nobugs.parthenon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.model.Usuários.Usuario;

public class LoginActivity extends AppCompatActivity {

    private Button btnEmail, btnTeste;
    private TextInputEditText emailLogin, senhaLogin;
    private FirebaseAuth autenticar;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("cpfs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticar = ConfiguracaoFirebase.getFirebaseAutenticacao();
        inicializarComponentes();
        //usuarioLogado();

        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String numeroCPFUsuario  = emailLogin.getText().toString();
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(numeroCPFUsuario)) {

                        if( dataSnapshot.child(numeroCPFUsuario).toString().equals("0")){
                        //Salvamos como 0 os cpfs da lista que foram salvos, mas ainda não foram cadastrados.

                        Intent i = new Intent(LoginActivity.this, CadastroActivity.class).putExtra("cpf", numeroCPFUsuario);
                        startActivity(i);}

                        else{
                            //Nesse caso, alteramos o valor do cpf, então ele já foi cadastrado.
                            senhaLogin.setVisibility(View.VISIBLE);
                            btnTeste.setVisibility(View.INVISIBLE);
                            btnEmail.setVisibility(View.VISIBLE); }
                    }

                    else{   Toast.makeText(LoginActivity.this, "Desculpe, mas seu CPF não foi cadastrado em nossa base de dados.", Toast.LENGTH_SHORT).show(); }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarAutenticacaoUsuario();
            }
        });




    }
       public void inicializarComponentes(){
    btnEmail = findViewById(R.id.loginEmail);
    btnTeste = findViewById(R.id.buttonTeste);
    emailLogin = findViewById(R.id.textEmailLogin);

    }


    public void validacaoUsuario(Usuario usuario){

        autenticar.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful() ){
                    startActivity( new Intent(LoginActivity.this, NavigationScreen.class) );    }
                else { String excecao = "";
                    try { throw task.getException(); }
                    catch ( FirebaseAuthInvalidUserException e ) { excecao = "Usuário não está cadastrado."; }
                    catch ( FirebaseAuthInvalidCredentialsException e ){ excecao = "E-mail e senha não correspondem a um usuário cadastrado"; }
                    catch (Exception e){ excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                        e.printStackTrace(); }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show(); } }}); }//fecha o método e o else

    public void validarAutenticacaoUsuario(){
        //Recuperar textos dos campos
        String email, senha;
        email = emailLogin.getText().toString();
        senha = senhaLogin.getText().toString();

        //Validar se e-mail e senha foram digitados
        if( !email.isEmpty() ){//verifica e-mail
            if( !senha.isEmpty() ){//verifica senha
                Usuario usuario = new Usuario();
                usuario.setEmail( email );
                usuario.setSenha( senha );
                validacaoUsuario( usuario ); }
            else { Toast.makeText(LoginActivity.this, "Preencha a senha!", Toast.LENGTH_SHORT).show(); } //fecha o primeiro else
        }//fecha o if email
        else { Toast.makeText(LoginActivity.this, "Preencha o email!", Toast.LENGTH_SHORT).show(); }//fecha o else
    }

     public void usuarioLogado(){
        if(autenticar.getCurrentUser() != null){
            startActivity( new Intent(LoginActivity.this, NavigationScreen.class) );
            finish();
        }

    }
}
