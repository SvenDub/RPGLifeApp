package questionablequality.rpglifeapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import questionablequality.rpglifeapp.adapter.QuestAdapter;
import questionablequality.rpglifeapp.data.Quest;
import questionablequality.rpglifeapp.databinding.ActivityQuestLogBinding;
import questionablequality.rpglifeapp.provider.QuestProvider;

public class QuestLogActivity extends AppCompatActivity {

    private ActivityQuestLogBinding binding;

    private QuestProvider mQuestProvider;
    private QuestAdapter mQuestAdapter;

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
        binding.BtnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.BtnAddQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestLogActivity.this, AddQuestActivity.class);
                startActivity(intent);
            }
        });

        //binds the adapter containing the quests.
        mQuestProvider = new QuestProvider(this);
        mQuestAdapter = new QuestAdapter(this, mQuestProvider.ReturnQuests());
        binding.LstQuests.setAdapter(mQuestAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Quest> quests = mQuestProvider.ReturnQuests();
        mQuestAdapter.clear();
        mQuestAdapter.addAll(quests);
    }
}
