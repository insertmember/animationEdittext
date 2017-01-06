package com.tongna.animationed.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tongna.animationed.R;


/**
 * Created by hxf on 2017/1/4.
 * 自定义输入框
 *
 */
public class EditTextAnimation extends LinearLayout {
    private MyEditText mEditText;
    private TextView mTextView;
    private Animation tvShow;
    private Animation tvHide;
    private Context context;
    private String text;
    private AlphaAnimation edHide = null;
    private AlphaAnimation edShow = null;

    public EditTextAnimation(Context context) {
        super(context);
        this.context = context;
    }

    public EditTextAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public EditTextAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }


    public void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.edittext_item, this);
        mEditText = (MyEditText) findViewById(R.id.edittext_item_ed);
        mTextView = (TextView) findViewById(R.id.edittext_item_tv);
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.edittext_animation);
        final String tvHint = array.getString(R.styleable.edittext_animation_texthint);
        final int tvColor = array.getColor(R.styleable.edittext_animation_texthintcolor, getResources().getColor(R.color.arg_333333));
        final int tvSize = array.getInteger(R.styleable.edittext_animation_texthinttextsize, 16);

        mTextView.setText(tvHint);
        mTextView.setTextColor(tvColor);
        mTextView.setTextSize(tvSize);

        final String edHint = array.getString(R.styleable.edittext_animation_edithint);
        final int edHintColor = array.getColor(R.styleable.edittext_animation_edithintcolor, getResources().getColor(R.color.arg_333333));
        int edHintSize = array.getInteger(R.styleable.edittext_animation_edittextsize, 16);
        int edTextColor = array.getColor(R.styleable.edittext_animation_edittextcolor, getResources().getColor(R.color.arg_333333));
        mEditText.setHint(edHint);
        mEditText.setHintTextColor(edHintColor);
        mEditText.setTextSize(edHintSize);
        mEditText.setTextColor(edTextColor);

        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mTextView.setVisibility(VISIBLE);
                    if ("".equals(text)) {
                        setShowAnimation(mTextView);

                        setHideAnimation(mEditText, 500);
                    }
//                    RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) mEditText.getLayoutParams();
//                    layout.leftMargin = mTextView.getWidth() + mTextView.getLeft();

                } else {
                    text = mEditText.getText().toString();
                    if ("".equals(text)) {
                        mTextView.setVisibility(GONE);
                        setHideAnimation(mTextView);
                        mEditText.setGravity(Gravity.CENTER);
                        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        mEditText.setLayoutParams(layout);
                        mEditText.setPadding(0, 0, 0, 0);
                        mEditText.setHint(edHint);
                        setShowAnimation(mEditText, 500);
                        mEditText.setHintTextColor(edHintColor);
                    }else{
                        int left = 365;
                        mEditText.setPadding(left, 0, 0, 0);
                        mEditText.setGravity(Gravity.LEFT | Gravity.CENTER);
                        mEditText.setVisibility(VISIBLE);
                        mEditText.clearAnimation();
                    }
                }
            }
        });


        array.recycle();
    }

    public String getText() {
        return mEditText.getText().toString();
    }

    //ed渐显隐藏动画
    private void setShowAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }
        if (null != edShow) {
            edShow.cancel();
        }
        edShow = new AlphaAnimation(0.0f, 1.0f);
        edShow.setDuration(duration);
        edShow.setFillAfter(true);
        view.startAnimation(edShow);
    }

    //ed渐渐隐藏动画
    private void setHideAnimation(View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }
        if (null != edHide) {
            edHide.cancel();
        }
        edHide = new AlphaAnimation(1.0f, 0.0f);
        edHide.setDuration(duration);
        edHide.setFillAfter(true);
        edHide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = 365;
                mEditText.setPadding(left, 0, 0, 0);
                mEditText.setGravity(Gravity.LEFT | Gravity.CENTER);
                mEditText.setHint("");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(edHide);
    }

    //tv渐隐动画效果
    private void setHideAnimation(View view) {
        if (null == tvHide) {
            tvHide = AnimationUtils.loadAnimation(context, R.anim.edit_tv_hide);
        }
        if (null != view) {
            view.startAnimation(tvHide);
        }

    }

    //tv渐现动画效果
    private void setShowAnimation(View view) {
        if (null == tvShow) {
            tvShow = AnimationUtils.loadAnimation(context, R.anim.edit_tv_shou);
        }
        if (null != view) {
            view.startAnimation(tvShow);
        }
    }
}
