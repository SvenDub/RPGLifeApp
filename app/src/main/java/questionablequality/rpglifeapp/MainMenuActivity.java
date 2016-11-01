package questionablequality.rpglifeapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Response;

import questionablequality.rpglifeapp.data.User;
import questionablequality.rpglifeapp.databinding.ActivityMainMenuBinding;

public class MainMenuActivity extends AppCompatActivity {

    ActivityMainMenuBinding binding;

    private User mUser;

    private ApiController mApiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize the DataBinding Viewbinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu);

        //Data binding the data element "User"
        mApiController = new ApiController(this);
        mApiController.getUser(mUserCallback);

        //Setting the Buttons OnClick events
        binding.btnQuestLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, QuestLogActivity.class);
                startActivity(intent);
            }
        });

        binding.btnGuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, GuildActivity.class);
                startActivity(intent);
            }
        });

        binding.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private FutureCallback<Response<User >> mUserCallback = new FutureCallback<Response<User>>() {
        @Override
        public void onCompleted(Exception e, Response<User> result) {
            if (e == null && result.getException() == null && result.getResult() != null) {
                mUser = result.getResult();
                binding.setUser(mUser);
                getSupportActionBar().setSubtitle(mUser.getUsername());

                //Dummy character creation
                mUser.makeCharacter(MainMenuActivity.this, 0);
                binding.ImgCharacter.setImageBitmap(mUser.getCharacter().getCharacterSprite());
            } else {
                finish();
                startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
            }
        }
    };

}
