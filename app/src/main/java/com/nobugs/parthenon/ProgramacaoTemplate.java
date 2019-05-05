package com.nobugs.parthenon;


import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class ProgramacaoTemplate extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.prog_template, container, false);

        TextView name = rootView.findViewById(R.id.name);
        TextView autor = rootView.findViewById(R.id.autor);
        TextView time = rootView.findViewById(R.id.time);
        TextView local = rootView.findViewById(R.id.local);

        name.setText(name.getText() + " " + savedInstanceState.getString("name"));
        autor.setText(savedInstanceState.getString("autor"));
        time.setText(time.getText() + " " + savedInstanceState.getString("time"));
        local.setText(local.getText() + " " + savedInstanceState.getString("local"));

        return rootView;
    }
}
