package com.team28.borrow.util.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;



public class MMTextView extends TextView {
    private String str;

    public MMTextView(Context context) {
        super(context);

    }

    public MMTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MMTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

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
