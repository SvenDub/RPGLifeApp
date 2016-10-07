package questionablequality.rpglifeapp;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import questionablequality.rpglifeapp.data.User;
import questionablequality.rpglifeapp.databinding.ActivityMainMenuBinding;

public class MainMenuActivity extends AppCompatActivity {

    ActivityMainMenuBinding binding;

    private AccountManager mAccountManager;

    private User mUser;
    private Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAccountManager = AccountManager.get(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Missing permissions"); // TODO Handle gracefully
        }
        Account[] accounts = mAccountManager.getAccountsByType("questionablequality.rpglifeapp");

        if (accounts.length > 0) {
            mAccount = accounts[0];
        } else {
            throw new RuntimeException("Not logged in"); // TODO Handle gracefully
        }

        Log.d("ACCESS TOKEN", mAccountManager.getPassword(mAccount));

        //Initialize the DataBinding Viewbinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu);

        //Data binding the data element "User"
        Ion.with(this)
                .load("http://svendubbeld.nl:12345/user/me")
                .addHeader("Authorization", mAccountManager.getPassword(mAccount))
                .as(User.class)
                .withResponse()
                .setCallback(mUserCallback);

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

            }
        });
    }

    private FutureCallback<Response<User>> mUserCallback = new FutureCallback<Response<User>>() {
        @Override
        public void onCompleted(Exception e, Response<User> result) {
            if (e == null && result.getException() == null && result.getResult() != null) {
                mUser = result.getResult();
                binding.setUser(mUser);

                //Dummy character creation
                mUser.makeCharacter(MainMenuActivity.this, 0);
                binding.ImgCharacter.setImageBitmap(mUser.getCharacter().getCharacterSprite());
            } else {
                Toast.makeText(MainMenuActivity.this, R.string.delete_account, Toast.LENGTH_LONG).show();
                mAccountManager.removeAccountExplicitly(mAccount);
                finish();
                startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
            }
        }
    };

}
