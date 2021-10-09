package com.wyjson.actionbardemo.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.wyjson.actionbardemo.R;

/**
 * @author Wyjson
 * @version 1
 * @date 2021/10/8 11:56 AM
 */
public class IconTextView extends androidx.appcompat.widget.AppCompatTextView {

    public IconTextView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public IconTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public IconTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setTypeface(getResources().getFont(R.font.iconfont));
        } else {
            setTypeface(ResourcesCompat.getFont(context, R.font.iconfont));
        }
    }
}
