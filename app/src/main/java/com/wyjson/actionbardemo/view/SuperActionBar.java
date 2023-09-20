package com.wyjson.actionbardemo.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
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
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.wyjson.actionbardemo.R;
import com.wyjson.actionbardemo.utils.StatusBarUtils;

/**
 * 超级活动栏
 *
 * @author Wyjson
 * @version 6
 */
public class SuperActionBar extends LinearLayout {

    private View vStatusBar;
    private View commonActionBar;
    private LinearLayout llLeftContainer, llMiddleContainer, llRightContainer;
    private TextView tvTitle;
    private View vActionBarLine;

    private OnClickListener mLeftClickListener, mRightClickListener;

    private ImageView ivLeft, ivRight;

    private TextView tvLeft, tvRight;

    private boolean isTitleCenter = true;

    private final @DrawableRes
    int imgLeft = R.drawable.super_action_bar_back_theme_selector;

    public SuperActionBar(Context context) {
        this(context, null);
    }

    public SuperActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    //<editor-fold desc="init">
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.super_action_bar_common_basics, this);
        setClickable(true);// 不让点击事件透过bar
        initView();
        initAttr(context, attrs);
        initSetting();
    }

    private void initView() {
        vStatusBar = findViewById(R.id.v_status_bar);

        commonActionBar = findViewById(R.id.common_action_bar);

        llLeftContainer = commonActionBar.findViewById(R.id.ll_container_left);
        llMiddleContainer = commonActionBar.findViewById(R.id.ll_container_middle);
        tvTitle = llMiddleContainer.findViewById(R.id.super_action_bar_middle_tv_title);
        llRightContainer = commonActionBar.findViewById(R.id.ll_container_right);

        vActionBarLine = findViewById(R.id.v_action_bar_line);

        llLeftContainer.setOnClickListener(v -> {
            if (mLeftClickListener != null)
                mLeftClickListener.onClick(v);
        });
        llRightContainer.setOnClickListener(v -> {
            if (mRightClickListener != null)
                mRightClickListener.onClick(v);
        });
    }

    private void initSetting() {
        if (!isInEditMode()) {
            switchStatusBarHeight(true);
            setTitleText(((Activity) getContext()).getTitle().toString());
        }
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.super_action_bar_background_color));
        float elevation = getContext().getResources().getDimension(R.dimen.super_action_bar_elevation);
        if (elevation != 0)
            setElevation(elevation);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SuperActionBar);

            String titleText = ta.getString(R.styleable.SuperActionBar_super_action_bar_title_text);
            if (!TextUtils.isEmpty(titleText))
                setTitleText(titleText);

            // ---------------------------------------------------------------------------------
            String leftText = ta.getString(R.styleable.SuperActionBar_super_action_bar_left_text);
            if (!TextUtils.isEmpty(leftText))
                setLeftText(leftText, new OnActionBarLeftClickListener());

            String rightText = ta.getString(R.styleable.SuperActionBar_super_action_bar_right_text);
            if (!TextUtils.isEmpty(rightText))
                setRightText(rightText, mRightClickListener);

            // ---------------------------------------------------------------------------------
            Drawable leftImg = ta.getDrawable(R.styleable.SuperActionBar_super_action_bar_left_img);
            if (leftImg != null)
                setLeftImg(leftImg, new OnActionBarLeftClickListener());

            Drawable rightImg = ta.getDrawable(R.styleable.SuperActionBar_super_action_bar_right_img);
            if (rightImg != null)
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

            ta.recycle();
        }
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

    public interface OnClickListener {
        void onClick(View view);
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        mLeftClickListener = listener;
    }

    public void setOnRightClickListener(OnClickListener listener) {
        mRightClickListener = listener;
    }

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
            ViewGroup.LayoutParams lp = vStatusBar.getLayoutParams();
            lp.height = 0;
            vStatusBar.setLayoutParams(lp);
        }
        return this;
    }

    /**
     * 靠前调用,要在设置left和right之前调用
     *
     * @param isOpen
     * @return
     */
    public SuperActionBar switchTitleCenter(boolean isOpen) {
        isTitleCenter = isOpen;
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) llMiddleContainer.getLayoutParams();
        if (isTitleCenter) {
            llMiddleContainer.setGravity(Gravity.CENTER);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        } else {
            llMiddleContainer.setGravity(Gravity.CENTER_VERTICAL + Gravity.START);
            lp.removeRule(RelativeLayout.CENTER_IN_PARENT);
        }
        llMiddleContainer.setLayoutParams(lp);
        return this;
    }

    public SuperActionBar addView(ViewGroup parentView, View view) {
        parentView.addView(view);
        refreshMiddleContainerMargin((LinearLayout) parentView);
        return this;
    }

    public SuperActionBar addLeftView(View view) {
        return addView(getLeftContainer(), view);
    }

    public SuperActionBar addMiddleView(View view) {
        return addView(getMiddleContainer(), view);
    }

    public SuperActionBar addRightView(View view) {
        return addView(getRightContainer(), view);
    }

    public SuperActionBar setView(ViewGroup parentView, View view) {
        return setView(parentView, view, null);
    }

    public SuperActionBar setView(ViewGroup parentView, View view, ViewConsumer viewConsumer) {
        parentView.removeAllViews();
        parentView.addView(view);
        refreshMiddleContainerMargin((LinearLayout) parentView);
        if (viewConsumer != null)
            viewConsumer.accept(view);
        return this;
    }

    public SuperActionBar setLeftView(View view) {
        return setView(getLeftContainer(), view);
    }

    public SuperActionBar setMiddleView(View view) {
        return setView(getMiddleContainer(), view);
    }

    public SuperActionBar setMiddleView(@LayoutRes int resource, ViewConsumer viewConsumer) {
        View view = LayoutInflater.from(getContext()).inflate(resource, getMiddleContainer(), false);
        return setView(getMiddleContainer(), view, viewConsumer);
    }

    public interface ViewConsumer {
        void accept(View view);
    }

    public SuperActionBar setRightView(View view) {
        return setView(getRightContainer(), view);
    }

    public SuperActionBar setLeftImg() {
        return setLeftImg(imgLeft, new OnActionBarLeftClickListener());
    }

    public SuperActionBar setLeftImg(@DrawableRes int resId, OnClickListener listener) {
        return setLeftImg(ContextCompat.getDrawable(getContext(), resId), listener);
    }

    public SuperActionBar setLeftImg(Drawable drawable, OnClickListener listener) {
        llLeftContainer.removeAllViews();
        if (ivLeft == null)
            ivLeft = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.super_action_bar_common_img, llLeftContainer, false);
        llLeftContainer.addView(ivLeft);

        if (drawable != null) {
            ivLeft.setImageDrawable(drawable);
            setOnLeftClickListener(listener);
            refreshMiddleContainerMargin(getLeftContainer());
        }
        return this;
    }

    public SuperActionBar setRightImg(@DrawableRes int resId, OnClickListener listener) {
        return setRightImg(ContextCompat.getDrawable(getContext(), resId), listener);
    }

    public SuperActionBar setRightImg(Drawable drawable, OnClickListener listener) {
        llRightContainer.removeAllViews();
        if (ivRight == null)
            ivRight = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.super_action_bar_common_img, llRightContainer, false);
        llRightContainer.addView(ivRight);

        if (drawable != null) {
            ivRight.setImageDrawable(drawable);
            setOnRightClickListener(listener);
            refreshMiddleContainerMargin(getRightContainer());
        }
        return this;
    }

    public SuperActionBar setLeftText(@StringRes int resId) {
        return setLeftText(resId, new OnActionBarLeftClickListener());
    }

    public SuperActionBar setLeftText(@StringRes int resId, OnClickListener listener) {
        return setLeftText(getContext().getString(resId), listener);
    }

    public SuperActionBar setLeftText(CharSequence text) {
        return setLeftText(text, new OnActionBarLeftClickListener());
    }

    public SuperActionBar setLeftText(CharSequence text, OnClickListener listener) {
        llLeftContainer.removeAllViews();
        if (tvLeft == null)
            tvLeft = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.super_action_bar_common_text, llLeftContainer, false);
        llLeftContainer.addView(tvLeft);

        if (!TextUtils.isEmpty(text)) {
            tvLeft.setText(text);
            setOnLeftClickListener(listener);
            refreshMiddleContainerMargin(getLeftContainer());
        }
        return this;
    }

    public SuperActionBar setRightText(@StringRes int resId, OnClickListener listener) {
        return setRightText(getContext().getString(resId), listener);
    }

    public SuperActionBar setRightText(CharSequence text, OnClickListener listener) {
        llRightContainer.removeAllViews();
        if (tvRight == null)
            tvRight = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.super_action_bar_common_text, llRightContainer, false);
        llRightContainer.addView(tvRight);

        if (!TextUtils.isEmpty(text)) {
            tvRight.setText(text);
            setOnRightClickListener(listener);
            refreshMiddleContainerMargin(getRightContainer());
        }
        return this;
    }

    private void refreshMiddleContainerMargin(final LinearLayout layoutContainer) {
        String tag = (String) layoutContainer.getTag();
        if (!TextUtils.equals(tag, "left") && !TextUtils.equals(tag, "right")) {
            return;
        }
        ViewTreeObserver vto = layoutContainer.getViewTreeObserver();
        if (isTitleCenter) {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    layoutContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) llMiddleContainer.getLayoutParams();
                    if (TextUtils.equals(tag, "left")) {
                        lp.leftMargin = lp.rightMargin <= layoutContainer.getWidth() ? layoutContainer.getWidth() : lp.rightMargin;
                        lp.rightMargin = lp.leftMargin;
                    } else {
                        lp.rightMargin = lp.leftMargin <= layoutContainer.getWidth() ? layoutContainer.getWidth() : lp.leftMargin;
                        lp.leftMargin = lp.rightMargin;
                    }
                    llMiddleContainer.setLayoutParams(lp);
                }
            });
        } else {
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    layoutContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) llMiddleContainer.getLayoutParams();
                    if (TextUtils.equals(tag, "left")) {
                        lp.leftMargin = layoutContainer.getWidth();
                    } else {
                        lp.rightMargin = layoutContainer.getWidth();
                    }
                    llMiddleContainer.setLayoutParams(lp);
                }
            });
        }
    }

    public SuperActionBar setBothListener(OnClickListener leftListener, OnClickListener rightListener) {
        setOnLeftClickListener(leftListener);
        setOnRightClickListener(rightListener);
        return this;
    }

    //</editor-fold>

    /**
     * ActionBar左侧按钮的点击事件
     */
    public static class OnActionBarLeftClickListener implements OnClickListener {

        @Override
        public void onClick(View view) {
            ((Activity) view.getContext()).onBackPressed();
        }
    }

}