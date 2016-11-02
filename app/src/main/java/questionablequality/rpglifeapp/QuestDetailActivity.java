package questionablequality.rpglifeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import java.util.List;

import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.databinding.ActivityQuestDetailBinding;

import static questionablequality.rpglifeapp.QuestLogActivity.GEOFENCE_RADIUS;

public class QuestDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ActivityQuestDetailBinding binding;

    private ApiController mApiController;

    private GoogleMap mMap;

    private Quest mQuest;
    private int mQuestId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuestId = getIntent().getIntExtra("quest", -1);

        if (mQuestId == -1) {
            finish();
            return;
        }

        // Initialize the DataBinding Viewbinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quest_detail);

        // Data binding the data element "User"
        mApiController = new ApiController(this);
        mApiController.getQuests(mQuestCallback);

        binding.btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = mQuest.getPlace().getLatLng();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latLng.latitude + "," + latLng.longitude));
                startActivity(intent);
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private FutureCallback<Response<List<Quest>>> mQuestCallback = new FutureCallback<Response<List<Quest>>>() {
        @Override
        public void onCompleted(Exception e, Response<List<Quest>> result) {
            if (e == null && result.getException() == null && result.getResult() != null) {
                List<Quest> quests = result.getResult();

                for (Quest quest : quests) {
                    if (quest.getId() == mQuestId) {
                        mQuest = quest;
                        break;
                    }
                }

                binding.setQuest(mQuest);

                if (mMap != null) {
                    updateMap();
                }
            } else {
                finish();
            }
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mQuest != null) {
            updateMap();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    private void updateMap() {
        Place place = mQuest.getPlace();
        if (place != null) {
            mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
            mMap.addCircle(new CircleOptions().center(place.getLatLng()).radius(GEOFENCE_RADIUS));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
            findViewById(R.id.map).setVisibility(View.VISIBLE);
            binding.btnNavigate.setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.map).setVisibility(View.GONE);
            binding.btnNavigate.setVisibility(View.GONE);
        }
    }
}
