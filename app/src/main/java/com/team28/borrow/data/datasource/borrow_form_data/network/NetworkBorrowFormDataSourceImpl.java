package com.team28.borrow.data.datasource.borrow_form_data.network;


import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.team28.borrow.data.model.entity.BorrowFormDataEntity;
import com.team28.borrow.rxfirebase.RxFirestore;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class NetworkBorrowFormDataSourceImpl implements NetworkBorrowFormDataSource {

    private static final String TAG = "network_borrow";
    private FirebaseFirestore mFirebaseDb;

    @Inject
    public NetworkBorrowFormDataSourceImpl() {
        mFirebaseDb = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .setPersistenceEnabled(true)
                .build();
        mFirebaseDb.setFirestoreSettings(settings);

    }

    @Override
    public void applyBorrowForm(BorrowFormDataEntity borrowFormDataEntity) {
        RxFirestore.addDocument(mFirebaseDb.collection("borrow_form_post"), borrowFormDataEntity.toFirebaseValue()).subscribe();

    }

    @Override
    public Flowable<List<BorrowFormDataEntity>> getBorrowApplyByCurrentUser(String uuid) {
        return RxFirestore.observeQueryRef(mFirebaseDb.collection("borrow_form_post").whereEqualTo("current_user_id", uuid))
                .map(queryDocumentSnapshots -> {
                    List<BorrowFormDataEntity> itemDataEntities = new ArrayList<>();

                    if (!queryDocumentSnapshots.isEmpty() && !queryDocumentSnapshots.getDocuments().isEmpty()) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            itemDataEntities.add(BorrowFormDataEntity.create(snapshot).withId(snapshot.getId()));
                        }

                        String source = queryDocumentSnapshots.getMetadata().isFromCache() ?
                                "local cache" : "server";

                        Log.d(TAG, "Data fetched from " + source);

                    }
                    return itemDataEntities;
                });

    }


}
