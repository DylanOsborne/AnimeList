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
import com.example.animelist.DataStore.AnimeViewModel;
import com.example.animelist.R;

public class AnimeListAdapter extends ListAdapter<Anime, AnimeViewHolder> {

    private final AnimeViewModel viewModel;

    public AnimeListAdapter(@NonNull DiffUtil.ItemCallback<Anime> diffCallback, AnimeViewModel viewModel) {
        super(diffCallback);
        this.viewModel = viewModel;
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
        holder.bindChecked(current.isCompleted());

        holder.editBtn.setOnClickListener(view -> {
            Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.edit_anime);

            EditText animeName = dialog.findViewById(R.id.editAnimeNameInputField);
            EditText animeSeason = dialog.findViewById(R.id.editAnimeSeasonInputField);
            EditText animeEps = dialog.findViewById(R.id.editAnimesEpisodeInputField);
            CheckBox isChecked = dialog.findViewById(R.id.editCheckBoxForAnimeCompleted);

            Button updateBtn = dialog.findViewById(R.id.updateAnimeButton);
            Button deleteBtn = dialog.findViewById(R.id.deleteButton);

            animeName.setText(current.getAnimeName());
            animeSeason.setText(Integer.toString(current.getAnimeSeason()));
            animeEps.setText(Integer.toString(current.getAnimeEpisode()));
            isChecked.setChecked(current.isCompleted());

            updateBtn.setOnClickListener(view1 -> {
                String name = animeName.getText().toString();
                int season = Integer.parseInt(animeSeason.getText().toString());
                int eps = Integer.parseInt(animeEps.getText().toString());
                boolean completed = isChecked.isChecked();

                current.setAnimeName(name);
                current.setAnimeSeason(season);
                current.setAnimeEpisode(eps);
                current.setCompleted(completed);

                viewModel.updateAnime(current);

                notifyItemChanged(position);
                dialog.dismiss();
            });

            deleteBtn.setOnClickListener(view1 -> {
                Dialog deleteConfirmation = new Dialog(view.getContext());
                deleteConfirmation.setContentView(R.layout.delete_confirmation);

                Button yesBtn = deleteConfirmation.findViewById(R.id.yesButton);
                Button noBtn = deleteConfirmation.findViewById(R.id.noButton);

                yesBtn.setOnClickListener(view2 -> {
                    viewModel.deleteAnime(current);
                    deleteConfirmation.dismiss();
                    dialog.dismiss();
                });

                noBtn.setOnClickListener(view2 -> {
                    deleteConfirmation.dismiss();
                    dialog.dismiss();
                });

                deleteConfirmation.show();
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