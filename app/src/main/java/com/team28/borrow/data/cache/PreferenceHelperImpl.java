package com.team28.borrow.data.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.team28.borrow.di.PerferenceInfo;
import com.team28.borrow.util.Constants;

import javax.inject.Inject;

public class PreferenceHelperImpl implements PreferenceHelper {

    private Context context;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Inject
    PreferenceHelperImpl(Context context, @PerferenceInfo String myPREFERENCES) {

        this.context = context;
        sharedpreferences = context.getSharedPreferences(myPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }


    @Override
    public void setUserName(String name) {
        editor.putString(Constants.PREF_KEY_NAME, name).apply();
    }

    @Override
    public String getUserName() {
        return sharedpreferences.getString(Constants.PREF_KEY_NAME, "");
    }

    @Override
    public void setPhoneNum(String phone) {
        editor.putString(Constants.PREF_KEY_PHONE, phone).apply();
    }

    @Override
    public String getPhoneNum() {
        return sharedpreferences.getString(Constants.PREF_KEY_PHONE, "");
    }
}
