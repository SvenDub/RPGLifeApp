package questionablequality.rpglifeapp;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import questionablequality.rpglifeapp.adapter.QuestAdapter;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.databinding.ActivityQuestLogBinding;
import questionablequality.rpglifeapp.provider.QuestProvider;

public class QuestLogActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status>, GoogleApiClient.ConnectionCallbacks {

    private static final int REQUEST_PERMISSION_LOCATION = 1;
    public static final int GEOFENCE_RADIUS = 100;

    private ActivityQuestLogBinding binding;

    private QuestProvider mQuestProvider;
    private QuestAdapter mQuestAdapter;

    private GoogleApiClient mGoogleApiClient;

    private List<Geofence> mGeofenceList = new ArrayList<>();
    private PendingIntent mGeofencePendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_quest_log);

        binding.BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.BtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.BtnAddQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestLogActivity.this, AddQuestActivity.class);
                intent.putExtra("isGuild", false);
                startActivity(intent);
            }
        });

        //binds the adapter containing the quests.
        mQuestProvider = new QuestProvider(this);
        mQuestAdapter = new QuestAdapter(this, mQuestProvider.ReturnQuests());
        binding.LstQuests.setAdapter(mQuestAdapter);

        binding.BtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(QuestLogActivity.this);
                new AlertDialog.Builder(QuestLogActivity.this)
                        .setTitle(R.string.filter)
                        .setView(editText)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String text = editText.getText().toString();
                                mQuestAdapter.getFilter().filter(text);

                                if (!text.isEmpty()) {
                                    binding.BtnFilter.setText(getString(R.string.action_filter) + " (" + text + ")");
                                } else {
                                    binding.BtnFilter.setText(getString(R.string.action_filter));
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        binding.LstQuests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quest quest = mQuestAdapter.getItem(position);

                Intent intent = new Intent(QuestLogActivity.this, QuestDetailActivity.class);
                intent.putExtra("quest", quest.getId());

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mGoogleApiClient.isConnected()) {
            reloadQuests();
        }
    }

    private void reloadQuests() {
        List<Quest> quests = mQuestProvider.ReturnQuests();
        mQuestAdapter.clear();
        mQuestAdapter.addAll(quests);

        for (Quest quest : quests) {
            if (quest.getPlace() != null && quest.getProgress() < quest.getGoal()) {
                mGeofenceList.add(new Geofence.Builder()
                        .setRequestId(Integer.toString(quest.getId()))
                        .setCircularRegion(quest.getPlace().getLatLng().latitude, quest.getPlace().getLatLng().longitude, GEOFENCE_RADIUS)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL)
                        .setLoiteringDelay(1)
                        .setExpirationDuration(365 * 24 * 60 * 60 * 1000)
                        .build()
                );
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
            return;
        }

        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    reloadQuests();
                }
                break;
        }
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("GMS", "Connection failed: " + connectionResult.getErrorMessage());
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.d("GEOFENCE", status.toString());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        reloadQuests();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }
}
