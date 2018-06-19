package com.franvara.ricklantis.presentation.utils.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.LinearLayout;

@CoordinatorLayout.DefaultBehavior(MoveUpwardBehavior.class)
public class CustomLinearLayoutWithUpwardBehavior extends LinearLayout {
    public CustomLinearLayoutWithUpwardBehavior(Context context) {
        super(context);
    }

    public CustomLinearLayoutWithUpwardBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayoutWithUpwardBehavior(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}