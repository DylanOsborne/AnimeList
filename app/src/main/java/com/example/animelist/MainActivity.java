package com.example.animelist;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.animelist.DataStore.Anime;
import com.example.animelist.DataStore.AnimeViewModel;
import com.example.animelist.RecyclerView.AnimeListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AnimeViewModel mAnimeViewModel;
    private AnimeListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnimeViewModel = new ViewModelProvider(this).get(AnimeViewModel.class);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new AnimeListAdapter(new AnimeListAdapter.AnimeDiff(), mAnimeViewModel, this);
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

        SearchView searchBar = findViewById(R.id.searchView);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        Spinner sortSpinner = findViewById(R.id.sortingSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.sort_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(spinnerAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = parentView.getItemAtPosition(position).toString();
                if (!selectedOption.equals("Sort by")) {
                    sortList(selectedOption);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void sortList(String selectedOption) {
        List<Anime> currentList = new ArrayList<>(adapter.getCurrentList());

        switch (selectedOption) {
            case "Name":
                Collections.sort(currentList, (anime1, anime2) ->
                        anime1.getAnimeName().compareToIgnoreCase(anime2.getAnimeName()));
                break;
            case "Completion":
                Collections.sort(currentList, (anime1, anime2) ->
                        Boolean.compare(anime1.isCompleted(), anime2.isCompleted()));
                break;
        }

        adapter.submitList(currentList);
        recyclerView.scrollToPosition(0);
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