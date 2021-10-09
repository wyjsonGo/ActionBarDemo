package com.wyjson.actionbardemo.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author WangYao
 * @version 1
 * @Description 跑马灯效果的TextView
 * @Date 2016-5-5 下午7:22:48
 * android:gravity="center" 可以换成其他属性
 * <p>
 * android:ellipsize="marquee"
 * android:gravity="center"
 * android:marqueeRepeatLimit="marquee_forever"
 * android:scrollHorizontally="true"
 * android:singleLine="true"
 * <p>
 * setEllipsize(TextUtils.TruncateAt.MARQUEE);
 * setGravity(Gravity.CENTER);
 * setMarqueeRepeatLimit(-1);
 * setHorizontallyScrolling(true);
 * setSingleLine(true);
 */
public class AlwaysMarqueeTextView extends AppCompatTextView {
    public AlwaysMarqueeTextView(Context context) {
        super(context);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlwaysMarqueeTextView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
