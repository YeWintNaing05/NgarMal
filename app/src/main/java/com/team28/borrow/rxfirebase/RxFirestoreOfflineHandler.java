package com.team28.borrow.rxfirebase;
import android.support.annotation.Nullable;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.functions.Cancellable;
//from FrangSierra repo

public class RxFirestoreOfflineHandler {

    /**
     * Method that listen a given reference and waits for a single change inside of it. However, this method
     * is just called in offline methods of add/set/delete in a new reference. That's why this reference should be
     * updated just once after successfully modify the reference with an offline operation.
     *
     * @param ref Document reference to be listened.
     * @return A Completable which emits when the given reference receives an offline update.
     */
    public static Completable listenOfflineListener(final DocumentReference ref) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) {
                try {
                    final ListenerRegistration listener = ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                emitter.onError(e);
                            } else {
                                if (documentSnapshot != null) {
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new NullPointerException("Empty Snapshot"));
                                }
                            }
                        }
                    });

                    emitter.setCancellable(new Cancellable() {
                        @Override
                        public void cancel() {
                            listener.remove();
                        }
                    });
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
}