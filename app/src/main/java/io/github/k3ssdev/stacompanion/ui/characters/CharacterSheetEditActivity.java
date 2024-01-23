package io.github.k3ssdev.stacompanion.ui.characters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.github.k3ssdev.stacompanion.MainActivity;
import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;
import io.github.k3ssdev.stacompanion.databinding.ActivityCharacterSheetEditBinding;

public class CharacterSheetEditActivity extends AppCompatActivity {

    // Binding para esta actividad
    private ActivityCharacterSheetEditBinding binding;
    private EditText characterNameEditText;
    private CharacterSheetViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet_edit);

        //characterNameEditText = findViewById(R.id.editTextCharacterName);

        // Utilizar Data Binding para inflar la vista
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_sheet_edit);

        // Establecer la Toolbar como ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Establecer el botón de navegación
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Color del titulo
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        // Inicializar el ViewModel
        viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Establecer el ViewModel en su binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userId = extras.getString("userId");
            String characterId = extras.getString("characterId");

            viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

            viewModel.getCharacterSheetFromDatabase(userId, characterId);
            viewModel.getCharacterSheetLiveData().observe(this, new Observer<CharacterSheet>() {
                @Override
                public void onChanged(CharacterSheet characterSheet) {
                    // Establece el título de la actividad con el nombre del personaje
                    setTitle(characterSheet.getCharacterName());

                    // Establece el nombre del personaje en el EditText
                    binding.editTextCharacterName.setText(characterSheet.getCharacterName());

                }
            });

        }
    }

    // Este método recupera la hoja de personaje modificada de las vistas
    private CharacterSheet getModifiedCharacterSheet() {
        CharacterSheet modifiedCharacterSheet = new CharacterSheet();
        modifiedCharacterSheet.setCharacterName(characterNameEditText.getText().toString());
        return modifiedCharacterSheet;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.character_sheet_toolbar_menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            // Obtén la hoja de personaje actual
            CharacterSheet currentCharacterSheet = viewModel.getCharacterSheetLiveData().getValue();

            // Comprueba si la hoja de personaje no es nula
            if (currentCharacterSheet != null) {
                // Establece los nuevos valores en la hoja de personaje
                updateCharacterSheet(currentCharacterSheet);

                // Guarda la hoja de personaje modificada en la base de datos
                String userId = getIntent().getExtras().getString("userId");
                String characterId = getIntent().getExtras().getString("characterId");
                viewModel.saveCharacterSheetToDatabase(userId, characterId, currentCharacterSheet);

                // Refresca UI y Finaliza la actividad
                setResult(RESULT_OK);
                finish();
            }

            return true;
        } else if (id == R.id.action_delete) {
            // Muestra un diálogo de confirmación para eliminar el personaje
            Drawable icon = ContextCompat.getDrawable(this, android.R.drawable.ic_dialog_alert); // Obtener el icono de alerta
            if (icon != null) { // Si el icono no es nulo, establecer el color del filtro
                icon.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.google_red), PorterDuff.Mode.SRC_IN));
            }
            new AlertDialog.Builder(this)
                    .setIcon(icon) // Establecer el icono
                    .setTitle("Eliminar personaje")
                    .setMessage("¿Estás seguro de que quieres eliminar este personaje?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Aquí va tu código para borrar el personaje
                            // Por ejemplo, puedes llamar a un método deleteCharacter()
                            deleteCharacter();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();

            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteCharacter() {
        // Obtén la hoja de personaje actual
        CharacterSheet currentCharacterSheet = viewModel.getCharacterSheetLiveData().getValue();

        // Comprueba si la hoja de personaje no es nula
        if (currentCharacterSheet != null) {
            // Borra la hoja de personaje de la base de datos
            String userId = getIntent().getExtras().getString("userId");
            String characterId = getIntent().getExtras().getString("characterId");

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + userId + "/characterSheets");
            ref.child(characterId).removeValue();

            // Refresca UI y Finaliza la actividad
            setResult(RESULT_OK);

            // Inicia MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            // Cierra la actividad actual
            finish();
        }
    }


    private void updateCharacterSheet(CharacterSheet currentCharacterSheet) {
        // Establece los nuevos valores en la hoja de personaje
        currentCharacterSheet.setCreationTimestamp(System.currentTimeMillis());
        currentCharacterSheet.setCharacterName(binding.editTextCharacterName.getText().toString());
        currentCharacterSheet.setSpecies(binding.editTextSpecies.getText().toString());
        currentCharacterSheet.setRank(binding.editTextRank.getText().toString());
        currentCharacterSheet.setEnvironment(binding.editTextEnvironment.getText().toString());
        currentCharacterSheet.setUpbringing(binding.editTextUpbringing.getText().toString());
        currentCharacterSheet.setAssignment(binding.editTextAssignment.getText().toString());
        currentCharacterSheet.setAcademy(binding.editTextAcademy.getText().toString());
        currentCharacterSheet.setCareer(binding.editTextCareer.getText().toString());
        currentCharacterSheet.setTraits(binding.editTextTraits.getText().toString());
        currentCharacterSheet.setValues(binding.editTextValues.getText().toString());
        currentCharacterSheet.setControl(Integer.parseInt(binding.editTextControl.getText().toString()));
        currentCharacterSheet.setFitness(Integer.parseInt(binding.editTextFitness.getText().toString()));
        currentCharacterSheet.setPresence(Integer.parseInt(binding.editTextPresence.getText().toString()));
        currentCharacterSheet.setDaring(Integer.parseInt(binding.editTextDaring.getText().toString()));
        currentCharacterSheet.setInsight(Integer.parseInt(binding.editTextInsight.getText().toString()));
        currentCharacterSheet.setReason(Integer.parseInt(binding.editTextReason.getText().toString()));
        currentCharacterSheet.setCommand(Integer.parseInt(binding.editTextCommand.getText().toString()));
        currentCharacterSheet.setSecurity(Integer.parseInt(binding.editTextSecurity.getText().toString()));
        currentCharacterSheet.setScience(Integer.parseInt(binding.editTextScience.getText().toString()));
        currentCharacterSheet.setConn(Integer.parseInt(binding.editTextConn.getText().toString()));
        currentCharacterSheet.setEngineering(Integer.parseInt(binding.editTextEngineering.getText().toString()));
        currentCharacterSheet.setMedicine(Integer.parseInt(binding.editTextMedicine.getText().toString()));
        currentCharacterSheet.setValues(binding.editTextValues.getText().toString());
        currentCharacterSheet.setTalents(binding.editTextTalents.getText().toString());
        currentCharacterSheet.setStress(Integer.parseInt(binding.editTextStress.getText().toString()));
        currentCharacterSheet.setMaxStress(Integer.parseInt(binding.editTextMaxStress.getText().toString()));
        currentCharacterSheet.setResistance(Integer.parseInt(binding.editTextResistance.getText().toString()));
        currentCharacterSheet.setAttacks(binding.editTextAttacks.getText().toString());
        currentCharacterSheet.setEquipment(binding.editTextEquipment.getText().toString());
        currentCharacterSheet.setInjuries(binding.editTextInjuries.getText().toString());
        currentCharacterSheet.setReputation(Integer.parseInt(binding.editTextReputation.getText().toString()));
        currentCharacterSheet.setPrivilege(Integer.parseInt(binding.editTextPrivilege.getText().toString()));
        currentCharacterSheet.setResponsibility(Integer.parseInt(binding.editTextResponsibility.getText().toString()));
        currentCharacterSheet.setFocuses(binding.editTextFocuses.getText().toString());
        currentCharacterSheet.setDetermination(Integer.parseInt(binding.editTextDetermination.getText().toString()));
        currentCharacterSheet.setAge(Integer.parseInt(binding.editTextAge.getText().toString()));
        currentCharacterSheet.setSkin(binding.editTextSkin.getText().toString());
        currentCharacterSheet.setHair(binding.editTextHair.getText().toString());
        currentCharacterSheet.setEyes(binding.editTextEyes.getText().toString());
        currentCharacterSheet.setHeight(Integer.parseInt(binding.editTextHeight.getText().toString()));
        currentCharacterSheet.setWeight(Integer.parseInt(binding.editTextWeight.getText().toString()));
        currentCharacterSheet.setEvent1(binding.editTextEvents.getText().toString());
        currentCharacterSheet.setNotesAndAwards(binding.editTextNotesAndAwards.getText().toString());
    }

}