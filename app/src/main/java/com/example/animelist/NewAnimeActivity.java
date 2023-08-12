package com.example.animelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

            if (TextUtils.isEmpty(animeName.getText()) || TextUtils.isEmpty(animeSeason.getText()) || TextUtils.isEmpty(animeEps.getText())) {
                Toast.makeText(this, "Incomplete Fields", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Anime Not Added", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                boolean completed = isChecked.isChecked();

                replyIntent.putExtra("name", animeName.getText().toString());
                replyIntent.putExtra("season", Integer.parseInt(animeSeason.getText().toString()));
                replyIntent.putExtra("episode", Integer.parseInt(animeEps.getText().toString()));
                replyIntent.putExtra("completed", completed);

                Toast.makeText(this, "Anime Added", Toast.LENGTH_SHORT).show();
                setResult(123, replyIntent);
            }

            finish();
        });
    }
}