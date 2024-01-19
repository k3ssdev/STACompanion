package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;

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

// Esta clase representa los detalles de la hoja de personaje en la aplicación.
public class CharacterSheetDetails extends AppCompatActivity {
    private CharacterSheet characterSheet;
    private Bundle savedInstanceState;

    // Este método se llama cuando se crea la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_character_sheet);

        // Recupera el objeto CharacterSheet del Intent
        Intent intent = getIntent();
        characterSheet = (CharacterSheet) intent.getSerializableExtra("CharacterSheet");

        // Configura la barra de aplicaciones con el nombre del personaje
        getSupportActionBar().setTitle(characterSheet.getCharacterName());

        // Configura el ViewPager y TabLayout
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager.setAdapter(new CharacterSheetPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    // Esta clase interna representa el adaptador de páginas para la hoja de personaje.
    private class CharacterSheetPagerAdapter extends FragmentPagerAdapter {
        public CharacterSheetPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Este método devuelve el fragmento correspondiente a la posición dada.
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

        // Este método devuelve el número total de páginas.
        @Override
        public int getCount() {
            return 4;
        }

        // Este método devuelve el título de la página correspondiente a la posición dada.
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