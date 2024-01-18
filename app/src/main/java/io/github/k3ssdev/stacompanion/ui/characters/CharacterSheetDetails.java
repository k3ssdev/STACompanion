package io.github.k3ssdev.stacompanion.ui.characters;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;

public class CharacterSheetDetails extends AppCompatActivity {
    private CharacterSheet characterSheet;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_character_sheet); // replace with your layout

        // Retrieve the CharacterSheet object from the Intent
        Intent intent = getIntent();
        characterSheet = (CharacterSheet) intent.getSerializableExtra("CharacterSheet");

        displayCharacterDetails();
    }

    public void displayCharacterDetails() {
        TextView characterNameTextView = findViewById(R.id.characterNameValue);
        characterNameTextView.setText(characterSheet.getCharacterName());

        TextView speciesTextView = findViewById(R.id.speciesValue);
        speciesTextView.setText(characterSheet.getSpecies());

        TextView rankTextView = findViewById(R.id.rankValue);
        rankTextView.setText(characterSheet.getRank());

        // Continue with the rest of the fields...
    }
}