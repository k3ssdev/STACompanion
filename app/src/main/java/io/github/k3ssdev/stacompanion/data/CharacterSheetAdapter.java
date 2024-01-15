package io.github.k3ssdev.stacompanion.data;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// TODO: implementar CharacterSheetAdapter

public class CharacterSheetAdapter extends RecyclerView.Adapter<CharacterSheetAdapter.ViewHolder> {

    private List<CharacterSheet> characterSheets;

    public CharacterSheetAdapter(ArrayList<CharacterSheet> characterSheets) {
        this.characterSheets = characterSheets != null ? characterSheets : new ArrayList<>();
    }

    // constructor y otros métodos...

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