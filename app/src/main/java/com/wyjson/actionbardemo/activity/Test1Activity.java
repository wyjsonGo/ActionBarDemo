package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.view.SuperActionBar;

public class Test1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        /**
         * 链式调用
         */
//        getSuperActionBar()
//                .initStyle(SuperActionBar.Style.TITLE_LEFT_TXT)
//                .setTitleText("标题a")
//                .setLeftTxtBtn(R.string.action_bar_left_return_icon,true, new SuperActionBar.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        finish();
//                    }
//                });
        // 封装后
        getSuperActionBar().initActionBarForLeftIcon("标题");

        ((TextView) findViewById(R.id.tv_content)).setText("标题,左返回按钮");

    }
}