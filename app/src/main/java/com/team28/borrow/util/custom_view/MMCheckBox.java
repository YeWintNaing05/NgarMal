package com.team28.borrow.util.custom_view;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

public class MMCheckBox extends AppCompatCheckBox {
    public MMCheckBox(Context context) {
        super(context);
    }

    public MMCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MMCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(UnicodeHelper.getText(text.toString()), type);
    }
}
