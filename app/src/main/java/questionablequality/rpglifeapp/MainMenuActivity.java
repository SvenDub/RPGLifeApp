package questionablequality.rpglifeapp;

import android.databinding.DataBindingUtil;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import questionablequality.rpglifeapp.data.*;

import questionablequality.rpglifeapp.databinding.ActivityMainMenuBinding;

public class MainMenuActivity extends AppCompatActivity {

    ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu);

        binding.setUser((User)getIntent().getSerializableExtra("User"));

        binding.MainMenuQuestLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vib.vibrate(1000);
            }
        });


    }
}
