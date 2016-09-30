package questionablequality.rpglifeapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import questionablequality.rpglifeapp.data.*;

import questionablequality.rpglifeapp.databinding.ActivityMainMenuBinding;

public class MainMenuActivity extends AppCompatActivity {

    ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize the DataBinding Viewbinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu);

        //Data binding the data element "User"
        binding.setUser((User)getIntent().getSerializableExtra("User"));


        //Setting the Buttons OnClick events
        binding.btnQuestLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, QuestLogActivity.class);
                startActivity(intent);    }
        });

        binding.btnGuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }   });

        binding.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }   });

        binding.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mu);
                mediaPlayer.start();    }
        });

    }
}
