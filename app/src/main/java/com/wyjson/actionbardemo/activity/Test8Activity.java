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

        getSuperActionBar().initActionBarForBothRightText("title", R.string.action_bar_right_save, false, new SuperActionBar.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Test8Activity.this, "保存按钮的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左边图片,右边文字");


    }
}