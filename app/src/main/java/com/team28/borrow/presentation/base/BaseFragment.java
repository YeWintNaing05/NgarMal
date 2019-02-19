package com.team28.borrow.presentation.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.team28.borrow.R;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class
BaseFragment extends Fragment {

    private CompositeDisposable compositeDisposable;
    private Unbinder unbinder;

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @LayoutRes
    public abstract int getLayoutResource();

    public void showSnack(String message, int actionMessage, View.OnClickListener onClickListener,
                          int duration) {
        View rootContent = Objects.requireNonNull(getActivity()).findViewById(android.R.id.content);
        Snackbar.make(rootContent, message, duration)
                .setAction(actionMessage, onClickListener)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    public void showSnack(String message, String actionMessage, View.OnClickListener onClickListener,
                          int duration) {
        View rootContent = Objects.requireNonNull(getActivity()).findViewById(android.R.id.content);
        Snackbar.make(rootContent, message, duration)
                .setAction(actionMessage, onClickListener)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    public void hideKeyboard() {
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void add(Disposable disposable) {
        if (compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}