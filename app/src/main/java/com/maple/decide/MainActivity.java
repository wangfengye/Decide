package com.maple.decide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ascend.wangfeng.latte.app.Latte;
import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.maple.decide.bean.Question;
import com.maple.decide.bean.QuestionMap;
import com.maple.decide.view.Turntable;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String QUESTIONS = "questions";
    private Turntable mTurntable;
    private TextView mTvTitle;
    TextView mTvRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        mTurntable = findViewById(R.id.turntable);
        mTvTitle = findViewById(R.id.tv_title);
        mTvRes = findViewById(R.id.tv_res);
        findViewById(R.id.img_feature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(MainActivity.this, RandomActivity.class));
                startActivity(new Intent(MainActivity.this, CoinActivity.class));
            }
        });
        findViewById(R.id.tv_turntable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTurntable.reSet();
                mTvRes.setText("???");
            }
        });
        mTurntable.setRotationListener(new Turntable.RotationListener() {
            @Override
            public void onRotationEnd(String data) {
                mTvRes.setText(data);
            }
        });
        findViewById(R.id.img_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QuestionsActivity.class));
            }
        });
        findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTurntable.start();
            }
        });
    }

    private void initData(){
        mTurntable.reSet();
        mTvRes.setText("???");
        mTurntable.setData(getQuestion().getOptions());
        mTvTitle.setText(getQuestion().getTitle());
    }

    public static Question getQuestion() {
        QuestionMap ma = LattePreference.getJson(QUESTIONS, QuestionMap.class);
        if (ma == null) ma = new QuestionMap();
        return ma.last();
    }
}
