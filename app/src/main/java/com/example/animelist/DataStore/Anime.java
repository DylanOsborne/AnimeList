package com.example.animelist.DataStore;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "anime_table")
public class Anime {


    @PrimaryKey(autoGenerate = true)
    private int AnimeID;

    @NonNull
    @ColumnInfo(name = "anime_name")
    private String animeName;

    @ColumnInfo(name = "anime_season")
    private int animeSeason;

    @ColumnInfo(name = "anime_episode")
    private int animeEpisode;

    @ColumnInfo(name = "anime_completed")
    private boolean completed;


    public Anime(@NonNull String animeName, int animeSeason, int animeEpisode, boolean completed) {
        this.animeName = animeName;
        this.animeSeason = animeSeason;
        this.animeEpisode = animeEpisode;
        this.completed = completed;
    }



    @NonNull
    public String getAnimeName() {
        return this.animeName;
    }

    public void setAnimeName(@NonNull String animeName) {
        this.animeName = animeName;
    }



    public int getAnimeSeason() {
        return this.animeSeason;
    }

    public void setAnimeSeason(int animeSeason) {
        this.animeSeason = animeSeason;
    }



    public int getAnimeEpisode() {
        return this.animeEpisode;
    }

    public void setAnimeEpisode(int animeEpisode) {
        this.animeEpisode = animeEpisode;
    }



    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }



    public int getAnimeID() {
        return this.AnimeID;
    }

    public void setAnimeID(int animeID) {
        AnimeID = animeID;
    }
}


