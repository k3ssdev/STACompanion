package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.databinding.FragmentTabSheetStatusBinding;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetViewModel;

public class StatusFragment extends Fragment {

    private CharacterSheetViewModel viewModel;

    public StatusFragment() {
    }

    // Este método crea una nueva instancia de StatusFragment.
    public static StatusFragment newInstance(String userId, String characterId) {
        StatusFragment fragment = new StatusFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("characterId", characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Este método crea la vista de StatusFragment.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el diseño de la interfaz de usuario para este fragmento y hacer el data binding.
        FragmentTabSheetStatusBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_sheet_status, container, false);

        if (binding == null) {
            throw new RuntimeException("¡Ocurrió un error de Data Binding!");
        }

        viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        binding.setStatusViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Comprobar si se han pasado argumentos a este fragmento.
        String userId = null;
        String characterId = null;
        if (getArguments() != null) {
            userId = getArguments().getString("userId");
            characterId = getArguments().getString("characterId");
        }

        if (userId != null && characterId != null) {
            viewModel.getCharacterSheetFromDatabase(userId, characterId);
        }

        viewModel.getCharacterSheetLiveData().observe(getViewLifecycleOwner(), characterSheet -> {
            // Actualizar la interfaz de usuario aquí con la nueva hoja de personaje

        });

        // Devuelve vista de status fragment
        return binding.getRoot();
    }
}
