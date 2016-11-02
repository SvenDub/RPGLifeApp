package questionablequality.rpglifeapp;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import questionablequality.rpglifeapp.adapter.GuildAdapter;
import questionablequality.rpglifeapp.adapter.QuestAdapter;
import questionablequality.rpglifeapp.data.User;
import questionablequality.rpglifeapp.databinding.ActivityGuildBinding;
import questionablequality.rpglifeapp.fragment.GuildFragment;
import questionablequality.rpglifeapp.fragment.JoinGuildFragment;
import questionablequality.rpglifeapp.provider.GuildMemberProvider;
import questionablequality.rpglifeapp.provider.GuildQuestProvider;
import questionablequality.rpglifeapp.provider.QuestProvider;

public class GuildActivity extends AppCompatActivity implements GuildFragment.OnFragmentInteractionListener, JoinGuildFragment.OnFragmentInteractionListener{

    ActivityGuildBinding binding;

    private ApiController mApiController;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_guild);
        mApiController = new ApiController(this);

        mApiController.getUser(mUserCallback);
    }

    private FutureCallback<Response<User >> mUserCallback = new FutureCallback<Response<User>>() {
        @Override
        public void onCompleted(Exception e, Response<User> result) {
            if (e == null && result.getException() == null && result.getResult() != null) {
                mUser = result.getResult();
                selectFragment();

            } else {
                finish();
            }
        }
    };

    private void selectFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment guild;
        if(mUser.hasGuild()){
            guild = GuildFragment.newInstance();
        }else{
            guild = JoinGuildFragment.newInstance();
        }
        fragmentTransaction.add(R.id.guild_container, guild);
        fragmentTransaction.commit();
    }
}
