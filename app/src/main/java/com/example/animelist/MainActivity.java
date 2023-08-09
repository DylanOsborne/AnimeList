package com.example.animelist;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.animelist.DataStore.Anime;
import com.example.animelist.DataStore.AnimeViewModel;
import com.example.animelist.RecyclerView.AnimeListAdapter;

public class MainActivity extends AppCompatActivity {

    private AnimeViewModel mAnimeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnimeViewModel = new ViewModelProvider(this).get(AnimeViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AnimeListAdapter adapter = new AnimeListAdapter(new AnimeListAdapter.AnimeDiff(), mAnimeViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAnimeViewModel.getAllAnimes().observe(this, adapter::submitList);

        Button addAnimeButton = findViewById(R.id.addAnimeButton);
        addAnimeButton.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, NewAnimeActivity.class);
            addAnimeActivityLaunch.launch(intent);
        });

        Button statsButton = findViewById(R.id.statsButton);
        statsButton.setOnClickListener( view -> {
            Intent intent = new Intent(this, StatsActivity.class);
            startActivity(intent);
        });
    }


    ActivityResultLauncher<Intent> addAnimeActivityLaunch = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == 123) {

                Intent data = result.getData();

                assert data != null;

                String animeName = data.getStringExtra("name");
                int animeSeason = data.getIntExtra("season", 0);
                int animeEps = data.getIntExtra("episode", 0);
                boolean isChecked = data.getBooleanExtra("completed", false);

                Anime anime = new Anime(animeName, animeSeason, animeEps, isChecked);
                mAnimeViewModel.insert(anime);

            }
        }
    );
}