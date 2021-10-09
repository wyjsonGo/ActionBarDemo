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

        getSuperActionBar().initActionBarForLeftIcon("标题");
        ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.test7_action_bar_middle, null);
        getSuperActionBar().getMiddleContainer().removeAllViews();
        getSuperActionBar().getMiddleContainer().addView(imageView);

        ((TextView) findViewById(R.id.tv_content)).setText("自定义带图片(左右按钮同理)");


    }
}