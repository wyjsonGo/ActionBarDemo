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
import android.widget.ImageButton;
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
 * 不带iconfont的版本
 *
 * @author Wyjson
 * @version 4
 * @date 2021-03-29 11:16
 */
public class SuperActionBar2 extends RelativeLayout {

    private LayoutInflater mInflater;

    private View vStatusBar;
    private View commonActionBar;
    private LinearLayout llLeftContainer, llMiddleContainer, llRightContainer;
    private TextView tvTitle;
    private View vActionBarLine;

    private OnClickListener mLeftClickListener, mRightClickListener;

    private ImageButton leftImgBtn, rightImgBtn;

    private TextView leftTxtBtn, rightTxtBtn;

    private final @DrawableRes
    int iconLeft = R.drawable.super_action_bar_back_white_selector;

    public enum Style {// 样式
        TITLE_ONLY(0x01),

        TITLE_LEFT_IMG(0x02),
        TITLE_RIGHT_IMG(0x03),
        TITLE_BOTH_IMG(0x04),

        TITLE_LEFT_TXT(0x05),
        TITLE_RIGHT_TXT(0x06),
        TITLE_BOTH_TXT(0x07),

        TITLE_LEFT_IMG_RIGHT_TXT(0x08),
        TITLE_LEFT_TXT_RIGHT_IMG(0x09);

        private final int value;

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

    public SuperActionBar2(Context context) {
        this(context, null);
    }

    public SuperActionBar2(Context context, AttributeSet attrs) {
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
            int styleType = ta.getInteger(R.styleable.SuperActionBar_super_action_bar_style, 0);
            if (styleType != 0) {
                initStyle(Style.getActionType(styleType));

                String titleText = ta.getString(R.styleable.SuperActionBar_super_action_bar_title_text);
                setTitleText(titleText);

                // ---------------------------------------------------------------------------------
                String leftText = ta.getString(R.styleable.SuperActionBar_super_action_bar_left_text);
                setLeftTxtBtn(leftText, new OnActionBarLeftClickListener());

                String rightText = ta.getString(R.styleable.SuperActionBar_super_action_bar_right_text);
                setRightTxtBtn(rightText, null);

                // ---------------------------------------------------------------------------------
                Drawable leftImg = ta.getDrawable(R.styleable.SuperActionBar_super_action_bar_left_img);
                setLeftImgBtn(leftImg != null ? leftImg : ContextCompat.getDrawable(getContext(), iconLeft), new OnActionBarLeftClickListener());

                Drawable rightImg = ta.getDrawable(R.styleable.SuperActionBar_super_action_bar_right_img);
                setLeftImgBtn(rightImg, new OnActionBarLeftClickListener());

                // ---------------------------------------------------------------------------------
                @ColorInt int leftTextColor = ta.getColor(R.styleable.SuperActionBar_super_action_bar_left_textColor, 0);
                if (leftTextColor != 0 && leftTxtBtn != null)
                    leftTxtBtn.setTextColor(leftTextColor);

                @ColorInt int rightTextColor = ta.getColor(R.styleable.SuperActionBar_super_action_bar_right_textColor, 0);
                if (rightTextColor != 0 && rightTxtBtn != null)
                    rightTxtBtn.setTextColor(rightTextColor);

                @ColorInt int titleTextColor = ta.getColor(R.styleable.SuperActionBar_super_action_bar_title_textColor, 0);
                if (titleTextColor != 0 && tvTitle != null)
                    tvTitle.setTextColor(titleTextColor);

                float leftTextSize = ta.getDimension(R.styleable.SuperActionBar_super_action_bar_left_textSize, 0);
                if (leftTextSize != 0 && leftTxtBtn != null)
                    leftTxtBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, leftTextSize);

                float rightTextSize = ta.getDimension(R.styleable.SuperActionBar_super_action_bar_right_textSize, 0);
                if (rightTextSize != 0 && rightTxtBtn != null)
                    rightTxtBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);

