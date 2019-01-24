package com.maple.decide.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author maple on 2019/1/24 14:10.
 * @version v1.0
 * @see 1040441325@qq.com
 * 多指随机
 * todo 没想好用途
 */
public class RandomHandView extends View {
    int[] colors = new int[]{Color.parseColor("#ee534f"),
            Color.parseColor("#aa47bc"),
            Color.parseColor("#5c6bc0"),
            Color.parseColor("#42a5f6"),
            Color.parseColor("#66bb6a"),
            Color.parseColor("#ffc928"),
            Color.parseColor("#ffa827"),
            Color.parseColor("#ec407a")};
    private Paint mPaint;
    private int mSelected = 0;
    private int mCounter = 0;
    private HashMap<Integer,PointF> mData = new HashMap<>();
    Handler mHandler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("TAG", "run: "+mSelected);
            if (mCounter>=100)return;
            mSelected++;
            mCounter++;
            if (mSelected>=mData.size())mSelected=0;
            invalidate();
            mHandler.postDelayed(runnable,120);
        }
    };

    public RandomHandView(Context context) {
        super(context);
        init();
    }

    public RandomHandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RandomHandView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mHandler.post(runnable);
    }

    public void reset(){
        if (mCounter<100)return;
        mData.clear();
        mSelected =0;
        mCounter =0;
        invalidate();
        mHandler.postDelayed(runnable,2000);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandler.post(runnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(Map.Entry<Integer,PointF> entry:mData.entrySet()){
            int i = entry.getKey();
            PointF p = entry.getValue();
            // 选择设置不同的透明度
            mPaint.setColor(colors[i%colors.length]);
            mPaint.setAlpha(i == mSelected ? 255 : 66);
            canvas.drawCircle(p.x, p.y, 120, mPaint);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mData.put(0,new PointF(event.getX(),event.getY()));
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mData.put(index,new PointF(event.getX(index),event.getY(index)));
                break;
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacks(runnable);
        super.onDetachedFromWindow();
    }
}
