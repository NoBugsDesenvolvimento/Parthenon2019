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
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.model.Usuários.Usuario;

public class LoginActivity extends AppCompatActivity {

    private Button btnFacebook, btnEmail, btnCadastro;
    private SignInButton signInButton;
    private TextInputEditText emailLogin, senhaLogin;
    private FirebaseAuth autenticar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticar = ConfiguracaoFirebase.getFirebaseAutenticacao();
        inicializarComponentes();
        //usuarioLogado();


        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarAutenticacaoUsuario();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });

    }
       public void inicializarComponentes(){
    btnFacebook = findViewById(R.id.loginFacebook);
    btnEmail = findViewById(R.id.loginEmail);
    signInButton = findViewById(R.id.loginGoogle);
    btnCadastro = findViewById(R.id.btnCadastro);
    emailLogin = findViewById(R.id.textEmailLogin);
    senhaLogin = findViewById(R.id.textSenhaLogin);
    }


    public void validacaoUsuario(Usuario usuario){

        autenticar.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
    }//fecha o método

     public void usuarioLogado(){
        if(autenticar.getCurrentUser() != null){
            startActivity( new Intent(LoginActivity.this, NavigationScreen.class) );
            finish();
        }

    }
}
