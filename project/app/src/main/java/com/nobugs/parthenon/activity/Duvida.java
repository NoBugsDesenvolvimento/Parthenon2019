package com.nobugs.parthenon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nobugs.parthenon.R;
import com.nobugs.parthenon.helper.ConfiguracaoFirebase;
import com.nobugs.parthenon.helper.RealmHelper;
import com.nobugs.parthenon.model.Perguntas.Pergunta;
import com.nobugs.parthenon.model.Perguntas.PerguntaAux;

import io.realm.Realm;
import io.realm.RealmResults;

public class Duvida extends FragmentActivity {
    public TextView tituloPergunta, conteudoPergunta, respostaPergunta;
    public EditText editEscreverResposta;
    public Button atualizarResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Já ta pegando a key da pergunta pra povoar a tela
        Intent it = getIntent();
        final String key = it.getStringExtra("key");
        Log.v("rgk", key);
        tituloPergunta = findViewById(R.id.questionTitle);
        conteudoPergunta = findViewById(R.id.questionText);
        respostaPergunta = findViewById(R.id.questionAnswer);
        editEscreverResposta = findViewById(R.id.escreverResposta);
        atualizarResposta = findViewById(R.id.buttonAtualizarResposta);

        Realm realm = RealmHelper.getRealm(this);
        RealmResults<Pergunta> pergunta = realm.where(Pergunta.class).equalTo("key", key).findAll();
        // pergunta.get(0) já é a pergunta

        final PerguntaAux atualizarPergunta = new PerguntaAux();
        atualizarPergunta.setPergunta(pergunta.get(0).getPergunta());
        atualizarPergunta.setResposta(pergunta.get(0).getResposta());
        atualizarPergunta.setEmail(pergunta.get(0).getEmail());
        atualizarPergunta.setRespondida(pergunta.get(0).getRespondida());
        atualizarPergunta.setTitulo(pergunta.get(0).getTitulo());

        tituloPergunta.setText(atualizarPergunta.getTitulo());
        conteudoPergunta.setText(atualizarPergunta.getPergunta());
        if(atualizarPergunta.getResposta() != null){
        respostaPergunta.setText(atualizarPergunta.getResposta());
        editEscreverResposta.setText(atualizarPergunta.getResposta());  }
        else{ respostaPergunta.setText("Lamentamos informar, mas a pergunta ainda não foi respondida. "); }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if(user.getPhotoUrl().toString().equals("admin")){
                editEscreverResposta.setVisibility(View.VISIBLE);
                atualizarResposta.setVisibility(View.VISIBLE);
                respostaPergunta.setVisibility(View.INVISIBLE); }
            else{ editEscreverResposta.setVisibility(View.INVISIBLE);
                atualizarResposta.setVisibility(View.INVISIBLE);
                respostaPergunta.setVisibility(View.VISIBLE);   }   }

        atualizarResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarPergunta.setResposta(editEscreverResposta.getText().toString());
                atualizarPergunta.setRespondida("1");
                FirebaseDatabase db = ConfiguracaoFirebase.getDatabase();
                DatabaseReference myRef = db.getReference("perguntas/" + key);
                myRef.setValue(atualizarPergunta);
            }
        });

    }
}
