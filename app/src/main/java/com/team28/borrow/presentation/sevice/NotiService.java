package com.team28.borrow.presentation.sevice;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import io.reactivex.annotations.NonNull;

public class NotiService extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotiService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@NonNull Intent intent) {

        Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();


    }
}
