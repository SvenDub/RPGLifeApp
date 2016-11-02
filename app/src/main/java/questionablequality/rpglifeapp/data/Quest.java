package questionablequality.rpglifeapp.data;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;

/**
 * Created by Tobi on 30-Sep-16.
 */

public class Quest {
    private int id;

    private String name;
    private String description;
    private int goal;

    private int progress;
    private int rewardxp;

    private String placeName;
    private double placeLat;
    private double placeLong;

    public Quest(String name, String description, int goal) {
        this.name = name;
        this.description = description;
        this.goal = goal;

        this.progress = 0;
        this.rewardxp = this.goal * 10;
    }

    public Quest(String name, String description, int goal, Place place) {
        this.name = name;
        this.description = description;
        this.goal = goal;

        this.progress = 0;
        this.rewardxp = this.goal * 10;

        setPlace(place);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getGoal() {
        return goal;
    }

    public int getRewardxp() {
        return rewardxp;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void increase() {
        progress++;
    }

    public void setPlace(Place place) {
        this.placeName = place.getName().toString();
        this.placeLat = place.getLatLng().latitude;
        this.placeLong = place.getLatLng().longitude;
    }

    /**
     * Only {@link Place#getName()} and {@link Place#getLatLng()} are supported.
     *
     * @return The selected place.
     */
    public Place getPlace() {
        if (placeName == null) {
            return null;
        }

        return new Place() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public List<Integer> getPlaceTypes() {
                return null;
            }

            @Override
            public CharSequence getAddress() {
                return null;
            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public CharSequence getName() {
                return placeName;
            }

            @Override
            public LatLng getLatLng() {
                return new LatLng(placeLat, placeLong);
            }

            @Override
            public LatLngBounds getViewport() {
                return null;
            }

            @Override
            public Uri getWebsiteUri() {
                return null;
            }

            @Override
            public CharSequence getPhoneNumber() {
                return null;
            }

            @Override
            public float getRating() {
                return 0;
            }

            @Override
            public int getPriceLevel() {
                return 0;
            }

            @Override
            public CharSequence getAttributions() {
                return null;
            }

            @Override
            public Place freeze() {
                return null;
            }

            @Override
            public boolean isDataValid() {
                return false;
            }
        };
    }
}
