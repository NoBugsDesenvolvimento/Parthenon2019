package com.nobugs.parthenon;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Question extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        Intent it = getIntent();
        Bundle bd =  it.getExtras();

        if (bd.getString("title") == null) Log.v("rgk", "sasfas");
        ((TextView) findViewById(R.id.questionText)).setText(bd.getString("title"));
        ((TextView) findViewById(R.id.answerText)).setText(bd.getString("answer"));
    }
}
