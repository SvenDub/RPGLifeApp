package questionablequality.rpglifeapp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import questionablequality.rpglifeapp.data.User;
import questionablequality.rpglifeapp.databinding.ActivityMainMenuBinding;

public class MainMenuActivity extends AppCompatActivity {

    ActivityMainMenuBinding binding;

    private AccountManager mAccountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountManager = AccountManager.get(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Missing permissions"); // TODO Handle gracefully
        }
        Account[] accounts = mAccountManager.getAccountsByType("questionablequality.rpglifeapp");

        Account account;

        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            throw new RuntimeException("Not logged in"); // TODO Handle gracefully
        }

        Log.d("ACCESS TOKEN", mAccountManager.getPassword(account));

        //Initialize the DataBinding Viewbinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu);

        //Data binding the data element "User"
        binding.setUser((User) getIntent().getSerializableExtra("User"));


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
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mu);
                mediaPlayer.start();
            }
        });

    }
}
