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

// Esta clase representa el fragmento de datos en la aplicación.
public class DatosTabFragment extends Fragment {

    // ViewModel para este fragmento
    private CharacterSheetViewModel viewModel;

    // Constructor vacío requerido
    public DatosTabFragment() {
        // Constructor público vacío requerido
    }

    // Método para crear una nueva instancia de este fragmento
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
        // Utilizar Data Binding para inflar la vista
        FragmentDatosTabBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_datos_tab, container, false);

        // Comprobar si la vinculación es nula
        if (binding == null) {
            throw new RuntimeException("¡Ocurrió un error de Data Binding!");
        }

        // Inicializar el ViewModel
        viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Establecer el ViewModel en su binding
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