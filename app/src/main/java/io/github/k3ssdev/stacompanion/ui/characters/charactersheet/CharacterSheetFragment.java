package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;

import android.os.Bundle;
import android.view.LayoutInflater;
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

    // Este método crea una nueva instancia del fragmento de la hoja de personaje.
    public static CharacterSheetFragment newInstance(String userId, String characterId) {
        CharacterSheetFragment fragment = new CharacterSheetFragment();
        Bundle args = new Bundle();
        args.putString("userId", userId);
        args.putString("characterId", characterId);
        fragment.setArguments(args);
        return fragment;
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

        // Este método devuelve el fragmento correspondiente a la posición dada.
        @NonNull
        // Este método devuelve el fragmento correspondiente a la posición dada.
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DataTabFragment.newInstance(userId, characterId);
                case 1:
                    return StatusFragment.newInstance(userId, characterId);
                case 2:
                    return SkillsFragment.newInstance(userId, characterId);
                case 3:
                    return OthersFragment.newInstance(userId, characterId);
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