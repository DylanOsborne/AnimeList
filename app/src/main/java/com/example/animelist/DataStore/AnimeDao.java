package com.example.animelist.DataStore;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AnimeDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Anime anime);

    @Query("DELETE FROM anime_table")
    void deleteAll();


    @Query("SELECT * FROM anime_table ORDER BY anime_name ASC")
    LiveData<List<Anime>> getAlphabetizedAnimes();
}
