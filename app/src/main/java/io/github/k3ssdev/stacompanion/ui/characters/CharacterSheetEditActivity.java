package io.github.k3ssdev.stacompanion.ui.characters;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;

public class CharacterSheetEditActivity extends AppCompatActivity {

    private EditText characterNameEditText;
    private CharacterSheetViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_sheet_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        characterNameEditText = findViewById(R.id.editTextCharacterName);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userId = extras.getString("userId");
            String characterId = extras.getString("characterId");

            viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

            viewModel.getCharacterSheetFromDatabase(userId, characterId);
            viewModel.getCharacterSheetLiveData().observe(this, new Observer<CharacterSheet>() {
                @Override
                public void onChanged(CharacterSheet characterSheet) {
                    // Aquí puedes actualizar tus vistas con los datos de characterSheet
                    // Por ejemplo, podrías establecer el texto del EditText con el nombre del personaje
                    characterNameEditText.setText(characterSheet.getCharacterName());
                }
            });

        }
    }

    // Este método recupera la hoja de personaje modificada de las vistas
    // Deberías reemplazar esto con tu propia lógica para recuperar la hoja de personaje modificada
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
            // Handle save action here
            CharacterSheet modifiedCharacterSheet = getModifiedCharacterSheet();
            // Save modifiedCharacterSheet in the database
            return true;
        } else if (id == android.R.id.home) {
            // Handle back action here
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
