package com.team28.borrow.rxfirebase.exception;

import android.support.annotation.NonNull;

public class RxFirebaseNullDataException extends NullPointerException {

    public RxFirebaseNullDataException() {
    }

    public RxFirebaseNullDataException(@NonNull String detailMessage) {
        super(detailMessage);
    }

}