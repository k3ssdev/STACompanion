package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.databinding.ActivityCharacterSheetEditBinding;
import io.github.k3ssdev.stacompanion.databinding.FragmentTabSheetDataBinding;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetViewModel;

// Esta clase representa el fragmento de datos en la aplicación.
public class DataTabFragment extends Fragment {

    // ViewModel para este fragmento
    private CharacterSheetViewModel viewModel;



    // Constructor vacío requerido
    public DataTabFragment() {
        // Constructor público vacío requerido
    }

    // Método para crear una nueva instancia de este fragmento
    public static DataTabFragment newInstance(String userId, String characterId) {
        DataTabFragment fragment = new DataTabFragment();
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
    public void onResume() {
        super.onResume();

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

        viewModel.getCharacterSheetLiveData().observe(this, characterSheet -> {
            // Actualiza la interfaz de usuario, en este caso, establece el título de la barra de aplicaciones.
            if (characterSheet != null) {
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(characterSheet.getCharacterName());
            }
        });
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Utilizar Data Binding para inflar la vista
        FragmentTabSheetDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab_sheet_data, container, false);

        // Comprobar si la vinculación es nula
        if (binding == null) {
            throw new RuntimeException("¡Ocurrió un error de Data Binding!");
        }

        // Inicializar el ViewModel
        viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Establecer el ViewModel en su binding
        binding.setViewModel(viewModel);
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