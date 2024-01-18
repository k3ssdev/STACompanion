package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetViewModel;

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
        // Inflate the layout for this fragment
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
        }

        // Set up the ViewPager and TabLayout
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        viewPager.setAdapter(new CharacterSheetPagerAdapter(getChildFragmentManager(), characterId));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    public class CharacterSheetPagerAdapter extends FragmentPagerAdapter {
        private final String characterId;
        private final String[] tabTitles = new String[]{"Datos", "Estado y equipo", "Atributos y Disciplinas", "Apariencia y otros"};

        public CharacterSheetPagerAdapter(@NonNull FragmentManager fm, String characterId) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.characterId = characterId;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return DatosTabFragment.newInstance(characterId);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}