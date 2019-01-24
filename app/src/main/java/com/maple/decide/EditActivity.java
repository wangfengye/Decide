package com.maple.decide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.maple.decide.bean.Question;
import com.maple.decide.bean.QuestionMap;

public class EditActivity extends AppCompatActivity {
    public static final String TYPE = "EditType";
    public static final int ADD = 1;
    public static final int UPDATE = 2;
    public static final String ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        RecyclerView mRv = findViewById(R.id.rv_options);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        int type = getIntent().getIntExtra(TYPE, ADD);
        QuestionMap questions = LattePreference.getJson(MainActivity.QUESTIONS, QuestionMap.class);
        if (questions == null) questions = new QuestionMap();
        final Question question;
        if (type == ADD) {
            question = new Question();
            question.addOption("");
            questions.addQuestion(question);
        } else {
            int id = getIntent().getIntExtra(ID, 0);
            question = questions.getQuestions().get(id);
        }
        OptionAdapter adapter = new OptionAdapter(question.getOptions(), mRv);
        mRv.setAdapter(adapter);

        final QuestionMap finalQuestions = questions;
        final TextView mtvTitle = findViewById(R.id.ed_title);
        mtvTitle.setText(question.getTitle());
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question.setTitle(mtvTitle.getText().toString());
                LattePreference.setJson(MainActivity.QUESTIONS, finalQuestions);
                Toast.makeText(EditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
