package com.team28.borrow.data.datasource.noti.network;

import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.team28.borrow.data.model.entity.NotiDataEntity;
import com.team28.borrow.domain.model.Noti;
import com.team28.borrow.rxfirebase.RxFirestore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class NetworkNotiDataSourceImpl implements NetworkNotiDataSource {


    private FirebaseFirestore mFirebaseDb;

    @Inject
    NetworkNotiDataSourceImpl() {
        mFirebaseDb = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .setPersistenceEnabled(true)
                .build();
        mFirebaseDb.setFirestoreSettings(settings);

    }

    @Override
    public Flowable<List<NotiDataEntity>> getNoti(String uuid) {
/*.collection("noti")
                .orderBy("date", "desc")*/
        return RxFirestore.observeQueryRef(mFirebaseDb.collection("noti").whereEqualTo("user_id", uuid).orderBy("date", Query.Direction.DESCENDING))

                .map(queryDocumentSnapshots -> {
                    List<NotiDataEntity> itemDataEntities = new ArrayList<>();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            itemDataEntities.add(NotiDataEntity.create(snapshot).withId(snapshot.getId()));
                            Log.e("data", snapshot.getId());
                        }

                    }
                    return itemDataEntities;
                });

    }

    @Override
    public Flowable<NotiDataEntity> getNotiData(String uuid) {
        return RxFirestore.observeQueryRef(mFirebaseDb.collection("noti").whereEqualTo("user_id", uuid))
                .map(queryDocumentSnapshots -> {

                    NotiDataEntity notiDataEntity = null;
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentChange snapshot : queryDocumentSnapshots.getDocumentChanges()) {

                            if (snapshot.getType().equals(DocumentChange.Type.ADDED)) {
                                return NotiDataEntity.create(snapshot.getDocument()).withId(snapshot.getDocument().getId());
                            }

                        }

                    }
                    return notiDataEntity;
                });
    }

    @Override
    public void sendNoti(NotiDataEntity entity) {
        RxFirestore.addDocument(mFirebaseDb.collection("noti"), entity.toFirebaseValue()).subscribe();
    }
}
