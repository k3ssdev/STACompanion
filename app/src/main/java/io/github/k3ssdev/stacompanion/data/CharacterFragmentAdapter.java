package io.github.k3ssdev.stacompanion.data;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.ui.characters.CharactersFragment;

// Esta clase es un adaptador para el RecyclerView en CharactersFragment.
// Implementa Filterable para permitir la búsqueda de personajes.
public class CharacterFragmentAdapter extends RecyclerView.Adapter<CharacterFragmentAdapter.ViewHolder> implements Filterable {

    private List<CharacterSheet> selectedItems = new ArrayList<>();

    private boolean isMultiSelectionEnabled = false;
    private final List<CharacterSheet> characterSheets;
    private List<CharacterSheet> characterSheetsFull;

    private CharactersFragment.OnItemClickListener listener;

    private final Fragment fragment;

    // Constructor que inicializa la lista de hojas de personajes.
    public CharacterFragmentAdapter(ArrayList<CharacterSheet> characterSheets, Fragment fragment) {
        this.characterSheets = characterSheets != null ? characterSheets : new ArrayList<>();
        this.characterSheetsFull = new ArrayList<>(this.characterSheets);
        this.fragment = fragment;
    }
/*    public CharacterFragmentAdapter(ArrayList<CharacterSheet> characterSheets) {
        this.characterSheets = characterSheets != null ? characterSheets : new ArrayList<>();
        this.characterSheetsFull = new ArrayList<>(this.characterSheets);
    }*/

    public void setMultiSelectionEnabled(boolean isEnabled) {
        isMultiSelectionEnabled = isEnabled;
    }
    public void sortByName() {
        Collections.sort(characterSheets, new Comparator<CharacterSheet>() {
            @Override
            public int compare(CharacterSheet cs1, CharacterSheet cs2) {
                return cs1.getCharacterName().compareToIgnoreCase(cs2.getCharacterName());
            }
        });
        notifyDataSetChanged();
    }

    public void sortByCreationDate() {
        Collections.sort(characterSheets, new Comparator<CharacterSheet>() {
            @Override
            public int compare(CharacterSheet cs1, CharacterSheet cs2) {
                return Long.compare(cs2.getCreationTimestamp(), cs1.getCreationTimestamp());
            }
        });
        notifyDataSetChanged();
    }
    // Método para obtener la lista de hojas de personajes completa.
    public void resetFilter() {
        characterSheets.clear();
        characterSheets.addAll(characterSheetsFull);
        notifyDataSetChanged();
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

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedItems.contains(sheet));

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(sheet);
            } else {
                selectedItems.remove(sheet);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (isMultiSelectionEnabled) {
                // Toggle the state of the item
                if (selectedItems.contains(sheet)) {
                    selectedItems.remove(sheet);
                } else {
                    selectedItems.add(sheet);
                }
                notifyItemChanged(position);
            } else {
                if (listener != null) {
                    listener.onItemClick(sheet);
                }
            }
            // Invalidate the options menu after changing the state
            fragment.requireActivity().invalidateOptionsMenu();
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (isMultiSelectionEnabled && selectedItems.contains(sheet)) {
                // If multi-selection mode is enabled and the current item is selected, clear the selection
                selectedItems.clear();
                isMultiSelectionEnabled = false;
            } else {
                // Otherwise, enable multi-selection mode and select the current item
                isMultiSelectionEnabled = true;
                selectedItems.add(sheet);
            }
            notifyDataSetChanged();
            // Invalidate the options menu after changing the state
            fragment.requireActivity().invalidateOptionsMenu();
            return true;
        });

        if (isMultiSelectionEnabled) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
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

    public List<CharacterSheet> getSelectedItems() {
        return selectedItems;
    }

    public void clearSelectedItems() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public boolean isMultiSelectionEnabled() {
        return isMultiSelectionEnabled;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView characterNameTextView;
        TextView speciesTextView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            characterNameTextView = itemView.findViewById(R.id.characterName);
            speciesTextView = itemView.findViewById(R.id.species);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    // Método para establecer el listener de clic en el elemento.
    public void setOnItemClickListener(CharactersFragment.OnItemClickListener listener) {
        this.listener = listener;
    }
}