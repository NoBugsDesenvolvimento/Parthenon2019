package com.nobugs.parthenon;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Programacao extends Fragment {
    Days days;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.programacao, container, false);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        days = new Days();
        fragmentTransaction.add(R.id.progContainer, days);
        fragmentTransaction.commit();


        /* PASSAR ISSO PRA DENTRO DO DAYS
        ProgramacaoTemplate templateProg = new ProgramacaoTemplate();
        Bundle bd = new Bundle();
        bd.putString("name", "Vo da aula de Android D+");
        bd.putString("autor", "Róger");
        bd.putString("time", "04:20");
        bd.putString("local", "Aqui em casa bb");
        templateProg.attach(rootView, bd);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);*/

        return rootView;
    }

}
