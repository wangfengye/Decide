package com.maple.decide.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author maple on 2019/1/19 17:27.
 * @version v1.0
 * @see 1040441325@qq.com
 * 转盘
 */
public class Turntable extends View {
    public static final String TAG = Turntable.class.getSimpleName();
    private ArrayList<String> data;
    private int mRadius;//外盘半径
    private int mSubRadius;//内盘半径
    private int mWidth;
    private int mHeight;
    private RectF mRectF;//圆弧基础
    private Paint mPaint;
    private int mRes = -1;
    private RotationListener mListener;


    public Turntable(Context context) {
        super(context);
        init();
    }

    public Turntable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Turntable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 重置数据
     *
     * @param data 选项列表
     */
    public void setData(ArrayList<String> data) {
        this.data = data;
        invalidate();
    }

    /**
     * @param listener 监听事件
     */
    public void setRotationListener(RotationListener listener) {
        this.mListener = listener;
    }

    public void reSet() {
        mRunning = false;
        mRes = -1;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = Math.min(w, h) / 2;
        mSubRadius = (int) (.95 * mRadius);
        mRectF = new RectF(w / 2 - mSubRadius, h / 2 - mSubRadius,
                w / 2 + mSubRadius, h / 2 + mSubRadius);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        data = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            data.add("sas" + i);
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, mPaint);
    }

    int i = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        i++;
        invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制背景圆
        mPaint.setColor(Color.parseColor("#ebf0ef"));
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
        float degree = 360f / data.size();
        float startDegree = 90 + degree / 2;

        for (int i = 0; i < data.size(); i++) {
            mPaint.setColor(getRandomColor(i));
            // 弧度是顺时针增长,我们设置的坐标系是逆时针增长,所以需要取反
            canvas.drawArc(mRectF, -degree * i - startDegree, degree, true, mPaint);
            if (mRes != -1 && i != mRes) {
                mPaint.setColor(Color.parseColor("#99ebf0ef"));
                canvas.drawArc(mRectF, -degree * i - startDegree, degree, true, mPaint);
            }
        }

        mPaint.setColor(Color.WHITE);
        for (int i = 0; i < data.size(); i++) {
            float x = (float) (mWidth / 2 + Math.cos((i * degree + startDegree) * Math.PI / 180) * mSubRadius);
            float y = (float) (mHeight / 2 - Math.sin((i * degree + startDegree) * Math.PI / 180) * mSubRadius);
            canvas.drawLine(mWidth / 2, mHeight / 2, x, y, mPaint);
        }
        drawChoices2(canvas, degree, startDegree);
    }

    private void drawChoices2(Canvas canvas, float degree, float startDegree) {
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(48);
        for (int i = 0; i < data.size(); i++) {
            float x = (float) (mWidth / 2 + Math.cos(((i - .5) * degree + startDegree) * Math.PI / 180) * mSubRadius);
            float y = (float) (mHeight / 2 - Math.sin(((i - .5) * degree + startDegree) * Math.PI / 180) * mSubRadius);
            Path path = new Path();
            path.moveTo(x, y);
            path.lineTo(mWidth / 2, mHeight / 2);
            // 参数说明, 文字, 路径, 水平偏移,垂直偏移,画笔
            canvas.drawTextOnPath(data.get(i), path, 50, 0, mPaint);
        }

    }

    int[] colors = new int[]{Color.parseColor("#ee534f"),
            Color.parseColor("#aa47bc"),
            Color.parseColor("#5c6bc0"),
            Color.parseColor("#42a5f6"),
            Color.parseColor("#66bb6a"),
            Color.parseColor("#ffc928"),
            Color.parseColor("#ffa827"),
            Color.parseColor("#ec407a")};


    public int getRandomColor(int i) {
     /*   int red = (int) (Math.random() * 256);
        int blue = (int) (Math.random() * 256);
        int green = (int) (Math.random() * 256);
        return Color.rgb(red, blue, green);*/
        return colors[i % colors.length];
    }

    private boolean mRunning;

    /**
     * 开始转盘
     *
     * @return 返回转盘结果
     */

    public int start() {
        final int res = (int) (Math.random() * data.size());
        start(res, 50, 5);
        return res;
    }

    /**
     * start rotation
     *
     * @param res         result
     * @param rotationNum rotation count
     * @param seconds     animator time
     */
    private void start(final int res, int rotationNum, int seconds) {
        if (mRunning) {
            Log.e(TAG, "start: onRunning");
            return;
        }
        mRunning = true;

        ValueAnimator animator = ObjectAnimator.ofFloat(this, "rotation", 0f, +rotationNum * 360f + res * 360f / data.size());
        animator.setDuration(seconds * 1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // mRunning = false;//改为手动重置
                mRes = res;
                invalidate();
                Log.i(TAG, "onAnimationEnd: " + res);
                if (mListener != null) mListener.onRotationEnd(data.get(res));
            }
        });
        animator.start();
    }

    public interface RotationListener {
        void onRotationEnd(String data);
    }
}
