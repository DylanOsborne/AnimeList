package com.example.animelist.DataStore;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Anime anime);

    @Query("DELETE FROM anime_table")
    void deleteAll();

    @Query("SELECT * FROM anime_table ORDER BY anime_name ASC")
    LiveData<List<Anime>> getAlphabetizedAnimes();

    @Delete
    void deleteAnime(Anime anime);

    @Update
    void updateAnime(Anime anime);
}
