package questionablequality.rpglifeapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Quest> quests = mQuestProvider.ReturnQuests();
        mQuestAdapter.clear();
        mQuestAdapter.addAll(quests);
    }
}
