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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuestProvider = new QuestProvider(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_quest);

        binding.AddQuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Quest quest = new Quest(binding.TxtQuestDescription.getText().toString());
                if (mQuestProvider.addQuest(quest)) {
                    finish();
                } else {
                    Snackbar.make(binding.activityAddQuest, R.string.error_add_quest, Snackbar.LENGTH_SHORT).show();
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
