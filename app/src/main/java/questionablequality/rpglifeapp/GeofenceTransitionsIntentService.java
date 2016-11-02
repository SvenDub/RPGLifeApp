package questionablequality.rpglifeapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;

import questionablequality.rpglifeapp.data.Quest;

public class GeofenceTransitionsIntentService extends IntentService implements GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = GeofenceTransitionsIntentService.class.getSimpleName();

    private ApiController mApiController;
    private GoogleApiClient mGoogleApiClient;

    private Queue<List<String>> mRemoveQueue = new ConcurrentLinkedQueue<>();

    public GeofenceTransitionsIntentService() {
        super(GeofenceTransitionsIntentService.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        mGoogleApiClient.connect();

        mApiController = new ApiController(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = Integer.toString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            updateQuests(triggeringGeofences);
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
                    geofenceTransition));
        }
    }

    private void updateQuests(List<Geofence> geofences) {
        try {
            List<Quest> quests = mApiController.getQuests().get().getResult();
            List<String> doneQuests = new ArrayList<>();

            for (Geofence geofence : geofences) {
                int id = Integer.parseInt(geofence.getRequestId());

                for (Quest quest : quests) {
                    if (quest.getId() == id) {
                        quest.increase();
                        mApiController.saveQuest(quest).get();

                        Log.i(TAG, id + " updated to " + quest.getProgress() + "/" + quest.getGoal());
                        sendNotification(id, quest);
                        break;
                    }
                }
            }

            for (Quest quest : quests) {
                if (quest.getProgress() >= quest.getGoal()) {
                    doneQuests.add(Integer.toString(quest.getId()));
                }
            }

            if (mGoogleApiClient.isConnected() && !doneQuests.isEmpty()) {
                LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, doneQuests);
            } else {
                mRemoveQueue.add(doneQuests);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(int id, Quest quest) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, QuestLogActivity.class), PendingIntent.FLAG_ONE_SHOT);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(quest.getName())
                .setContentText(quest.getDescription())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setProgress(quest.getGoal(), quest.getProgress(), false)
                .setSubText(quest.getProgress() + "/" + quest.getGoal())
                .setShowWhen(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setGroup("quests")
                .setContentIntent(intent)
                .build();

        notificationManager.notify(id, notification);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        List<String> doneQuests;
        do {
            doneQuests = mRemoveQueue.poll();
            if (doneQuests != null) {
                if (!doneQuests.isEmpty()) {
                    LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, doneQuests);
                }
            }
        } while (doneQuests != null);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
