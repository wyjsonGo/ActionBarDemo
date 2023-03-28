package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;

public class Test5Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar()
                .setTitleText("标题过长情况,确保不被两边按钮压住!标题过长情况,确保不被两边按钮压住!")
                .setLeftText();

        ((TextView) findViewById(R.id.tv_content)).setText("标题过长情况,确保不被两边按钮压住");


    }
}