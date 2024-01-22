package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

        // Create a new CharacterSheet object
        CharacterSheet characterSheet = new CharacterSheet();


        // Get the data from the EditText fields and set it in the CharacterSheet object
        EditText editTextCharacterName = rootView.findViewById(R.id.editTextCharacterName);
        if (editTextCharacterName != null) {
            characterSheet.setCharacterName(editTextCharacterName.getText().toString());
        }

        EditText editTextSpecies = rootView.findViewById(R.id.editTextSpecies);
        if (editTextSpecies != null) {
            characterSheet.setSpecies(editTextSpecies.getText().toString());
        }

        EditText editTextRank = rootView.findViewById(R.id.editTextRank);
        if (editTextRank != null) {
            characterSheet.setRank(editTextRank.getText().toString());
        }

        EditText editTextEnvironment = rootView.findViewById(R.id.editTextEnvironment);
        if (editTextEnvironment != null) {
            characterSheet.setEnvironment(editTextEnvironment.getText().toString());
        }

        EditText editTextUpbringing = rootView.findViewById(R.id.editTextUpbringing);
        if (editTextUpbringing != null) {
            characterSheet.setUpbringing(editTextUpbringing.getText().toString());
        }

        EditText editTextAssignment = rootView.findViewById(R.id.editTextAssignment);
        if (editTextAssignment != null) {
            characterSheet.setAssignment(editTextAssignment.getText().toString());
        }

        EditText editTextAcademy = rootView.findViewById(R.id.editTextAcademy);
        if (editTextAcademy != null) {
            characterSheet.setAcademy(editTextAcademy.getText().toString());
        }

        EditText editTextCareer = rootView.findViewById(R.id.editTextCareer);
        if (editTextCareer != null) {
            characterSheet.setCareer(editTextCareer.getText().toString());
        }

        EditText editTextTraits = rootView.findViewById(R.id.editTextTraits);
        if (editTextTraits != null) {
            characterSheet.setTraits(editTextTraits.getText().toString());
        }

        EditText editTextValues = rootView.findViewById(R.id.editTextValues);
        if (editTextValues != null) {
            characterSheet.setValues(editTextValues.getText().toString());
        }

        EditText editTextPronouns = rootView.findViewById(R.id.pronouns_edit_text);
        if (editTextPronouns != null) {
            characterSheet.setPronouns(editTextPronouns.getText().toString());
        }

        EditText editTextAge = rootView.findViewById(R.id.age_edit_text);
        if (editTextAge != null) {
            characterSheet.setAge(editTextAge.getText().toString());
        }

        EditText editTextSkin = rootView.findViewById(R.id.skin_edit_text);
        if (editTextSkin != null) {
            characterSheet.setSkin(editTextSkin.getText().toString());
        }

        EditText editTextHair = rootView.findViewById(R.id.hair_edit_text);
        if (editTextHair != null) {
            characterSheet.setHair(editTextHair.getText().toString());
        }

        EditText editTextWeight = rootView.findViewById(R.id.weight_edit_text);
        if (editTextWeight != null) {
            characterSheet.setWeight(editTextWeight.getText().toString());
        }

        EditText editTextHeight = rootView.findViewById(R.id.height_edit_text);
        if (editTextHeight != null) {
            characterSheet.setHeight(editTextHeight.getText().toString());
        }

        EditText editTextEyes = rootView.findViewById(R.id.eyes_edit_text);
        if (editTextEyes != null) {
            characterSheet.setEyes(editTextEyes.getText().toString());
        }

        EditText editTextEvent1 = rootView.findViewById(R.id.event1_edit_text);
        if (editTextEvent1 != null) {
            characterSheet.setEvent1(editTextEvent1.getText().toString());
        }

        EditText editTextNotesAndAwards = rootView.findViewById(R.id.notes_and_awards_edit_text);
        if (editTextNotesAndAwards != null) {
            characterSheet.setNotesAndAwards(editTextNotesAndAwards.getText().toString());
        }

        EditText editTextControl = rootView.findViewById(R.id.editTextControl);
        if (editTextControl != null) {
            characterSheet.setControl(editTextControl.getText().toString());
        }

        EditText editTextFitness = rootView.findViewById(R.id.editTextFitness);
        if (editTextFitness != null) {
            characterSheet.setFitness(editTextFitness.getText().toString());
        }

        EditText editTextPresence = rootView.findViewById(R.id.editTextPresence);
        if (editTextPresence != null) {
            characterSheet.setPresence(editTextPresence.getText().toString());
        }

        EditText editTextDaring = rootView.findViewById(R.id.editTextDaring);
        if (editTextDaring != null) {
            characterSheet.setDaring(editTextDaring.getText().toString());
        }

        EditText editTextInsight = rootView.findViewById(R.id.editTextInsight);
        if (editTextInsight != null) {
            characterSheet.setInsight(editTextInsight.getText().toString());
        }

        EditText editTextReason = rootView.findViewById(R.id.editTextReason);
        if (editTextReason != null) {
            characterSheet.setReason(editTextReason.getText().toString());
        }

        EditText editTextCommand = rootView.findViewById(R.id.editTextCommand);
        if (editTextCommand != null) {
            characterSheet.setCommand(editTextCommand.getText().toString());
        }

        EditText editTextSecurity = rootView.findViewById(R.id.editTextSecurity);
        if (editTextSecurity != null) {
            characterSheet.setSecurity(editTextSecurity.getText().toString());
        }

        EditText editTextScience = rootView.findViewById(R.id.editTextScience);
        if (editTextScience != null) {
            characterSheet.setScience(editTextScience.getText().toString());
        }

        EditText editTextConn = rootView.findViewById(R.id.editTextConn);
        if (editTextConn != null) {
            characterSheet.setConn(editTextConn.getText().toString());
        }

        EditText editTextEngineering = rootView.findViewById(R.id.editTextEngineering);
        if (editTextEngineering != null) {
            characterSheet.setEngineering(editTextEngineering.getText().toString());
        }

        EditText editTextMedicine = rootView.findViewById(R.id.editTextMedicine);
        if (editTextMedicine != null) {
            characterSheet.setMedicine(editTextMedicine.getText().toString());
        }

        EditText editTextStress = rootView.findViewById(R.id.stressEditText);
        if (editTextStress != null) {
            characterSheet.setStress(editTextStress.getText().toString());
        }

        EditText editTextMaxStress = rootView.findViewById(R.id.maxStressEditText);
        if (editTextMaxStress != null) {
            characterSheet.setMaxStress(editTextMaxStress.getText().toString());
        }

        EditText editTextResistance = rootView.findViewById(R.id.resistanceEditText);
        if (editTextResistance != null) {
            characterSheet.setResistance(editTextResistance.getText().toString());
        }



        EditText editTextInjuries = rootView.findViewById(R.id.injuriesEditText);
        if (editTextInjuries != null) {
            characterSheet.setInjuries(editTextInjuries.getText().toString());
        }

        EditText editTextReputation = rootView.findViewById(R.id.reputationEditText);
        if (editTextReputation != null) {
            characterSheet.setReputation(editTextReputation.getText().toString());
        }

        EditText editTextPrivilege = rootView.findViewById(R.id.privilegeEditText);
        if (editTextPrivilege != null) {
            characterSheet.setPrivilege(editTextPrivilege.getText().toString());
        }

        EditText editTextResponsibility = rootView.findViewById(R.id.responsibilityEditText);
        if (editTextResponsibility != null) {
            characterSheet.setResponsibility(editTextResponsibility.getText().toString());
        }

        EditText editTextDetermination = rootView.findViewById(R.id.determinationEditText);
        if (editTextDetermination != null) {
            characterSheet.setDetermination(editTextDetermination.getText().toString());
        }

        EditText editTextAttacks = rootView.findViewById(R.id.attacksEditText);
        if (editTextAttacks != null) {
            characterSheet.setAttacks(editTextAttacks.getText().toString());
        }

        EditText editTextEquipment = rootView.findViewById(R.id.equipmentEditText);
        if (editTextEquipment != null) {
            characterSheet.setEquipment(editTextEquipment.getText().toString());
        }


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
                        return EditableSkillsFragment.newInstance(userId, characterId);
                    } else {
                        return SkillsFragment.newInstance(userId, characterId);
                    }
                case 3:
                    if (editMode) {
                        return EditableOthersFragment.newInstance(userId, characterId);
                    } else {
                        return OthersFragment.newInstance(userId, characterId);
                    }
            }
            return DataTabFragment.newInstance(userId, characterId);
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