package questionablequality.rpglifeapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import questionablequality.rpglifeapp.adapter.QuestAdapter;
import questionablequality.rpglifeapp.databinding.ActivityQuestLogBinding;
import questionablequality.rpglifeapp.provider.QuestProvider;

public class QuestLogActivity extends AppCompatActivity {

    ActivityQuestLogBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_quest_log);

        binding.BtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        QuestProvider QP = new QuestProvider(this);
        QuestAdapter QA = new QuestAdapter(this, QP.ReturnQuests());
        binding.LstQuests.setAdapter(QA);
    }
}
