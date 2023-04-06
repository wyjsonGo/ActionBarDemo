package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyjson.actionbardemo.R;

public class Test7Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.test7_action_bar_middle, null);
        getSuperActionBar().setLeftText().setMiddleView(imageView);

        ((TextView) findViewById(R.id.tv_content)).setText("自定义带图片(左右按钮同理)");

    }
}