package io.github.k3ssdev.stacompanion.ui.characters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.github.k3ssdev.stacompanion.data.CharacterSheet;

// Esta clase representa el modelo de vista para la hoja de personaje.
public class CharacterSheetViewModel extends ViewModel {

    // Etiqueta para los mensajes de registro.
    private static final String TAG = "CharacterSheetViewModel";

    // LiveData que contiene la hoja de personaje.
    private MutableLiveData<CharacterSheet> characterSheetLiveData = new MutableLiveData<>();

    // Este método devuelve la LiveData que contiene la hoja de personaje.
    public LiveData<CharacterSheet> getCharacterSheetLiveData() {
        return characterSheetLiveData;
    }

    // Este método recupera la hoja de personaje de la base de datos utilizando el ID del personaje.
    public void getCharacterSheetFromDatabase(String userId, String characterId) {

        // Registro de la llamada al método.
        Log.d(TAG, "getCharacterSheetFromDatabase called with characterId: " + characterId);

        // Referencia a la base de datos.
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        // Consulta a la base de datos para obtener la hoja de personaje.
        dbRef.child("users").child(userId).child("characterSheets").child(characterId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Si la hoja de personaje existe, se recupera y se establece en la LiveData.
                            CharacterSheet characterSheet = dataSnapshot.getValue(CharacterSheet.class);
                            Log.d(TAG, "CharacterSheet obtained from database: " + characterSheet);
                            characterSheetLiveData.setValue(characterSheet);
                        } else {
                            // Si la hoja de personaje no existe, se registra un mensaje.
                            Log.d(TAG, "No such document");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Si la consulta a la base de datos falla, se registra el error.
                        Log.d(TAG, "get failed with ", databaseError.toException());
                    }
                });
    }

    // Este método devuelve el nombre del personaje.
    // Actualmente, este método no está implementado y siempre devuelve null.
    // TODO: Implementar este método para que devuelva el nombre del personaje.
    public CharSequence getCharacterName() {
        return null;
    }
}