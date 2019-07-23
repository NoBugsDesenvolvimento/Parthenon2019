package com.nobugs.parthenon.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nobugs.parthenon.R;
import com.nobugs.parthenon.activity.Inscrever;

public class InscricaoHolder extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_inscricao_holder, container, false);

        Button bt = rootView.findViewById(R.id.bt_inscricao);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getContext(), Inscrever.class);
                startActivity(it);
            }
        });

        return rootView;
    }

}
