package com.team28.borrow.util.custom_view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class MMButtonView extends AppCompatButton {

    public MMButtonView(Context context) {
        super(context);
    }

    public MMButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MMButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence c, BufferType type) {
        super.setText(UnicodeHelper.getText(c.toString()), type);
    }
}
