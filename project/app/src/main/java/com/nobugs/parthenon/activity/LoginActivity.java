package com.nobugs.parthenon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.SignInButton;
import com.nobugs.parthenon.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnFacebook, btnEmail, btnCadastro;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    }
}
