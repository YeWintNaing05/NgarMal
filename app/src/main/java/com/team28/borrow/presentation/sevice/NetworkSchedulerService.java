package com.team28.borrow.presentation.sevice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.team28.borrow.R;
import com.team28.borrow.data.model.entity.NotiDataEntity;
import com.team28.borrow.presentation.feature.main.MainActivity;
import com.team28.borrow.util.Constants;
import com.team28.borrow.util.custom_view.UnicodeHelper;


import javax.annotation.Nullable;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class NetworkSchedulerService extends JobService implements
        ConnectivityReceiver.ConnectivityReceiverListener {

    private static final String TAG = NetworkSchedulerService.class.getSimpleName();

    private ConnectivityReceiver mConnectivityReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service created");
        mConnectivityReceiver = new ConnectivityReceiver(this);
    }


    /**
     * When the app's NetworkConnectionActivity is created, it starts this service. This is so that the
     * activity and this service can communicate back and forth. See "setUiCallback()"
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob" + mConnectivityReceiver);
        registerReceiver(mConnectivityReceiver, new IntentFilter(Constants.CONNECTIVITY_ACTION));
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob");
        unregisterReceiver(mConnectivityReceiver);
        return true;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
       /* String message = isConnected ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();*/

        if (isConnected) {
           /* RxFirestore.observeQueryRef(FirebaseFirestore.getInstance().collection("noti").whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid()))
                    .subscribe(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {

                            for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                                if (change.getType().equals(DocumentChange.Type.ADDED)) {
                                    //showNoti(this, change.getDocument().toObject(NotiDataEntity.class));
                                }
                            }


                        }
                    });*/

           if(FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseFirestore.getInstance().collection("noti").whereEqualTo("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(queryDocumentSnapshots != null)
                    for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                        if (change.getType().equals(DocumentChange.Type.ADDED)) {
                            showNoti(NetworkSchedulerService.this, NotiDataEntity.create(change.getDocument()).withId(change.getDocument().getId()));
                        }
                    }
                }
            });


        } else
            Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
    }


    private void showNoti(Context context, NotiDataEntity entity) {
       /* PendingIntent pi = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(entity.title() + "\t" + entity.date())
                .setContentText(entity.content())
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pi)
                .setShowWhen(true)
                .setColor(Color.RED)
                .setLocalOnly(true)
                .build();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name =entity.title() ;
            String description = entity.content();
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(new Random().nextInt()), name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }else{
            NotificationManagerCompat.from(context)
                    .notify(new Random().nextInt(), notification);
        }
*/
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    UnicodeHelper.getText("ငှားမယ်"),
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Ngar Mal");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(UnicodeHelper.getText(entity.title())) // title for notification
                .setContentText(UnicodeHelper.getText(entity.content()))// message for notification
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
      //  mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}