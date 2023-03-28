package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;

public class Test11Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar()
                .setTitleText("标题")
                .setLeftText()
                .setRightImg(R.mipmap.ic_launcher_round, view -> Toast.makeText(Test11Activity.this, "右边图片按钮的点击事件", Toast.LENGTH_SHORT).show());

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左边Icon,右边图片");

    }
}