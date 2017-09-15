package com.example.testview.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.testview.R;
import com.example.testview.view.StrokeTextView;

public class STextViewActivity extends AppCompatActivity {

    private StrokeTextView stv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stext_view);

        stv= (StrokeTextView) findViewById(R.id.stroke_text);

    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,STextViewActivity.class);
        context.startActivity(intent);
    }

    public void change(View view) {
        stv.setText("lihang");
    }
}
