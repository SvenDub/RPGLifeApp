package questionablequality.rpglifeapp;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import questionablequality.rpglifeapp.databinding.ActivityAddQuestBinding;

public class AddQuestActivity extends AppCompatActivity {

    ActivityAddQuestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_quest);

        binding.QuestAddBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
