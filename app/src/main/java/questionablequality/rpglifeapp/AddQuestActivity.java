package questionablequality.rpglifeapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.databinding.ActivityAddQuestBinding;
import questionablequality.rpglifeapp.provider.QuestProvider;

public class AddQuestActivity extends AppCompatActivity {

    private static final int PLACE_PICKER_REQUEST = 1;

    private ActivityAddQuestBinding binding;

    private QuestProvider mQuestProvider;
    private Place mPlace = null;

    private boolean isGuildQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isGuildQuest = getIntent().getBooleanExtra("isGuild", false);

        mQuestProvider = new QuestProvider(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_quest);

        binding.AddQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quest quest = null;
                if (mPlace != null) {
                    quest = new Quest(binding.TxtQuestName.getText().toString(), binding.TxtQuestDescription.getText().toString(), Integer.parseInt(binding.TxtSetGoalAmount.getText().toString()), mPlace);
                } else {
                    quest = new Quest(binding.TxtQuestName.getText().toString(), binding.TxtQuestDescription.getText().toString(), Integer.parseInt(binding.TxtSetGoalAmount.getText().toString()));
                }

                if(isGuildQuest){
                    if (mQuestProvider.addGuildQuest(quest)) {
                        finish();
                    } else {
                        Snackbar.make(binding.activityAddQuest, R.string.error_add_quest, Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    if (mQuestProvider.addQuest(quest)) {
                        finish();
                    } else {
                        Snackbar.make(binding.activityAddQuest, R.string.error_add_quest, Snackbar.LENGTH_SHORT).show();
                    }
                }



            }
        });

        binding.btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    intent = new PlacePicker.IntentBuilder().build(AddQuestActivity.this);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    Toast.makeText(AddQuestActivity.this, R.string.common_google_play_services_unsupported_text, Toast.LENGTH_LONG).show();
                }
                startActivityForResult(intent, PLACE_PICKER_REQUEST);
            }
        });

        binding.btnRemoveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnRemoveLocation.setEnabled(false);
                binding.txtLocation.setText("");
            }
        });

        binding.QuestAddBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PLACE_PICKER_REQUEST:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(this, data);

                    binding.btnRemoveLocation.setEnabled(true);
                    binding.txtLocation.setText(place.getName());

                    mPlace = place;
                }
                break;
        }
    }
}
