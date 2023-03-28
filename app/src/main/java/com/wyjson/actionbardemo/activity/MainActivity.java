package com.wyjson.actionbardemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wyjson.actionbardemo.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSuperActionBar().initStyleToTitle(R.string.app_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test1:
                startActivity(new Intent(this, Test1Activity.class));
                break;
            case R.id.tv_test2:
                startActivity(new Intent(this, Test2Activity.class));
                break;
            case R.id.tv_test3:
                startActivity(new Intent(this, Test3Activity.class));
                break;
            case R.id.tv_test4:
                startActivity(new Intent(this, Test4Activity.class));
                break;
            case R.id.tv_test5:
                startActivity(new Intent(this, Test5Activity.class));
                break;
            case R.id.tv_test6:
                startActivity(new Intent(this, Test6Activity.class));
                break;
            case R.id.tv_test7:
                startActivity(new Intent(this, Test7Activity.class));
                break;
            case R.id.tv_test8:
                startActivity(new Intent(this, Test8Activity.class));
                break;
            case R.id.tv_test9:
                startActivity(new Intent(this, Test9Activity.class));
                break;
            case R.id.tv_test10:
                startActivity(new Intent(this, Test10Activity.class));
                break;
            case R.id.tv_test11:
                startActivity(new Intent(this, Test11Activity.class));
                break;
            case R.id.tv_icon:
                startActivity(new Intent(this, IconActivity.class));
                break;
        }
    }
}