package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;

public class Test3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar().initActionBarForBothTxt("标题", R.string.icon_setting, view -> {
            Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
        });

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左右按钮");


    }
}