package com.team28.borrow.util.custom_view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

public class UnicodeHelper {

    private static Boolean isUnicode = null;

    public static void init(Context context) {
        TextView tv = new TextView(context);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText("\u1000");

        tv.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int length1 = tv.getMeasuredWidth();

        tv.setText("\u1000\u1039\u1000");
        tv.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int length2 = tv.getMeasuredWidth();

        isUnicode = length1 == length2;
    }

    public static boolean isUnicode() {
        if (isUnicode == null)
            throw new UnsupportedOperationException("UniCodeHelper was not initialized.");
        return isUnicode;
    }

    public static String getText(String unicodeString) {
        return (isUnicode()) ? unicodeString : Rabbit.uni2zg(unicodeString);
    }

    public static String getZawgyiString(String unicodeString) {
        return (isUnicode()) ? unicodeString : Rabbit.uni2zg(unicodeString);
    }

    public static String getUnicodeString(String zgString) {
        return (isUnicode()) ? Rabbit.zg2uni(zgString) : zgString;
    }

    public static String getUnicode(String string){
        return (isUnicode())?string:Rabbit.zg2uni((string));
    }

    public static String getAppropriateString(String zgString) {
        return (isUnicode()) ? Rabbit.zg2uni(zgString) : zgString;
    }


}
