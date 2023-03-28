package com.wyjson.actionbardemo.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.utils.StatusBarUtils;

/**
 * 超级活动栏
 *
 * @author Wyjson
 * @version 4
 * @date 2021-03-29 11:16
 */
public class SuperActionBar extends LinearLayout {

    private LayoutInflater mInflater;

    private View vStatusBar;
    private View commonActionBar;
    private LinearLayout llLeftContainer, llMiddleContainer, llRightContainer;
    private TextView tvTitle;
    private View vActionBarLine;

    private OnClickListener mLeftClickListener, mRightClickListener;

    private ImageView ivLeft, ivRight;

    private IconTextView tvLeft, tvRight;

    private final @DrawableRes
    int iconLeft = R.drawable.super_action_bar_back_white_selector;
    private final @StringRes
    int textLeft = R.string.super_action_bar_left_return_icon;

    public enum Style {// 样式
        TITLE(0x01),

        TITLE_LEFT_IMG(0x02),
        TITLE_RIGHT_IMG(0x03),
        TITLE_BOTH_IMG(0x04),

        TITLE_LEFT_TEXT(0x05),
        TITLE_RIGHT_TEXT(0x06),
        TITLE_BOTH_TEXT(0x07),

        TITLE_LEFT_IMG_RIGHT_TEXT(0x08),
        TITLE_LEFT_TEXT_RIGHT_IMG(0x09);

        private int value;

        public int getValue() {
            return value;
        }

        Style(int value) {
            this.value = value;
        }

        public static Style getActionType(int v) {
            Style[] values = values();
            for (Style each : values) {
                if (each.getValue() == v) {
                    return each;
                }
            }
            return null;
        }
    }

    public SuperActionBar(Context context) {
        this(context, null);
    }

