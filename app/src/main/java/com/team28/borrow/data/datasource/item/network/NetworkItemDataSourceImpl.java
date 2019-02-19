package com.team28.borrow.data.datasource.item.network;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.team28.borrow.util.Constants;
import com.team28.borrow.data.model.entity.ItemDataEntity;
import com.team28.borrow.rxfirebase.RxFirestore;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class NetworkItemDataSourceImpl implements NetworkItemDataSource {


    private FirebaseFirestore mFirebaseDb;

    @Inject
    NetworkItemDataSourceImpl() {
        mFirebaseDb = FirebaseFirestore.getInstance();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .setPersistenceEnabled(true)
                .build();
        mFirebaseDb.setFirestoreSettings(settings);

    }


    @Override
    public Flowable<List<ItemDataEntity>> getItemEntities() {
        return RxFirestore.observeQueryRef(mFirebaseDb.collection("item_owner_post").whereEqualTo("state", Constants.ACCEPT)).map(queryDocumentSnapshots -> {
            List<ItemDataEntity> itemDataEntities = new ArrayList<>();

            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                    itemDataEntities.add(ItemDataEntity.create(snapshot).withId(snapshot.getId()));
                    Log.e("data", snapshot.getId());
                }

            }
            return itemDataEntities;
        });
    }

    @Override
    public Flowable<List<ItemDataEntity>> getItemEntitiesByCategory(@NonNull String category) {
        return RxFirestore.observeQueryRef(mFirebaseDb.collection("item_owner_post")
                .whereEqualTo("category", category)
                .whereEqualTo("state", Constants.ACCEPT))
                .map(queryDocumentSnapshots -> {
                    List<ItemDataEntity> itemDataEntities = new ArrayList<>();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            itemDataEntities.add(ItemDataEntity.create(snapshot).withId(snapshot.getId()));
                            Log.e("data", snapshot.getId());
                        }

                    }
                    return itemDataEntities;
                });
    }

    @Override
    public void addItem(ItemDataEntity itemDataEntity) {
        RxFirestore.addDocument(mFirebaseDb.collection("item_owner_post"), itemDataEntity.toFirebaseValue()).subscribe();
    }

    @Override
    public void updateItem(ItemDataEntity itemDataEntity, String id) {
        RxFirestore.addDocument(mFirebaseDb.collection("item_owner_post").document(id), itemDataEntity.toFirebaseValue()).subscribe();

    }

    @Override
    public Flowable<List<ItemDataEntity>> getItemEntitiesByCurrentCuser(@NonNull String uuid) {
        return RxFirestore.observeQueryRef(mFirebaseDb.collection("item_owner_post").whereEqualTo("user_id", uuid))
                .map(queryDocumentSnapshots -> {
                    List<ItemDataEntity> itemDataEntities = new ArrayList<>();

                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            itemDataEntities.add(ItemDataEntity.create(snapshot).withId(snapshot.getId()));
                            Log.e("data", snapshot.getId());
                        }       /*for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                itemDataEntities.add(ItemDataEntity.create(dc.getDocument()).withId(dc.getDocument().getId()));

                            } else if (dc.getType() == DocumentChange.Type.ADDED) {
                                itemDataEntities.add(ItemDataEntity.create(dc.getDocument()).withId(dc.getDocument().getId()));

                            }

                        }*/

                    }
                    return itemDataEntities;
                });
    }


}
