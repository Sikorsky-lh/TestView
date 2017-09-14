package com.example.testview.view;

/**
 * Created by lihang on 2017/9/13.
 */

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.testview.R;

public class ArrowView extends View {

    private Bitmap mBtimap;

    private float[] pos;
    private float[] tan;

    private Matrix mMatrix;

    private Paint mPaint;
    private Paint mAnotherPaint;

    private int mWidth;
    private int mHeight;

    private ValueAnimator valueAnimator;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;
    private int defaultDuration=5000;

    private float curValue=0;

    public ArrowView(Context context) {
        super(context);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth/2,mHeight/2);

        Path path=new Path();
        path.addCircle(0,0,200,Path.Direction.CW);

        PathMeasure measure=new PathMeasure(path,false);

        measure.getPosTan(curValue*measure.getLength(),pos,tan);

        mMatrix.reset();
        float degree= (float) (Math.atan2(tan[1],tan[0])*180/Math.PI);

        mMatrix.postRotate(degree,mBtimap.getWidth()/2,mBtimap.getHeight()/2);
        mMatrix.postTranslate(pos[0]-mBtimap.getWidth()/2,pos[1]-mBtimap.getHeight()/2);

        Path dst =new Path();
        measure.getSegment(0,curValue*measure.getLength(),dst,true);

        canvas.drawPath(path,mPaint);
        canvas.drawBitmap(mBtimap,mMatrix,mPaint);

        canvas.drawPath(dst,mAnotherPaint);

    }

    private void init(){

        initPaint();
        initValueAnimator();

        pos=new float[2];
        tan=new float[2];

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=14;
        mBtimap=BitmapFactory.decodeResource(getResources(), R.drawable.arrow,options);

        mMatrix=new Matrix();
        valueAnimator.start();
    }

    private void initValueAnimator(){
        valueAnimator=ValueAnimator.ofFloat(0,1).setDuration(defaultDuration);
        mUpdateListener=new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curValue= (float) animation.getAnimatedValue();
                invalidate();
            }
        };
        mAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                valueAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };
        valueAnimator.addUpdateListener(mUpdateListener);
        valueAnimator.addListener(mAnimatorListener);
    }

    private void initPaint(){
        mPaint=new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        mAnotherPaint=new Paint();
        mAnotherPaint.setColor(Color.RED);
        mAnotherPaint.setStyle(Paint.Style.STROKE);
        mAnotherPaint.setStrokeWidth(10);
    }

}
