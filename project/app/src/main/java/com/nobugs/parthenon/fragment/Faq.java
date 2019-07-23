package com.nobugs.parthenon.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import com.nobugs.parthenon.R;

import java.util.ArrayList;

public class Faq extends Fragment {
    private EditText search;
    private ArrayList<View> questionsViews = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_faq, container, false);

        search = rootView.findViewById(R.id.search_question);

        LinearLayout scroll = rootView.findViewById(R.id.faq);

        String[] questions = getResources().getStringArray(R.array.questions);
        String[] answers = getResources().getStringArray(R.array.answer);
        int total = questions.length;

        for (int i = 0; i < total; i++) {
            FrameLayout templateFaq = (FrameLayout) getLayoutInflater().inflate(R.layout.template_faq, scroll, false);
            ((TextView) templateFaq.findViewById(R.id.question_template)).setText(questions[i]);

            final Bundle bd = new Bundle();
            bd.putString("title", questions[i]);
            bd.putString("answer", answers[i]);
            templateFaq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Intent it = new Intent(container.getContext(), Question.class);
                    it.putExtras(bd);

                    startActivity(it);*/
                }
            });

            scroll.addView(templateFaq);
        }

        rootView.findViewsWithText(questionsViews,"Question",View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchString = search.getText().toString().toLowerCase();

                int total = questionsViews.size();
                for (int i = 0; i < total; i++){
                    String textString = ((TextView) questionsViews.get(i).findViewById(R.id.question_template)).getText().toString().toLowerCase();
                    if (!textString.contains(searchString)){
                        questionsViews.get(i).setVisibility(View.GONE);
                    }else{
                        questionsViews.get(i).setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return rootView;
    }

}
