package com.example.testview.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testview.R;

/**
 * Created by lihang on 2017/9/14.
 */

public class StrokeTextView extends TextView {

    private TextView borderText;

    private int borderColor= Color.BLUE;

    private float borderWidth=4;


    public StrokeTextView(Context context) {
        super(context);
        borderText=new TextView(context);
        init();
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        borderText=new TextView(context,attrs);

        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        borderColor=a.getColor(R.styleable.StrokeTextView_border_color,Color.WHITE);
        borderWidth=a.getFloat(R.styleable.StrokeTextView_border_width,4);
        init();
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        borderText=new TextView(context,attrs,defStyleAttr);
        init();
    }

    private void init(){
        TextPaint tp=borderText.getPaint();
        tp.setStyle(Paint.Style.STROKE);
        tp.setStrokeWidth(borderWidth);

        borderText.setTextColor(borderColor);
        borderText.setGravity(getGravity());

    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        borderText.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.i("Tag",getText().toString());

//        CharSequence cs=borderText.getText();
//        if(cs==null || !cs.equals(this.getText())){
            borderText.setText(getText());
//        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        borderText.measure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        borderText.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        borderText.layout(left,top,right,bottom);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        requestLayout();
    }

    public void setBorderColor(int color){
        borderColor=color;
    }

    public void setBorderWidth(float width){
        borderWidth=width;
    }

}
