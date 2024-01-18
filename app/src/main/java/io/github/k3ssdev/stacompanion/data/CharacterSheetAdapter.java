package io.github.k3ssdev.stacompanion.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.ui.characters.CharactersFragment;

public class CharacterSheetAdapter extends RecyclerView.Adapter<CharacterSheetAdapter.ViewHolder> implements Filterable {
    private List<CharacterSheet> characterSheets;
    private List<CharacterSheet> characterSheetsFull;

    private CharactersFragment.OnItemClickListener listener;
    public CharacterSheetAdapter(ArrayList<CharacterSheet> characterSheets) {
        this.characterSheets = characterSheets != null ? characterSheets : new ArrayList<>();
        this.characterSheetsFull = new ArrayList<>(this.characterSheets);
    }

    // Filterable methods
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<CharacterSheet> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(characterSheetsFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (CharacterSheet item : characterSheetsFull) {
                        if (item.getCharacterName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }


            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                characterSheets.clear();
                if (results.values != null) {
                    characterSheets.addAll((List) results.values);
                }
                characterSheetsFull = new ArrayList<>(characterSheets); // Actualizar characterSheetsFull
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view for each item of the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_sheet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CharacterSheet sheet = characterSheets.get(position);
        holder.characterNameTextView.setText(sheet.getCharacterName());
        holder.speciesTextView.setText(sheet.getSpecies());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(sheet);
            }
        });

    }

    public void clearCharacterSheets() {
        this.characterSheets.clear();
    }
    @Override
    public int getItemCount() {
        return characterSheets.size();
    }

    public void addCharacterSheet(CharacterSheet sheet) {
        characterSheets.add(sheet);
        characterSheetsFull = new ArrayList<>(characterSheets); // Update characterSheetsFull
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView characterNameTextView;
        TextView speciesTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            characterNameTextView = itemView.findViewById(R.id.characterName);
            speciesTextView = itemView.findViewById(R.id.species);
        }
    }



    public void setOnItemClickListener(CharactersFragment.OnItemClickListener listener) {
        this.listener = listener;
    }

}