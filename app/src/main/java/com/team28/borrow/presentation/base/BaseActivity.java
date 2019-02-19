package com.team28.borrow.presentation.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.team28.borrow.R;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        this.setContentView(getLayoutId());
        ButterKnife.bind(this, this);

        main(savedInstanceState);

    }

    protected abstract int getLayoutId();

    protected abstract void main(Bundle savedInstanceState);


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
    }

    public void showSnack(String message, int actionMessage, View.OnClickListener onClickListener,
                          int duration) {
        View rootContent = findViewById(android.R.id.content);
        Snackbar.make(rootContent, message, duration)
                .setAction(actionMessage, onClickListener)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    public void showSnack(String message, String actionMessage, View.OnClickListener onClickListener,
                          int duration) {
        View rootContent = findViewById(android.R.id.content);
        Snackbar.make(rootContent, message, duration)
                .setAction(actionMessage, onClickListener)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
