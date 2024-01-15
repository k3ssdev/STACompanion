package io.github.k3ssdev.stacompanion.data;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CharacterSheetAdapter extends RecyclerView.Adapter<CharacterSheetAdapter.ViewHolder> implements Filterable {
    private List<CharacterSheet> characterSheets;
    private List<CharacterSheet> characterSheetsFull;

    public CharacterSheetAdapter(ArrayList<CharacterSheet> characterSheets) {
        this.characterSheets = characterSheets != null ? characterSheets : new ArrayList<>();
        this.characterSheetsFull = new ArrayList<>(this.characterSheets); // Initialize characterSheetsFull with the data from characterSheets
    }

    // constructor y otros métodos...

    // Métodos de Filterable
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
                        // Aquí debes cambiar 'getName()' por el método que obtiene el campo que quieres buscar
                        if (item.getName().toLowerCase().contains(filterPattern)) {
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
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflar la vista de cada elemento del RecyclerView
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // establecer los datos en la vista de cada elemento
    }

    @Override
    public int getItemCount() {
        return characterSheets.size();
    }

    public void addCharacterSheet(CharacterSheet sheet) {
        characterSheets.add(sheet);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // referencias a las vistas de cada elemento

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // inicializar las vistas
        }
    }
}