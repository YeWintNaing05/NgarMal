package com.team28.borrow.presentation.base;

import android.arch.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BaseViewModel extends ViewModel {

  private CompositeDisposable compositeDisposable = new CompositeDisposable();

  public void add(Disposable disposable) {
    compositeDisposable.add(disposable);
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    if (!compositeDisposable.isDisposed()) {
      compositeDisposable.clear();
    }
  }
}