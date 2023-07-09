package com.example.animelist.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.animelist.DataStore.Anime;
import com.example.animelist.R;

public class AnimeListAdapter extends ListAdapter<Anime, AnimeViewHolder> {

    public AnimeListAdapter(@NonNull DiffUtil.ItemCallback<Anime> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AnimeViewHolder.create(parent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(AnimeViewHolder holder, int position) {
        Anime current = getItem(position);
        holder.bindName(current.getAnimeName());

        if(current.isCompleted()) {
            holder.bindChecked();
        }

        holder.editBtn.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.edit_anime);

            EditText animeName = view.findViewById(R.id.editAnimeNameInputField);
            EditText animeSeason = view.findViewById(R.id.editAnimeSeasonInputField);
            EditText animeEps = view.findViewById(R.id.editAnimesEpisodeInputField);
            CheckBox isChecked = view.findViewById(R.id.editCheckBoxForAnimeCompleted);

            Button updateBtn = view.findViewById(R.id.updateAnimeButton);

            animeName.setText(current.getAnimeName());
            animeSeason.setText(current.getAnimeSeason());
            animeEps.setText(current.getAnimeEpisode());
            isChecked.setChecked(current.isCompleted());

            updateBtn.setOnClickListener(view1 -> {

                String name = "";
                int season = 0;
                int eps = 0;

                if(!animeName.getText().toString().equals("") &&
                        !animeSeason.getText().toString().equals("") &&
                            !animeEps.getText().toString().equals("")) {

                    name = animeName.getText().toString();
                    season =  Integer.parseInt(animeSeason.getText().toString());
                    eps = Integer.parseInt(animeEps.getText().toString());

                    boolean completed = isChecked.isChecked();

                    current.setAnimeName(name);
                    current.setAnimeSeason(season);
                    current.setAnimeEpisode(eps);
                    current.setCompleted(completed);

                    notifyItemChanged(position);

                    dialog.dismiss();
                }
            });

            dialog.show();
        });
    }

    public static class AnimeDiff extends DiffUtil.ItemCallback<Anime> {

        @Override
        public boolean areItemsTheSame(@NonNull Anime oldItem, @NonNull Anime newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Anime oldItem, @NonNull Anime newItem) {
            return oldItem.getAnimeName().equals(newItem.getAnimeName());
        }
    }

}