package com.team28.borrow.rxfirebase;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;


public final class RxSharedPreferences {
    private static final String DEFAULT_STRING = "";

    @NonNull
    public static RxSharedPreferences create(@NonNull SharedPreferences preferences) {
        return new RxSharedPreferences(preferences);
    }

    private final SharedPreferences preferences;
    private final Observable<String> keyChanges;

    private RxSharedPreferences(final SharedPreferences preferences) {
        this.preferences = preferences;
        this.keyChanges = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            final OnSharedPreferenceChangeListener listener = (preferences1, key) -> emitter.onNext(key);

            emitter.setCancellable(() -> preferences.unregisterOnSharedPreferenceChangeListener(listener));

            preferences.registerOnSharedPreferenceChangeListener(listener);
        }).share();
    }

    @NonNull
    public Preference<String> getString(@NonNull String key) {
        return getString(key, DEFAULT_STRING);
    }

    @NonNull
    public Preference<String> getString(@NonNull String key, @NonNull String defaultValue) {
        return new RealPreference<>(preferences, key, defaultValue, StringAdapter.INSTANCE, keyChanges);
    }


    public void clear() {
        preferences.edit().clear().apply();
    }
}