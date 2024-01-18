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
import io.github.k3ssdev.stacompanion.databinding.FragmentDatosTabBinding;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetViewModel;

public class DatosTabFragment extends Fragment {

    private CharacterSheetViewModel viewModel;

    public DatosTabFragment() {
        // Required empty public constructor
    }

    public static DatosTabFragment newInstance(String characterId) {
        DatosTabFragment fragment = new DatosTabFragment();
        Bundle args = new Bundle();
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
        // Use Data Binding to inflate the view
        FragmentDatosTabBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_datos_tab, container, false);

        // Check if binding is null
        if (binding == null) {
            throw new RuntimeException("Data Binding error occurred!");
        }

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Set the ViewModel in your binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Recuperar el ID del personaje del Bundle
        String characterId = null;
        if (getArguments() != null) {
            characterId = getArguments().getString("characterId");
        }

        // Usar el ID del personaje para recuperar los detalles del personaje
        if (characterId != null) {
            viewModel.getCharacterSheetFromDatabase(characterId);
        }

        // Return the root view
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observe the characterSheetLiveData
        viewModel.getCharacterSheetLiveData().observe(getViewLifecycleOwner(), characterSheet -> {
            // Update your UI here with the new characterSheet
            // This will automatically update the TextView with the character's name when the data changes
        });
    }
}