package com.example.testview.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testview.R;
import com.example.testview.view.ArrowView;

public class ArrowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrow);

        ArrowView arrowView= (ArrowView) findViewById(R.id.arrow);
    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context, ArrowActivity.class);
        context.startActivity(intent);
    }

}
