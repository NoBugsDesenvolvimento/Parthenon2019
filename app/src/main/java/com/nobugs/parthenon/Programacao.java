package com.nobugs.parthenon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class Programacao extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.programacao, container, false);

        TextView filterButton = rootView.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout filters = rootView.findViewById(R.id.filters);
                if (filters.getVisibility() == View.GONE)
                    filters.setVisibility(View.VISIBLE);
                else
                    filters.setVisibility(View.GONE);
            }
        });

        ProgramacaoTemplate templateProg = new ProgramacaoTemplate();
        Bundle bd = new Bundle();
        bd.putString("name", "Vo da aula de Android D+");
        bd.putString("autor", "Róger");
        bd.putString("time", "04:20");
        bd.putString("local", "Aqui em casa bb");
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), rootView.getChildCount()-1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), rootView.getChildCount()-1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), rootView.getChildCount()-1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), rootView.getChildCount()-1);


        return rootView;
    }

}
