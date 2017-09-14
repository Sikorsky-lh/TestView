package com.example.testview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import static com.example.testview.view.SearchView.State.ENDING;
import static com.example.testview.view.SearchView.State.NONE;
import static com.example.testview.view.SearchView.State.SEARCHING;
import static com.example.testview.view.SearchView.State.STARTING;

/**
 * Created by lihang on 2017/9/13.
 */

public class SearchView extends View {

    private Paint mPaint;

    private Path searchPath,loadingPath;

    private PathMeasure mMeasure;

    private int mWidth,mHeight;

    private int defualtDuration=2000;

    public static enum State{
        NONE,
        STARTING,
        SEARCHING,
        ENDING
    }

    private State currentState=State.NONE;

    private ValueAnimator mStartingAnimator,mSearchingAnimator,mEndingAnimator;

    private float mAnimatorValue;

    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private Animator.AnimatorListener mAnimatorListener;

    private Handler mAnimatorHandler;

    private boolean isOver;

    private int count=0;


    public SearchView(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight=h;
        mWidth=w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawColor(Color.parseColor("#0082D7"));

        switch (currentState){
            case NONE:
                canvas.drawPath(searchPath,mPaint);
                break;
            case STARTING:
                mMeasure.setPath(searchPath,false);
                Path dst=new Path();
                mMeasure.getSegment(mMeasure.getLength()*mAnimatorValue,mMeasure.getLength(),dst,true);
                canvas.drawPath(dst,mPaint);
                break;
            case SEARCHING:
                mMeasure.setPath(loadingPath,false);
                Path dst2=new Path();
                float stop=mAnimatorValue*mMeasure.getLength();
                float start= (float) (stop-(0.5-Math.abs(0.5-mAnimatorValue))*200f);
                mMeasure.getSegment(start,stop,dst2,true);
                canvas.drawPath(dst2,mPaint);
                break;
            case ENDING:
                mMeasure.setPath(searchPath,false);
                Path dst3=new Path();
                mMeasure.getSegment(mMeasure.getLength()*(1-mAnimatorValue),mMeasure.getLength(),dst3,true);
                canvas.drawPath(dst3,mPaint);
                break;

        }
//        canvas.drawPath(searchPath,mPaint);
//        canvas.drawPath(loadingPath,mPaint);
    }




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init(){
        initPaint();
        initPath();
        initListener();
        initHandler();
        initAnimator();

        currentState=State.STARTING;
        mStartingAnimator.start();
    }

    private void initPaint(){
        mPaint=new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(15);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initPath(){
        searchPath=new Path();
        loadingPath=new Path();
        searchPath.addArc(-50,-50,50,50,45,359.9f);
        loadingPath.addArc(-100,-100,100,100,45,359.9f);

        mMeasure=new PathMeasure();
        mMeasure.setPath(loadingPath,false);

        float[] pos=new float[2];
        mMeasure.getPosTan(mMeasure.getLength(),pos,null);

        searchPath.lineTo(pos[0],pos[1]);
    }

    private void initListener(){
        mUpdateListener=new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue= (float) animation.getAnimatedValue();
                invalidate();
            }
        };

        mAnimatorListener=new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };

    }

    private void initHandler(){
        mAnimatorHandler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (currentState){
                    case STARTING:
                        isOver=false;
                        currentState= SEARCHING;
//                        mStartingAnimator.removeAllListeners();
                        mSearchingAnimator.start();
                        break;
                    case SEARCHING:
                        if(!isOver){
                            mSearchingAnimator.start();
                            count++;
                            if(count>2){
                                isOver=true;
                            }
                        }else {
                            currentState=ENDING;
                            mEndingAnimator.start();
                            count=0;
                        }
                        break;
                    case ENDING:
                        currentState=STARTING;
                        mStartingAnimator.start();
                        break;
                }
            }
        };
    }

    private void initAnimator(){
        mStartingAnimator=ValueAnimator.ofFloat(0,1).setDuration(defualtDuration);
        mSearchingAnimator=ValueAnimator.ofFloat(0,1).setDuration(defualtDuration);
        mEndingAnimator=ValueAnimator.ofFloat(0,1).setDuration(defualtDuration);

        mStartingAnimator.addUpdateListener(mUpdateListener);
        mSearchingAnimator.addUpdateListener(mUpdateListener);
        mEndingAnimator.addUpdateListener(mUpdateListener);

        mStartingAnimator.addListener(mAnimatorListener);
        mSearchingAnimator.addListener(mAnimatorListener);
        mEndingAnimator.addListener(mAnimatorListener);
    }




}
