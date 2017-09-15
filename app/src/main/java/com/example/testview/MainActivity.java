package com.example.testview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.testview.activity.ArrowActivity;
import com.example.testview.activity.PieActivity;
import com.example.testview.activity.STextViewActivity;
import com.example.testview.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=MainActivity.this;

    }



    public void toPieView(View view) {
        PieActivity.actionStart(mContext);
    }


    public void toArrowView(View view) {
        ArrowActivity.actionStart(mContext);
    }


    public void toSearchView(View view) {
        SearchActivity.actionStart(mContext);
    }

    public void toStrokeTextView(View view) {
        STextViewActivity.actionStart(mContext);
    }
}
