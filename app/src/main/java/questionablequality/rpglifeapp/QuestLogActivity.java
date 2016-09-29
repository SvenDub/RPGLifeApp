package questionablequality.rpglifeapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import questionablequality.rpglifeapp.databinding.ActivityQuestLogBinding;

public class QuestLogActivity extends AppCompatActivity {

    ActivityQuestLogBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quest_log);
    }
}
