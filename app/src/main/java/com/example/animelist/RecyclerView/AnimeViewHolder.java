package com.example.animelist.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.animelist.R;

public class AnimeViewHolder extends RecyclerView.ViewHolder {

    private final TextView animeItemView;
    private final CheckBox isAnimeCompletedView;
    final Button editBtn;

    private AnimeViewHolder(View itemView) {
        super(itemView);
        animeItemView = itemView.findViewById(R.id.textView_item_name);
        isAnimeCompletedView = itemView.findViewById(R.id.completedCheckBox);
        editBtn = itemView.findViewById(R.id.editBtn);
    }

    public void bindName(String text) {
        animeItemView.setText(text);
    }

    public void bindChecked(boolean isChecked) {
        isAnimeCompletedView.setChecked(isChecked);
    }


    static AnimeViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new AnimeViewHolder(view);
    }
}
