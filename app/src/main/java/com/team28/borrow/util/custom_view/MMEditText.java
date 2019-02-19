package com.team28.borrow.util.custom_view;

import android.content.Context;
import android.util.AttributeSet;


public class MMEditText extends android.support.v7.widget.AppCompatEditText {

    private String str;


    public MMEditText(Context context) {
        super(context);
        setHint(UnicodeHelper.getText(getHint().toString()));

    }

    public MMEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHint(UnicodeHelper.getText(getHint().toString()));
    }

    public MMEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHint(UnicodeHelper.getText(getHint().toString()));

    }

    public void setMMText(String uniCode, JobExecutor executor) {
        if (executor == null) {
            str = UnicodeHelper.getText(uniCode);

        } else {
            executor.execute(() -> {
                        String convertedText = UnicodeHelper.getText(uniCode);
                        post(() -> str = convertedText);

                    }
            );
        }
    }


    @Override
    public void setText(CharSequence text, BufferType type) {
        setMMText(text.toString(), null);
        super.setText(str, type);
    }
}
