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

// Esta clase es un adaptador para el RecyclerView en CharactersFragment.
// Implementa Filterable para permitir la búsqueda de personajes.
public class CharacterFragmentAdapter extends RecyclerView.Adapter<CharacterFragmentAdapter.ViewHolder> implements Filterable {
    private final List<CharacterSheet> characterSheets;
    private List<CharacterSheet> characterSheetsFull;

    private CharactersFragment.OnItemClickListener listener;

    // Constructor que inicializa la lista de hojas de personajes.
    public CharacterFragmentAdapter(ArrayList<CharacterSheet> characterSheets) {
        this.characterSheets = characterSheets != null ? characterSheets : new ArrayList<>();
        this.characterSheetsFull = new ArrayList<>(this.characterSheets);
    }

    // Implementación de los métodos de Filterable para filtrar la lista de personajes.
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
                    characterSheets.addAll((List<? extends CharacterSheet>) results.values);
                }
                characterSheetsFull = new ArrayList<>(characterSheets); // Actualizar characterSheetsFull
                notifyDataSetChanged();
            }
        };
    }

    // Método para crear ViewHolder para cada elemento en el RecyclerView.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_sheet_item, parent, false);
        return new ViewHolder(view);
    }

    // Método para vincular los datos de la hoja de personaje con el ViewHolder.
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

    // Método para limpiar la lista de hojas de personajes.
    public void clearCharacterSheets() {
        this.characterSheets.clear();
    }

    // Método para obtener el número de elementos en la lista de hojas de personajes.
    @Override
    public int getItemCount() {
        return characterSheets.size();
    }

    // Método para agregar una hoja de personaje a la lista.
    public void addCharacterSheet(CharacterSheet sheet) {
        characterSheets.add(sheet);
        characterSheetsFull = new ArrayList<>(characterSheets); // Actualizar characterSheetsFull
        notifyDataSetChanged();
    }

    // ViewHolder que contiene las vistas para cada elemento en el RecyclerView.
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView characterNameTextView;
        TextView speciesTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            characterNameTextView = itemView.findViewById(R.id.characterName);
            speciesTextView = itemView.findViewById(R.id.species);
        }
    }

    // Método para establecer el listener de clic en el elemento.
    public void setOnItemClickListener(CharactersFragment.OnItemClickListener listener) {
        this.listener = listener;
    }
}