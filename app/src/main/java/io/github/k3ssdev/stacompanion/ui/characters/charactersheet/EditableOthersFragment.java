package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.databinding.FragmentTabSheetEditOthersBinding;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetViewModel;

public class EditableOthersFragment extends Fragment {

    private CharacterSheetViewModel viewModel;

    public EditableOthersFragment() {
    }

    public static EditableOthersFragment newInstance(String userId, String characterId) {
        EditableOthersFragment fragment = new EditableOthersFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("characterId", characterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Mostrar el menú de opciones
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        // Inflar el menú
        inflater.inflate(R.menu.character_sheet_toolbar_menu_edit, menu);

        // Ocultar el botón de editar
        menu.findItem(R.id.action_edit).setVisible(false);

        // Llamar al método de la superclase

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentTabSheetEditOthersBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_sheet_edit_others, container, false);
        // Comprobar si la vinculación es nula
        if (binding == null) {
            throw new RuntimeException("¡Ocurrió un error de Data Binding!");
        }

        // Inicializar el ViewModel
        viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Establecer el ViewModel en su binding
        binding.setOthersViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Recuperar el ID del usuario y del personaje del Bundle
        String userId = null;
        String characterId = null;
        if (getArguments() != null) {
            userId = getArguments().getString("userId");
            characterId = getArguments().getString("characterId");
        }

        // Usar el ID del usuario y del personaje para recuperar los detalles del personaje
        if (userId != null && characterId != null) {
            viewModel.getCharacterSheetFromDatabase(userId, characterId);
        }

        // Devolver la vista raíz
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observar el characterSheetLiveData
        viewModel.getCharacterSheetLiveData().observe(getViewLifecycleOwner(), characterSheet -> {
            // Actualizar la interfaz de usuario aquí con la nueva hoja de personaje
            // Esto actualizará automáticamente el TextView con el nombre del personaje cuando los datos cambien
        });
    }
}