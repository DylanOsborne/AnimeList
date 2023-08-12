package com.example.animelist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.animelist.DataStore.Anime;
import com.example.animelist.DataStore.AnimeViewModel;

public class StatsActivity extends AppCompatActivity {

    AnimeViewModel animeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_stats);

        animeViewModel = new ViewModelProvider(this).get(AnimeViewModel.class);

        TextView totalAnimes = findViewById(R.id.totalAnimeWatchedNumber);
        TextView totalCompleted = findViewById(R.id.totalCompletedNumber);
        TextView totalEps = findViewById(R.id.totalEpisodesNumber);

        animeViewModel.getAnimeCount().observe(this, count -> totalAnimes.setText(String.valueOf(count)));

        animeViewModel.getAllAnimes().observe(this, animeList -> {
            int tCompleted = 0;
            int tEps = 0;

            if (animeList != null) {
                for (Anime anime : animeList) {
                    if (anime.isCompleted()) {
                        tCompleted++;
                    }
                    tEps += anime.getAnimeEpisode();
                }
            }

            totalCompleted.setText(String.valueOf(tCompleted));
            totalEps.setText(String.valueOf(tEps));
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener( view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}
