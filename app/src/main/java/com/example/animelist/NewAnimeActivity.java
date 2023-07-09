package com.example.animelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class NewAnimeActivity extends AppCompatActivity {


    private EditText animeName;
    private EditText animeSeason;
    private EditText animeEps;
    private CheckBox isChecked;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_anime);

        animeName = findViewById(R.id.animeNameInputField);
        animeSeason = findViewById(R.id.animeSeasonInputField);
        animeEps = findViewById(R.id.animesEpisodeInputField);
        isChecked = findViewById(R.id.checkBoxForAnimeCompleted);


        Button button = findViewById(R.id.doneAnimeButton);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if (TextUtils.isEmpty(animeName.getText()) &&
                    TextUtils.isEmpty(animeSeason.getText()) &&
                    TextUtils.isEmpty(animeEps.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                Bundle bundle = new Bundle();
                boolean completed = isChecked.isChecked();


                replyIntent.putExtra("name", animeName.getText().toString());
                replyIntent.putExtra("season", animeSeason.getText());
                replyIntent.putExtra("episode", animeEps.getText());
                replyIntent.putExtra("completed", completed);

                setResult(123, replyIntent);
            }

            finish();
        });
    }
}