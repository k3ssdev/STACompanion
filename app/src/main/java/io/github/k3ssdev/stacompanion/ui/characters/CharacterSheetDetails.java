package io.github.k3ssdev.stacompanion.ui.characters;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;

public class CharacterSheetDetails extends AppCompatActivity {
    private CharacterSheet characterSheet;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_character_sheet);

        // Retrieve the CharacterSheet object from the Intent
        Intent intent = getIntent();
        characterSheet = (CharacterSheet) intent.getSerializableExtra("CharacterSheet");

        // Set up the ViewPager and TabLayout
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new CharacterSheetPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    private class CharacterSheetPagerAdapter extends FragmentPagerAdapter {
        public CharacterSheetPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DatosTabFragment();
                case 1:
                    return new EstadoYEquipoFragment();
                case 2:
                    return new AtributosYDisciplinasFragment();
                case 3:
                    return new AparienciaYOtrosFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Datos";
                case 1:
                    return "Estado y equipo";
                case 2:
                    return "Atributos y Disciplinas";
                case 3:
                    return "Apariencia y otros";
                default:
                    return null;
            }
        }
    }
}