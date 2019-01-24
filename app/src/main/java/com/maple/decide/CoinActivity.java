package com.maple.decide;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.plattysoft.leonids.ParticleSystem;

public class CoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final ImageView icon = findViewById(R.id.img_icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim(icon);
            }
        });
        // 点赞特效动画
/*       new ParticleSystem(this,5,getResources().getDrawable(R.mipmap.ic_feature),5000)
               .setSpeedRange(0.2f, 0.5f)
               .oneShot(button, 5);*/
    }

    private void anim(final ImageView icon) {
        float a = (int) (Math.random()*2);
        ValueAnimator animator = ObjectAnimator.ofFloat(icon, "rotationX", 0f, 20 * 360f + 45+180*a);
        final int[] base =new int[2];
        base[0]=135;
        base[1]=0;
        final int[] imgSrcs = new int[]{R.mipmap.ic_feature,R.mipmap.img_coin};

       animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float a = (float) valueAnimator.getAnimatedValue();
                int i = (int)a ;
                if (i>=base[0]){
                    icon.setImageResource(imgSrcs[base[1]]);
                    icon.setRotation(base[1]==0?180:0);
                    base[0]+=180;
                    base[1]= base[1]==0?1:0;
                }

            }
        });
        animator.setDuration(5000);
        ValueAnimator animator1 = ObjectAnimator.ofFloat(icon, "translationY", 0, -1800, 0);
        animator1.setDuration(5000);
        animator1.setInterpolator(new BounceInterpolator());
        AnimatorSet set = new AnimatorSet();
        
        set.play(animator).with(animator1);
        set.start();
    }
}
