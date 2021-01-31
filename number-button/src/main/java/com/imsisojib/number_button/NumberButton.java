package com.imsisojib.number_button;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class NumberButton extends RelativeLayout {
    private Context mContext;
    private AttributeSet attrs;
    private int styleAttr;
    private View view;
    public NumberButton(Context context) {
        super(context);
    }

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
