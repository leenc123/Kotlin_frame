package com.leen.model_mine.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.HashMap;

/**
 * @author 创建人：Administrator
 * 创建日期：2019/6/5
 * 描述：
 */
public class MyScrollview extends NestedScrollView {
    public MyScrollview(@NonNull Context context) {
        super(context);
    }

    public MyScrollview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return true;
    }
}
