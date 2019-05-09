package com.nobugs.parthenon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

public class Faq extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.faq, container, false);

        LinearLayout scroll = rootView.findViewById(R.id.faq);

        for (int i = 0; i < 25; i++) {
            FrameLayout templateFaq = (FrameLayout) getLayoutInflater().inflate(R.layout.faq_template, scroll, false);

            final Bundle bd = new Bundle();
            bd.putString("title", "Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa melek, tu ta top?");
            bd.putString("answer", getString(R.string.placeholderBig));
            templateFaq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent(container.getContext(), Question.class);
                    it.putExtras(bd);

                    startActivity(it);
                }
            });

            scroll.addView(templateFaq);
        }

        return rootView;
    }

}
