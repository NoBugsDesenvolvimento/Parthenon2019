package com.nobugs.parthenon;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class Programacao extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) getLayoutInflater().inflate(R.layout.programacao, container, false);

        ViewPager datePager = (ViewPager) getLayoutInflater().inflate(R.layout.days_navigation, null, false);
        rootView.addView(datePager.findViewById(R.id.datePager));

        ProgramacaoTemplate templateProg = new ProgramacaoTemplate();
        Bundle bd = new Bundle();
        bd.putString("name", "Vo da aula de Android D+");
        bd.putString("autor", "Róger");
        bd.putString("time", "04:20");
        bd.putString("local", "Aqui em casa bb");
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);
        rootView.addView(templateProg.onCreateView(getLayoutInflater(), rootView, bd), -1);

        return rootView;
    }

}
