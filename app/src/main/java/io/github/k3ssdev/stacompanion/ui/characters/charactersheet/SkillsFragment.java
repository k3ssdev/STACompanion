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
import io.github.k3ssdev.stacompanion.databinding.FragmentTabSheetSkillsBinding;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetViewModel;

public class SkillsFragment extends Fragment {


    private CharacterSheetViewModel viewModel;

    public SkillsFragment() {
    }

    public static SkillsFragment newInstance(String userId, String characterId) {
        SkillsFragment fragment = new SkillsFragment();
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentTabSheetSkillsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_sheet_skills, container, false);

        if (binding == null) {
            throw new RuntimeException("¡Ocurrió un error de Data Binding!");
        }

        viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        binding.setSkillsViewModel(viewModel);
        binding.setLifecycleOwner(this);

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
            // Esto actualizará automáticamente el TextView con el nombre del personaje cuando los datos cambien
        });


        // devuelve vista de status fragment
        return binding.getRoot();
    }
}
