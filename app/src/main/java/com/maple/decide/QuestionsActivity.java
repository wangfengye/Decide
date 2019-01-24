package com.maple.decide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ascend.wangfeng.latte.util.storage.LattePreference;
import com.maple.decide.bean.QuestionMap;

public class QuestionsActivity extends AppCompatActivity {
    RecyclerView mRv;
    QuestionAdapter mAdapter;
    QuestionMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        mRv = findViewById(R.id.rv_questions);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mMap = new QuestionMap();
        mAdapter = new QuestionAdapter(mMap);
        mAdapter.setListener(new QuestionAdapter.Listener() {
            @Override
            public void onBottomClicked() {
                Intent intent = new Intent(QuestionsActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.TYPE, EditActivity.ADD);
                startActivity(intent);
            }

            @Override
            public void onClick(int position) {
                mMap.setLastUsed(position);
                LattePreference.setJson(MainActivity.QUESTIONS, mMap);
                finish();
            }

            @Override
            public void onEditClick(int position) {
                Intent intent = new Intent(QuestionsActivity.this, EditActivity.class);
                intent.putExtra(EditActivity.TYPE, EditActivity.UPDATE);
                intent.putExtra(EditActivity.ID, position);
                startActivity(intent);
            }
        });
        mRv.setAdapter(mAdapter);
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        final TextView textView = findViewById(R.id.tv_del);
        findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdapter.mDelMode){
                    mAdapter.enableDel(false);
                    textView.setText("删除");
                }else {
                    mAdapter.enableDel(true);
                    textView.setText("完成");
                }
            }
        });
    }

    private void initData() {
        mMap = LattePreference.getJson(MainActivity.QUESTIONS, QuestionMap.class);
        mAdapter.resetData(mMap);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