                float titleTextSize = ta.getDimension(R.styleable.SuperActionBar_super_action_bar_title_textSize, 0);
                if (titleTextSize != 0 && tvTitle != null)
                    tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);

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
    public SuperActionBar2 initStyle(Style style) {
        if (!isInEditMode()) {
            StatusBarUtils.setStatusBarHeight((Activity) getContext(), vStatusBar);
        }
        initStyleTitle();
        switch (style) {
            case TITLE_ONLY:
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
            case TITLE_LEFT_TXT:
                initStyleLeftTxt();
                break;
            case TITLE_RIGHT_TXT:
                initStyleRightTxt();
                break;
            case TITLE_BOTH_TXT:
                initStyleLeftTxt();
                initStyleRightTxt();
                break;
            case TITLE_LEFT_IMG_RIGHT_TXT:
                initStyleLeftImg();
                initStyleRightTxt();
                break;
            case TITLE_LEFT_TXT_RIGHT_IMG:
                initStyleLeftTxt();
                initStyleRightImg();
                break;
        }
        return this;
    }

    /**
     * 默认文字标题
     */
    private void initStyleTitle() {
        llLeftContainer.removeAllViews();
        llRightContainer.removeAllViews();
    }

    /**
     * 左侧图片按钮
     */
    private void initStyleLeftImg() {
        leftImgBtn = (ImageButton) mInflater.inflate(R.layout.super_action_bar_common_left_right_img_btn, null);
        llLeftContainer.addView(leftImgBtn);
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
        rightImgBtn = (ImageButton) mInflater.inflate(R.layout.super_action_bar_common_left_right_img_btn, null);
        llRightContainer.addView(rightImgBtn);
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
    private void initStyleLeftTxt() {
        leftTxtBtn = (TextView) mInflater.inflate(R.layout.super_action_bar_common_left_right_txt_btn, null);
        llLeftContainer.addView(leftTxtBtn);
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
    private void initStyleRightTxt() {
        rightTxtBtn = (TextView) mInflater.inflate(R.layout.super_action_bar_common_left_right_txt_btn, null);
        llRightContainer.addView(rightTxtBtn);
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

    public ImageButton getLeftImgBtn() {
        return leftImgBtn;
    }

    public ImageButton getRightImgBtn() {
        return rightImgBtn;
    }

    public TextView getLeftTxtBtn() {
        return leftTxtBtn;
    }

    public TextView getRightTxtBtn() {
        return rightTxtBtn;
    }

    public View getActionBarLine() {
        return vActionBarLine;
    }

    //</editor-fold>


    //<editor-fold desc="set">
    public SuperActionBar2 setTitleText(CharSequence title) {
        if (tvTitle != null)
            tvTitle.setText(title);
        return this;
    }

    /**
     * 是否打开状态栏高度
     * 默认开启(沉浸式)
     *
     * @param isOpen
     * @return
     */
    public SuperActionBar2 switchStatusBarHeight(boolean isOpen) {
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

    public SuperActionBar2 setLeftImgBtn(@DrawableRes int resId, OnClickListener listener) {
        return setLeftImgBtn(ContextCompat.getDrawable(getContext(), resId), listener);
    }

    public SuperActionBar2 setLeftImgBtn(Drawable drawable, OnClickListener listener) {
        if (leftImgBtn != null && drawable != null) {
            leftImgBtn.setImageDrawable(drawable);
            setOnLeftClickListener(listener);
            setMiddleContainerMargin(llLeftContainer, true);
        }
        return this;
    }

    public SuperActionBar2 setRightImgBtn(@DrawableRes int resId, OnClickListener listener) {
        return setRightImgBtn(ContextCompat.getDrawable(getContext(), resId), listener);
    }

    public SuperActionBar2 setRightImgBtn(Drawable drawable, OnClickListener listener) {
        if (rightImgBtn != null && drawable != null) {
            rightImgBtn.setImageDrawable(drawable);
            setOnRightClickListener(listener);
            setMiddleContainerMargin(llRightContainer, false);
        }
        return this;
    }

    public SuperActionBar2 setLeftTxtBtn(@StringRes int resId, OnClickListener listener) {
        return setLeftTxtBtn(getContext().getString(resId), listener);
    }

    public SuperActionBar2 setLeftTxtBtn(CharSequence text, OnClickListener listener) {
        if (leftTxtBtn != null && !TextUtils.isEmpty(text)) {
            leftTxtBtn.setText(text);
            leftTxtBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, R.dimen.super_action_bar_btn_size);
            setOnLeftClickListener(listener);
            setMiddleContainerMargin(llLeftContainer, true);
        }
        return this;
    }

    public SuperActionBar2 setRightTxtBtn(@StringRes int resId, OnClickListener listener) {
        return setRightTxtBtn(getContext().getString(resId), listener);
    }

    public SuperActionBar2 setRightTxtBtn(CharSequence text, OnClickListener listener) {
        if (rightTxtBtn != null && !TextUtils.isEmpty(text)) {
            rightTxtBtn.setText(text);
            rightTxtBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.super_action_bar_btn_size));
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
                LayoutParams lp = (LayoutParams) llMiddleContainer.getLayoutParams();
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

    public SuperActionBar2 setBothListener(OnClickListener leftListener, OnClickListener rightListener) {
        setOnLeftClickListener(leftListener);
        setOnRightClickListener(rightListener);
        return this;
    }

    //</editor-fold>

    //<editor-fold desc="java use">

    /**
     * 只有标题
     *
     * @param title
     */
    public void initActionBarForOnlyTitle(CharSequence title) {
        initStyle(SuperActionBar2.Style.TITLE_ONLY)
                .setTitleText(title);
    }

    /**
     * 标题 + 左侧Icon按钮
     *
     * @param title
     */
    public void initActionBarForLeftIcon(CharSequence title) {
        initActionBarForLeftIcon(title, iconLeft, new OnActionBarLeftClickListener());
    }

    /**
     * 标题 + 左侧Icon按钮 + 点击事件
     *
     * @param title
     * @param icon
     * @param listener
     */
    public void initActionBarForLeftIcon(CharSequence title, @DrawableRes int icon, SuperActionBar2.OnClickListener listener) {
        initStyle(Style.TITLE_LEFT_IMG)
                .setTitleText(title)
                .setLeftImgBtn(icon != 0 ? icon : iconLeft, listener);
    }

    /**
     * 标题 + 右侧Icon按钮 + 点击事件
     *
     * @param title
     * @param icon
     * @param listener
     */
    public void initActionBarForRightIcon(CharSequence title, @StringRes int icon, SuperActionBar2.OnClickListener listener) {
        initStyle(SuperActionBar2.Style.TITLE_RIGHT_TXT)
                .setTitleText(title)
                .setRightTxtBtn(icon, listener);
    }

    /**
     * 标题 + 右侧文字按钮 + 点击事件
     *
     * @param title
     * @param resId
     * @param listener
     */
    public void initActionBarForRightTxt(CharSequence title, @StringRes int resId, SuperActionBar2.OnClickListener listener) {
        initStyle(SuperActionBar2.Style.TITLE_RIGHT_TXT)
                .setTitleText(title)
                .setRightTxtBtn(resId, listener);
    }

    /**
     * 标题 + 两侧文字按钮 + 点击事件
     *
     * @param title
     * @param leftListener
     * @param rightResId
     * @param rightListener
     */
    public void initActionBarForBothTxt(
            CharSequence title,
            SuperActionBar2.OnClickListener leftListener,
            @StringRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initActionBarForBothLeftImgRightTxt(title, iconLeft, leftListener, rightResId, rightListener);
    }

    public void initActionBarForBothTxt(CharSequence title, @StringRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initActionBarForBothTxt(title, new OnActionBarLeftClickListener(), rightResId, rightListener);
    }

    public void initActionBarForBothIcon(CharSequence title, @StringRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initActionBarForBothTxt(title, new OnActionBarLeftClickListener(), rightResId, rightListener);
    }

    /**
     * 标题 + 两侧文字按钮 + 点击事件
     *
     * @param title
     * @param leftResId
     * @param leftListener
     * @param rightResId
     * @param rightListener
     */
    public void initActionBarForBothTxt(
            CharSequence title,
            @StringRes int leftResId, SuperActionBar2.OnClickListener leftListener,
            @StringRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initStyle(SuperActionBar2.Style.TITLE_BOTH_TXT)
                .setTitleText(title)
                .setLeftTxtBtn(leftResId, leftListener)
                .setRightTxtBtn(rightResId, rightListener);
    }

    /**
     * 标题 + 左侧图片按钮
     *
     * @param title
     */
    public void initActionBarForLeftImg(CharSequence title) {
        initActionBarForLeftImg(title, iconLeft, new OnActionBarLeftClickListener());
    }

    /**
     * 标题 + 左侧图片按钮 + 点击事件
     *
     * @param title
     * @param resId
     * @param listener
     */
    public void initActionBarForLeftImg(CharSequence title, @DrawableRes int resId, SuperActionBar2.OnClickListener listener) {
        initStyle(SuperActionBar2.Style.TITLE_LEFT_IMG)
                .setTitleText(title)
                .setLeftImgBtn(resId, listener);
    }

    /**
     * 标题 + 右侧图片按钮 + 点击事件
     *
     * @param title
     * @param resId
     * @param promptResId
     * @param listener
     */
    public void initActionBarForRightImg(CharSequence title, @DrawableRes int resId, @StringRes int promptResId, SuperActionBar2.OnClickListener listener) {
        initStyle(SuperActionBar2.Style.TITLE_RIGHT_IMG)
                .setTitleText(title)
                .setRightImgBtn(resId, listener);
    }

    public void initActionBarForBothImg(
            CharSequence title,
            @DrawableRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initActionBarForBothImg(title, iconLeft, new SuperActionBar2.OnActionBarLeftClickListener(), rightResId, rightListener);
    }

    /**
     * 标题 + 两侧图片按钮 + 点击事件
     *
     * @param title
     * @param leftResId
     * @param leftListener
     * @param rightResId
     * @param rightListener
     */
    public void initActionBarForBothImg(
            CharSequence title,
            @DrawableRes int leftResId, SuperActionBar2.OnClickListener leftListener,
            @DrawableRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initStyle(SuperActionBar2.Style.TITLE_BOTH_IMG)
                .setTitleText(title)
                .setLeftImgBtn(leftResId, leftListener)
                .setRightImgBtn(rightResId, rightListener);
    }

    public void initActionBarForBothRightImg(
            CharSequence title,
            @DrawableRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initActionBarForBothImg(title, iconLeft, new SuperActionBar2.OnActionBarLeftClickListener(), rightResId, rightListener);
    }

    /**
     * 标题 + 左侧文字,右侧图片按钮 + 点击事件
     *
     * @param title
     * @param leftResId
     * @param leftListener
     * @param rightResId
     * @param rightListener
     */
    public void initActionBarForBothLeftTxtRightImg(
            CharSequence title,
            @StringRes int leftResId, SuperActionBar2.OnClickListener leftListener,
            @DrawableRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initStyle(SuperActionBar2.Style.TITLE_LEFT_TXT_RIGHT_IMG)
                .setTitleText(title)
                .setLeftTxtBtn(leftResId, leftListener)
                .setRightImgBtn(rightResId, rightListener);
    }

    public void initActionBarForBothRightTxt(
            CharSequence title,
            @StringRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initActionBarForBothLeftImgRightTxt(title, iconLeft, new SuperActionBar2.OnActionBarLeftClickListener(), rightResId, rightListener);
    }

    /**
     * 标题 + 左侧图片按钮,右侧文字 + 点击事件
     *
     * @param title
     * @param leftResId
     * @param leftListener
     * @param rightResId
     * @param rightListener
     */
    public void initActionBarForBothLeftImgRightTxt(
            CharSequence title,
            @DrawableRes int leftResId, SuperActionBar2.OnClickListener leftListener,
            @StringRes int rightResId, SuperActionBar2.OnClickListener rightListener) {
        initStyle(Style.TITLE_LEFT_IMG_RIGHT_TXT)
                .setTitleText(title)
                .setLeftImgBtn(leftResId, leftListener)
                .setRightTxtBtn(rightResId, rightListener);
    }

    /**
     * ActionBar左侧按钮的点击事件
     */
    public static class OnActionBarLeftClickListener implements SuperActionBar2.OnClickListener {

        @Override
        public void onClick(View view) {
            ((Activity) view.getContext()).finish();
        }
    }

    //</editor-fold>

}
