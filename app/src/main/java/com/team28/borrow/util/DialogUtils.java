package com.team28.borrow.util;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DialogUtils {
    private static int mYear, mMonth, mDay, mHour, mMinute;

    public interface onDateSetComplete {
        public void date(String s);

        public void error(String s);
    }


    public static void showDatePicker(Context context, onDateSetComplete onDateSetComplete) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) -> {
                    if (dayOfMonth >= mDay)
                        onDateSetComplete.date(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    else
                        onDateSetComplete.error("Pls choose date correctly");
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public static void
    showMessageOKCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
