package com.wyjson.actionbardemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wyjson.actionbardemo.R;

/**
 * https://www.iconfont.cn
 *
 * @author Wyjson
 * @version 1
 * @date 2021/9/30 4:45 PM
 */
public class IconActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon);

        getSuperActionBar().initActionBarForLeftIcon("Icon");

//        ((TextView) findViewById(R.id.tv_1)).setTypeface(IconFontUtils.getInstance().getIconStyleFont());

    }

    private boolean isIcon1, isIcon2;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                isIcon1 = !isIcon1;
                ((TextView) findViewById(R.id.tv_1)).setTextSize(isIcon1 ? 36 : 24);
                break;
            case R.id.tv_2:
                isIcon2 = !isIcon2;
                if (isIcon2) {
                    ((TextView) findViewById(R.id.tv_2)).setText(R.string.icon_fault);
                    ((TextView) findViewById(R.id.tv_2)).setTextColor(Color.RED);
                } else {
                    ((TextView) findViewById(R.id.tv_2)).setText(R.string.icon_finish);
                    ((TextView) findViewById(R.id.tv_2)).setTextColor(Color.GREEN);
                }
                break;
        }
    }

}