package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.view.SuperActionBar;

public class Test11Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar().initActionBarForBothLeftTxtRightImg("标题", R.mipmap.ic_launcher_round, new SuperActionBar.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Test11Activity.this, "右边图片按钮的点击事件", Toast.LENGTH_SHORT).show();
            }
        });

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左边Icon,右边图片");


    }
}