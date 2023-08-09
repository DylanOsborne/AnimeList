package com.example.animelist.DataStore;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AnimeViewModel extends AndroidViewModel {

    private final AnimeRepository mRepository;

    private final LiveData<List<Anime>> mAllAnimes;

    public AnimeViewModel (Application application) {
        super(application);
        mRepository = new AnimeRepository(application);
        mAllAnimes = mRepository.getAllAnime();
    }

    public LiveData<List<Anime>> getAllAnimes() {
        return mAllAnimes;
    }

    public void insert(Anime anime) {
        mRepository.insert(anime);
    }

    public void deleteAnime(Anime anime) {
        mRepository.deleteAnime(anime);
    }

    public void updateAnime(Anime anime) {
        mRepository.updateAnime(anime);
    }
}
