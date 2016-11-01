package questionablequality.rpglifeapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import questionablequality.rpglifeapp.adapter.GuildAdapter;
import questionablequality.rpglifeapp.adapter.QuestAdapter;
import questionablequality.rpglifeapp.databinding.ActivityGuildBinding;
import questionablequality.rpglifeapp.provider.GuildMemberProvider;
import questionablequality.rpglifeapp.provider.GuildQuestProvider;
import questionablequality.rpglifeapp.provider.QuestProvider;

public class GuildActivity extends AppCompatActivity {

    ActivityGuildBinding binding;

    private GuildQuestProvider mGuildQuestProvider;
    private QuestAdapter mQuestAdapter;

    private GuildMemberProvider mGuildMemberProvider;
    private GuildAdapter mGuildAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guild);

/**

        //binds the adapter containing the quests.
        mGuildQuestProvider = new GuildQuestProvider(this);
        mQuestAdapter = new QuestAdapter(this, mGuildQuestProvider.ReturnQuests());
        binding.LstQuests.setAdapter(mQuestAdapter);

        //binds the adapter containing the guildmembers.
        mGuildMemberProvider = new GuildMemberProvider(this);
        mGuildAdapter = new GuildAdapter(this, mGuildMemberProvider.ReturnMembers());
        binding.LstMembers.setAdapter(mGuildAdapter);
 **/
    }
}
