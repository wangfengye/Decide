package com.maple.decide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.maple.decide.view.RandomHandView;

public class RandomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        final RandomHandView rv = findViewById(R.id.rv);
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rv.reset();
            }
        });
    }
}
