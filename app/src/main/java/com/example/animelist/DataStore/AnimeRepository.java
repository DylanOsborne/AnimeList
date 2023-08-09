package com.example.animelist.DataStore;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnimeRepository {

    private final AnimeDao mAnimeDao;
    private final LiveData<List<Anime>> mAllAime;
    private final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);


    AnimeRepository(Application application) {
        AnimeRoomDatabase db = AnimeRoomDatabase.getDatabase(application);
        mAnimeDao = db.animeDao();
        mAllAime = mAnimeDao.getAlphabetizedAnimes();
    }

    LiveData<List<Anime>> getAllAnime() {
        return mAllAime;
    }

    void insert (Anime anime) {
        databaseWriteExecutor.execute(() -> mAnimeDao.insert(anime));
    }

    void deleteAnime(Anime anime) {
        databaseWriteExecutor.execute(() -> mAnimeDao.deleteAnime(anime));
    }

    void updateAnime(Anime anime) {
        databaseWriteExecutor.execute(() -> mAnimeDao.updateAnime(anime));
    }
}

