package com.example.testview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.testview.PieData;
import com.example.testview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihang on 2017/9/13.
 */

public class Pie extends View {

    private Paint piePaint;
    private Paint textPaint;

    private int mWidth;
    private int mHeight;

    private List<PieData> mData=new ArrayList<>();

    private float startAngle=0;

    private int[] colors={Color.GRAY,Color.GREEN,Color.BLACK,Color.BLUE,Color.CYAN,Color.RED};

    public Pie(Context context) {
        super(context);
        init();
    }

    public Pie(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.PieView);
        startAngle=a.getFloat(R.styleable.PieView_start_angle,0);

        a.recycle();
        init();
    }

    public Pie(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.PieView);
        startAngle=a.getFloat(R.styleable.PieView_start_angle,50.2f);

        a.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode==MeasureSpec.AT_MOST && heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        }else if(widthSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heightSpecSize);
        }else if(heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,200);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight=h;
        mWidth=w;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth/2,mHeight/2);

        float curAngle=startAngle;
        for(int i=0;i<mData.size();i++){
            PieData pie=mData.get(i);
            piePaint.setColor(pie.getColor());

            float r= (float) Math.min(0.8*mWidth/2,0.8*mHeight/2);
            canvas.drawArc(-r,-r,r,r,curAngle,pie.getAngle(),true,piePaint);
            curAngle+=pie.getAngle();
        }
    }

    private void init(){
        piePaint=new Paint();
        piePaint.setStyle(Paint.Style.FILL);
        piePaint.setAntiAlias(true);
    }

    public void setData(List<PieData> pieDatas){
        mData=pieDatas;

        float sum=0;
        for (int i=0;i<mData.size();i++){
            PieData pie=mData.get(i);
            sum+=pie.getValue();
            pie.setColor(colors[i]);
        }

        for(PieData pie:mData){
            pie.setPercentage(pie.getValue()/sum);
            pie.setAngle(360*pie.getPercentage());
        }

        invalidate();
    }


    public void setStartAngle(float angle){
        startAngle=angle;
    }
}
