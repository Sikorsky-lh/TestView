package com.example.testview.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.testview.PieData;
import com.example.testview.R;
import com.example.testview.view.Pie;

import java.util.ArrayList;
import java.util.List;

public class PieActivity extends AppCompatActivity {

    private List<PieData> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        initData();
        Pie pie= (Pie) findViewById(R.id.pie);
        pie.setData(mData);
    }

    private void initData(){
        mData.add(new PieData("上海",80));
        mData.add(new PieData("南京",75));
        mData.add(new PieData("杭州",85));
        mData.add(new PieData("西安",50));
        mData.add(new PieData("石家庄",30));
    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,PieActivity.class);
        context.startActivity(intent);
    }
}
