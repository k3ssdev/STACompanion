package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetViewModel;

// Esta clase representa el fragmento de la hoja de personaje en la aplicación.
public class CharacterSheetFragment extends Fragment {

    private CharacterSheetViewModel mViewModel;

    private boolean editMode = false;

    // Este método crea una nueva instancia del fragmento de la hoja de personaje.
    public static CharacterSheetFragment newInstance(String userId, String characterId, boolean editMode) {
        CharacterSheetFragment fragment = new CharacterSheetFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("characterId", characterId);
        args.putBoolean("editMode", editMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        int itemId = item.getItemId();
        if (itemId == R.id.action_edit) {
            // Abre el fragmento de edición de la hoja de personaje
            fragmentManager.beginTransaction()
                    .replace(R.id.container, CharacterSheetFragment.newInstance(getArguments().getString("userId"), getArguments().getString("characterId"), true))
                    .addToBackStack(null)
                    .commit();
            return true;
        } else if (itemId == R.id.action_save) {
            saveData();
            // Vuelve al modo normal
            fragmentManager.beginTransaction()
                    .replace(R.id.container, CharacterSheetFragment.newInstance(getArguments().getString("userId"), getArguments().getString("characterId"), false))
                    .addToBackStack(null)
                    .commit();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void saveData() {
        // Get the root view of the fragment
        View rootView = getView();
        if (rootView == null) {
            return;
        }

        // Get userId and characterId from the arguments
        String userId = getArguments().getString("userId");
        String characterId = getArguments().getString("characterId");

        // Get the character sheet from the ViewModel
        CharacterSheet characterSheet = mViewModel.getCharacterSheetLiveData().getValue();



        // Save the data in the Firebase Realtime Database
        mViewModel.saveCharacterSheetToDatabase(userId, characterId, characterSheet);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Indica que este fragmento tiene un menú de opciones

        if (getArguments() != null) {
            editMode = getArguments().getBoolean("editMode", false);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.character_sheet_toolbar_menu, menu); // Infla el menú
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem itemEdit = menu.findItem(R.id.action_edit);
        MenuItem itemSave = menu.findItem(R.id.action_save);
        if (itemEdit != null && itemSave != null) {
            itemEdit.setVisible(!editMode);
            itemSave.setVisible(editMode);
        }
    }

    // Este método se llama para inflar el diseño del fragmento.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Infla el diseño para este fragmento
        View view = inflater.inflate(R.layout.fragment_character_sheet, container, false);

        // Inicializa el ViewModel
        mViewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Establece un título predeterminado o indicador de carga
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Cargando...");

        // Recupera el ID del usuario y del personaje del Bundle
        String userId = null;
        String characterId = null;
        if (getArguments() != null) {
            userId = getArguments().getString("userId");
            characterId = getArguments().getString("characterId");
        }

        // Usa el ID del usuario y del personaje para recuperar los detalles del personaje
        if (userId != null && characterId != null) {
            mViewModel.getCharacterSheetFromDatabase(userId, characterId);
        }

        // Establece el título de la barra de herramientas con el nombre del personaje
        mViewModel.getCharacterSheetLiveData().observe(getViewLifecycleOwner(), new Observer<CharacterSheet>() {
            @Override
            public void onChanged(@Nullable final CharacterSheet characterSheet) {
                // Actualiza la interfaz de usuario, en este caso, establece el título de la barra de aplicaciones.
                if (characterSheet != null) {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(characterSheet.getCharacterName());
                }
            }
        });

        // Configura el ViewPager y TabLayout
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        viewPager.setAdapter(new CharacterSheetPagerAdapter(getChildFragmentManager(), userId, characterId));
        tabLayout.setupWithViewPager(viewPager);

        // Muestra el botón de volver si no estás en modo de edición
//        if (!editMode && getActivity() != null && getActivity() instanceof AppCompatActivity) {
//            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }

        return view;
    }

    // Este método se llama cuando la actividad del fragmento ha sido creada.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Usa el ViewModel
    }

    // Esta clase representa el adaptador de páginas para la hoja de personaje.
    public class CharacterSheetPagerAdapter extends FragmentPagerAdapter {
        private final String userId;
        private final String characterId;
        private final String[] tabTitles = new String[]{"Datos", "Estado/Equipo", "Atributos/Disciplinas", "Otros"};

        // Constructor del adaptador de páginas.
        public CharacterSheetPagerAdapter(@NonNull FragmentManager fm, String userId, String characterId) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.userId = userId;
            this.characterId = characterId;
        }


        @NonNull
        // Este método devuelve el fragmento correspondiente a la posición
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    if (editMode) {
                        return EditableDataTabFragment.newInstance(userId, characterId);
                    } else {
                        return DataTabFragment.newInstance(userId, characterId);
                    }
                case 1:
                    if (editMode) {
                        return EditableStatusFragment.newInstance(userId, characterId);
                    } else {
                        return StatusFragment.newInstance(userId, characterId);
                    }
                case 2:
                    if (editMode) {
                        return SkillsFragment.newInstance(userId, characterId);
                    } else {
                        return SkillsFragment.newInstance(userId, characterId);
                    }
                case 3:
                    if (editMode) {
                        return OthersFragment.newInstance(userId, characterId);
                    } else {
                        return OthersFragment.newInstance(userId, characterId);
                    }
                default:
                    return null;
            }
        }



        // Este método devuelve el número total de páginas.
        @Override
        public int getCount() {
            return tabTitles.length;
        }

        // Este método devuelve el título de la página correspondiente a la posición dada.
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}