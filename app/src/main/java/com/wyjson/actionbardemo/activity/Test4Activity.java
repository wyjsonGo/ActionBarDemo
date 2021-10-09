package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.view.SuperActionBar;

public class Test4Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar().initActionBarForLeftIcon("标题", R.string.icon_close, new SuperActionBar.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Test4Activity.this, "自定义关闭事件", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左按钮自定义图标和关闭事件");


    }
}