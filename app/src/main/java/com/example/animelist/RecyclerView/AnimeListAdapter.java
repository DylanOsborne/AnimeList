package com.example.animelist.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.animelist.DataStore.Anime;
import com.example.animelist.DataStore.AnimeViewModel;
import com.example.animelist.R;

import java.util.ArrayList;
import java.util.List;

public class AnimeListAdapter extends ListAdapter<Anime, AnimeViewHolder> implements Filterable {

    private final AnimeViewModel viewModel;
    private List<Anime> originalData;
    private final Context context;

    public AnimeListAdapter(@NonNull DiffUtil.ItemCallback<Anime> diffCallback, AnimeViewModel viewModel, Context context) {
        super(diffCallback);
        this.viewModel = viewModel;
        this.context = context;
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
                    Toast.makeText(context, "Anime Deleted", Toast.LENGTH_SHORT).show();
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

    @Override
    public Filter getFilter() {
        return new AnimeFilter();
    }

    private class AnimeFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();

            List<Anime> filteredList = new ArrayList<>();

            if (originalData == null) {
                originalData = getCurrentList();
            }

            if (filterString.isEmpty()) {
                filteredList.addAll(originalData);
            } else {
                for (Anime anime : originalData) {
                    if (anime.getAnimeName().toLowerCase().startsWith(filterString)) {
                        filteredList.add(anime);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count = filteredList.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Anime> filteredList = (List<Anime>) results.values;
            submitList(filteredList);
        }
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

