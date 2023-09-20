package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.wyjson.actionbardemo.R;

public class Test10Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_10_xml);

        ((TextView) findViewById(R.id.tv_content)).setText("xml实现");
    }

}