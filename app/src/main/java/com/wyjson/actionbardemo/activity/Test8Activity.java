package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.view.SuperActionBar;

public class Test8Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar()
                .initStyle(SuperActionBar.Style.TITLE_LEFT_IMG_RIGHT_TXT)
                .setTitleText("标题")
                .setLeftImgBtn(R.drawable.super_action_bar_back_bg_selector, new SuperActionBar.OnActionBarLeftClickListener())
                .setRightTxtBtn(R.string.action_bar_right_save, new SuperActionBar.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Test8Activity.this, "保存按钮的点击事件", Toast.LENGTH_SHORT).show();
                    }
                })
        ;

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左边图片,右边文字");


    }
}