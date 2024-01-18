package io.github.k3ssdev.stacompanion.ui.characters;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;
import io.github.k3ssdev.stacompanion.databinding.FragmentCharacterSheetBinding;

public class CharacterSheetFragment extends Fragment {

    private CharacterSheetViewModel mViewModel;

    public static CharacterSheetFragment newInstance() {
        return new CharacterSheetFragment();
    }

    /*@Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_sheet, container, false);

        // Initialize the ViewModel
        mViewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Recuperar el ID del personaje del Bundle
        String characterId = null;
        if (getArguments() != null) {
            characterId = getArguments().getString("characterId");
        }

        // Usar el ID del personaje para recuperar los detalles del personaje
        if (characterId != null) {
            mViewModel.getCharacterSheetFromDatabase(characterId);
            mViewModel.getCharacterSheetLiveData().observe(getViewLifecycleOwner(), new Observer<CharacterSheet>() {
                @Override
                public void onChanged(CharacterSheet characterSheet) {
                    // Ahora puedes usar characterSheet para rellenar tus vistas
                    Log.d(TAG, "CharacterSheetLiveData changed: " + characterSheet);
                    TextView characterNameTextView = view.findViewById(R.id.characterNameValue);
                    if (characterSheet != null) {
                        characterNameTextView.setText(characterSheet.getCharacterName());
                    } else {
                        characterNameTextView.setText("Character not found");
                    }

                    // Haz lo mismo para las demás vistas en tu fragmento
                }
            });
        }

        return view;
    }*/

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Use Data Binding to inflate the view
        FragmentCharacterSheetBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_sheet, container, false);

        // Initialize the ViewModel
        mViewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Set the ViewModel in your binding
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        // Recuperar el ID del personaje del Bundle
        String characterId = null;
        if (getArguments() != null) {
            characterId = getArguments().getString("characterId");
        }

        // Usar el ID del personaje para recuperar los detalles del personaje
        if (characterId != null) {
            mViewModel.getCharacterSheetFromDatabase(characterId);
            mViewModel.getCharacterSheetLiveData().observe(getViewLifecycleOwner(), new Observer<CharacterSheet>() {
                @Override
                public void onChanged(CharacterSheet characterSheet) {
                    // Ahora puedes usar characterSheet para rellenar tus vistas
                    Log.d(TAG, "CharacterSheetLiveData changed: " + characterSheet);
                    if (characterSheet != null) {
                        // Use Data Binding to set the text
                        binding.characterNameValue.setText(characterSheet.getCharacterName());
                    } else {
                        binding.characterNameValue.setText("Character not found");
                    }

                    // Haz lo mismo para las demás vistas en tu fragmento
                }
            });
        }

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}