package questionablequality.rpglifeapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.databinding.ActivityAddQuestBinding;
import questionablequality.rpglifeapp.provider.QuestProvider;

public class AddQuestActivity extends AppCompatActivity {

    ActivityAddQuestBinding binding;


    private QuestProvider mQuestProvider;

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
                if(binding.chkBxGPS.isChecked()){
                    quest = new Quest(binding.TxtQuestName.getText().toString(), binding.TxtQuestDescription.getText().toString(), Integer.parseInt(binding.TxtSetGoalAmount.getText().toString() /**TODO: add location get**/ ));
                }else{
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

        binding.QuestAddBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