    public SuperActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //<editor-fold desc="init style">
    private void init(Context context, AttributeSet attrs) {
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.super_action_bar_common_basics, this);
        setOnClickListener(null);// 不让点击事件透过bar
        initView();
        initAttr(context, attrs);
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.super_action_bar_background_color));
        float elevation = getContext().getResources().getDimension(R.dimen.super_action_bar_elevation);
        if (elevation != 0)
            setElevation(elevation);
    }

    private void initView() {
        vStatusBar = findViewById(R.id.v_status_bar);

        commonActionBar = findViewById(R.id.common_action_bar);

        llLeftContainer = commonActionBar.findViewById(R.id.ll_container_left);
        llMiddleContainer = commonActionBar.findViewById(R.id.ll_container_middle);
        tvTitle = llMiddleContainer.findViewById(R.id.super_action_bar_middle_tv_title);
        llRightContainer = commonActionBar.findViewById(R.id.ll_container_right);

        vActionBarLine = findViewById(R.id.v_action_bar_line);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SuperActionBar);
            int styleType = ta.getInteger(R.styleable.SuperActionBar_super_action_bar_style, Style.TITLE_LEFT_TEXT.value);
            if (styleType != 0) {
                initStyle(Style.getActionType(styleType));

                String titleText = ta.getString(R.styleable.SuperActionBar_super_action_bar_title_text);
                if (TextUtils.isEmpty(titleText) && !isInEditMode()) {
                    titleText = ((Activity) getContext()).getTitle().toString();
                }
                setTitleText(titleText);

                // ---------------------------------------------------------------------------------
                String leftIcon = ta.getString(R.styleable.SuperActionBar_super_action_bar_left_icon);
                setLeftText(!TextUtils.isEmpty(leftIcon) ? leftIcon : getContext().getString(textLeft), true, new OnActionBarLeftClickListener());

                String rightIcon = ta.getString(R.styleable.SuperActionBar_super_action_bar_right_icon);
                setRightText(rightIcon, true, mRightClickListener);

                String leftText = ta.getString(R.styleable.SuperActionBar_super_action_bar_left_text);
                setLeftText(leftText, false, new OnActionBarLeftClickListener());

                String rightText = ta.getString(R.styleable.SuperActionBar_super_action_bar_right_text);
                setRightText(rightText, false, mRightClickListener);

                // ---------------------------------------------------------------------------------
                Drawable leftImg = ta.getDrawable(R.styleable.SuperActionBar_super_action_bar_left_img);
                setLeftImg(leftImg != null ? leftImg : ContextCompat.getDrawable(getContext(), iconLeft), new OnActionBarLeftClickListener());

                Drawable rightImg = ta.getDrawable(R.styleable.SuperActionBar_super_action_bar_right_img);
                setRightImg(rightImg, mRightClickListener);

                // ---------------------------------------------------------------------------------
                @ColorInt int titleTextColor = ta.getColor(R.styleable.SuperActionBar_super_action_bar_title_textColor, -1);
                if (titleTextColor != -1 && tvTitle != null)
                    tvTitle.setTextColor(titleTextColor);

                @ColorInt int leftTextColor = ta.getColor(R.styleable.SuperActionBar_super_action_bar_left_textColor, -1);
                if (leftTextColor != -1 && tvLeft != null)
                    tvLeft.setTextColor(leftTextColor);

                @ColorInt int rightTextColor = ta.getColor(R.styleable.SuperActionBar_super_action_bar_right_textColor, -1);
                if (rightTextColor != -1 && tvRight != null)
                    tvRight.setTextColor(rightTextColor);

                float titleTextSize = ta.getDimension(R.styleable.SuperActionBar_super_action_bar_title_textSize, -1);
                if (titleTextSize != -1 && tvTitle != null)
                    tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);

                float leftTextSize = ta.getDimension(R.styleable.SuperActionBar_super_action_bar_left_textSize, -1);
                if (leftTextSize != -1 && tvLeft != null)
                    tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);

                float rightTextSize = ta.getDimension(R.styleable.SuperActionBar_super_action_bar_right_textSize, -1);
                if (rightTextSize != -1 && tvRight != null)
                    tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);

                Drawable bg = ta.getDrawable(R.styleable.SuperActionBar_super_action_bar_background);
                if (bg != null && commonActionBar != null)
                    commonActionBar.setBackground(bg);

            }
            ta.recycle();
        }
    }

    public interface OnClickListener {
        void onClick(View view);
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        mLeftClickListener = listener;
    }

    public void setOnRightClickListener(OnClickListener listener) {
        mRightClickListener = listener;
    }

    /**
     * 初始化样式
     *
     * @param style
     */
    private SuperActionBar initStyle(Style style) {
        if (!isInEditMode()) {
            StatusBarUtils.setStatusBarHeight((Activity) getContext(), vStatusBar);
            setTitleText(((Activity) getContext()).getTitle().toString());
        }

        llLeftContainer.removeAllViews();
        llRightContainer.removeAllViews();

        switch (style) {
            case TITLE:
                break;
            case TITLE_LEFT_IMG:
                initStyleLeftImg();
                break;
            case TITLE_RIGHT_IMG:
                initStyleRightImg();
                break;
            case TITLE_BOTH_IMG:
                initStyleLeftImg();
                initStyleRightImg();
                break;
            case TITLE_LEFT_TEXT:
                initStyleLeftText();
                break;
            case TITLE_RIGHT_TEXT:
                initStyleRightText();
                break;
            case TITLE_BOTH_TEXT:
                initStyleLeftText();
                initStyleRightText();
                break;
            case TITLE_LEFT_IMG_RIGHT_TEXT:
                initStyleLeftImg();
                initStyleRightText();
                break;
            case TITLE_LEFT_TEXT_RIGHT_IMG:
                initStyleLeftText();
                initStyleRightImg();
                break;
        }
        return this;
    }

    /**
     * 左侧图片按钮
     */
    private void initStyleLeftImg() {
        ivLeft = (ImageView) mInflater.inflate(R.layout.super_action_bar_common_img, null);
        llLeftContainer.addView(ivLeft);
        llLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftClickListener != null)
                    mLeftClickListener.onClick(v);
            }
        });
    }

    /**
     * 右侧图片按钮
     */
    private void initStyleRightImg() {
        ivRight = (ImageView) mInflater.inflate(R.layout.super_action_bar_common_img, null);
        llRightContainer.addView(ivRight);
        llRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightClickListener != null)
                    mRightClickListener.onClick(v);
            }
        });
    }

    /**
     * 左侧文字按钮
     */
    private void initStyleLeftText() {
        tvLeft = (IconTextView) mInflater.inflate(R.layout.super_action_bar_common_text, null);
        llLeftContainer.addView(tvLeft);
        llLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftClickListener != null)
                    mLeftClickListener.onClick(v);
            }
        });
    }

    /**
     * 右侧文字按钮
     */
    private void initStyleRightText() {
        tvRight = (IconTextView) mInflater.inflate(R.layout.super_action_bar_common_text, null);
        llRightContainer.addView(tvRight);
        llRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightClickListener != null)
                    mRightClickListener.onClick(v);
            }
        });
    }
    //</editor-fold>

    //<editor-fold desc="get">

    public View getStatusBar() {
        return vStatusBar;
    }

    public View getActionBar() {
        return commonActionBar;
    }

    public LinearLayout getLeftContainer() {
        return llLeftContainer;
    }

    public LinearLayout getMiddleContainer() {
        return llMiddleContainer;
    }

    public LinearLayout getRightContainer() {
        return llRightContainer;
    }

    public TextView getMiddleTitle() {
        return tvTitle;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public ImageView getIvRight() {
        return ivRight;
    }

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public View getActionBarLine() {
        return vActionBarLine;
    }

    //</editor-fold>


    //<editor-fold desc="set">
    public SuperActionBar setTitleText(CharSequence title) {
        if (tvTitle != null)
            tvTitle.setText(title);
        return this;
    }

    public SuperActionBar setTitleText(@StringRes int resId) {
        if (tvTitle != null)
            tvTitle.setText(resId);
        return this;
    }

    /**
     * 是否打开状态栏高度
     * 默认开启(沉浸式)
     *
     * @param isOpen
     * @return
     */
    public SuperActionBar switchStatusBarHeight(boolean isOpen) {
        if (isOpen) {
            StatusBarUtils.setStatusBarHeight((Activity) getContext(), vStatusBar);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ViewGroup.LayoutParams lp = vStatusBar.getLayoutParams();
                lp.height = 0;
                vStatusBar.setLayoutParams(lp);
            }
        }
        return this;
    }

    public SuperActionBar setLeftImg() {
        return setLeftImg(ContextCompat.getDrawable(getContext(), iconLeft), new OnActionBarLeftClickListener());
    }

    public SuperActionBar setLeftImg(@DrawableRes int resId, OnClickListener listener) {
        return setLeftImg(ContextCompat.getDrawable(getContext(), resId), listener);
    }

    public SuperActionBar setLeftImg(Drawable drawable, OnClickListener listener) {
        if (ivLeft != null && drawable != null) {
            ivLeft.setImageDrawable(drawable);
            setOnLeftClickListener(listener);
            setMiddleContainerMargin(llLeftContainer, true);
        }
        return this;
    }

    public SuperActionBar setRightImg(@DrawableRes int resId, OnClickListener listener) {
        return setRightImg(ContextCompat.getDrawable(getContext(), resId), listener);
    }

    public SuperActionBar setRightImg(Drawable drawable, OnClickListener listener) {
        if (ivRight != null && drawable != null) {
            ivRight.setImageDrawable(drawable);
            setOnRightClickListener(listener);
            setMiddleContainerMargin(llRightContainer, false);
        }
        return this;
    }

    public SuperActionBar setLeftText() {
        return setLeftText(textLeft, true, new OnActionBarLeftClickListener());
    }

    public SuperActionBar setLeftText(@StringRes int resId, boolean isIcon, OnClickListener listener) {
        return setLeftText(getContext().getString(resId), isIcon, listener);
    }

    public SuperActionBar setLeftText(CharSequence text, boolean isIcon, OnClickListener listener) {
        if (tvLeft != null && !TextUtils.isEmpty(text)) {
            tvLeft.setText(text);
            tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(isIcon ? R.dimen.super_action_bar_btn_icon_size : R.dimen.super_action_bar_btn_size));
            setOnLeftClickListener(listener);
            setMiddleContainerMargin(llLeftContainer, true);
        }
        return this;
    }

    public SuperActionBar setRightText(@StringRes int resId, boolean isIcon, OnClickListener listener) {
        return setRightText(getContext().getString(resId), isIcon, listener);
    }

    public SuperActionBar setRightText(CharSequence text, boolean isIcon, OnClickListener listener) {
        if (tvRight != null && !TextUtils.isEmpty(text)) {
            tvRight.setText(text);
            tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(isIcon ? R.dimen.super_action_bar_btn_icon_size : R.dimen.super_action_bar_btn_size));
            setOnRightClickListener(listener);
            setMiddleContainerMargin(llRightContainer, false);
        }
        return this;
    }

    private void setMiddleContainerMargin(final LinearLayout layoutContainer, final boolean isLeft) {
        ViewTreeObserver vto = layoutContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layoutContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) llMiddleContainer.getLayoutParams();
                if (isLeft) {
                    lp.leftMargin = lp.rightMargin <= layoutContainer.getWidth() ? layoutContainer.getWidth() : lp.rightMargin;
                    lp.rightMargin = lp.leftMargin;
                } else {
                    lp.rightMargin = lp.leftMargin <= layoutContainer.getWidth() ? layoutContainer.getWidth() : lp.leftMargin;
                    lp.leftMargin = lp.rightMargin;
                }
                llMiddleContainer.setLayoutParams(lp);
            }
        });
    }

    public SuperActionBar setBothListener(OnClickListener leftListener, OnClickListener rightListener) {
        setOnLeftClickListener(leftListener);
        setOnRightClickListener(rightListener);
        return this;
    }

    //</editor-fold>

    //<editor-fold desc="java use">

    public SuperActionBar initStyleToTitle(CharSequence title) {
        return initStyle(Style.TITLE).setTitleText(title);
    }

    public SuperActionBar initStyleToTitle(@StringRes int resId) {
        return initStyle(Style.TITLE).setTitleText(resId);
    }

    public SuperActionBar initStyleToLeftImg() {
        return initStyle(Style.TITLE_LEFT_IMG);
    }

    public SuperActionBar initStyleToRightImg() {
        return initStyle(Style.TITLE_RIGHT_IMG);
    }

    public SuperActionBar initStyleToBothImg() {
        return initStyle(Style.TITLE_BOTH_IMG);
    }

    public SuperActionBar initStyleToLeftText() {
        return initStyle(Style.TITLE_LEFT_TEXT);
    }

    public SuperActionBar initStyleToRightText() {
        return initStyle(Style.TITLE_RIGHT_TEXT);
    }

    public SuperActionBar initStyleToBothText() {
        return initStyle(Style.TITLE_BOTH_TEXT);
    }

    public SuperActionBar initStyleToLeftImgRightText() {
        return initStyle(Style.TITLE_LEFT_IMG_RIGHT_TEXT);
    }

    public SuperActionBar initStyleToLeftTextRightImg() {
        return initStyle(Style.TITLE_LEFT_TEXT_RIGHT_IMG);
    }

    /**
     * ActionBar左侧按钮的点击事件
     */
    public static class OnActionBarLeftClickListener implements SuperActionBar.OnClickListener {

        @Override
        public void onClick(View view) {
            ((Activity) view.getContext()).onBackPressed();
        }
    }

    //</editor-fold>

}
