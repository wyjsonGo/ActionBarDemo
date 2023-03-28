package com.wyjson.actionbardemo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.view.AlwaysMarqueeTextView;

public class Test6Activity extends BaseActivity {

    private boolean isSwitch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        getSuperActionBar()
                .setLeftText();
        AlwaysMarqueeTextView tvTitle = (AlwaysMarqueeTextView) LayoutInflater.from(this).inflate(R.layout.test6_action_bar_middle, null);
        tvTitle.setText("标题过长情况,走马灯效果&标题过长情况,走马灯效果");
        getSuperActionBar().getMiddleContainer().removeAllViews();
        getSuperActionBar().getMiddleContainer().addView(tvTitle);

        /**
         * 下面代码是切换走马灯开关
         */
        TextView textView = ((TextView) findViewById(R.id.tv_content));
        textView.setText("标题过长情况,走马灯效果(点击切换)");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSwitch = !isSwitch;
                switchTitleAlwaysMarquee(tvTitle, isSwitch);
                Toast.makeText(Test6Activity.this, "标题走马灯:" + isSwitch, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 是否打开标题走马灯
     *
     * @param isOpen
     * @return
     */
    public void switchTitleAlwaysMarquee(AlwaysMarqueeTextView tvTitle, boolean isOpen) {
        if (tvTitle != null) {
            if (isOpen) {
                tvTitle.setGravity(Gravity.CENTER);
                tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                tvTitle.setMarqueeRepeatLimit(-1);
                tvTitle.setHorizontallyScrolling(true);
                tvTitle.setSingleLine(true);
            } else {
                tvTitle.setGravity(Gravity.START | Gravity.CENTER);
                tvTitle.setEllipsize(TextUtils.TruncateAt.END);
                tvTitle.setMarqueeRepeatLimit(3);
                tvTitle.setHorizontallyScrolling(false);
                tvTitle.setSingleLine(true);
            }
        }
    }
}