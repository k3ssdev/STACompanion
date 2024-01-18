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

public class CharacterSheetViewModel extends ViewModel {

        private static final String TAG = "CharacterSheetViewModel";

        private MutableLiveData<CharacterSheet> characterSheetLiveData = new MutableLiveData<>();

        public LiveData<CharacterSheet> getCharacterSheetLiveData() {
            return characterSheetLiveData;
        }

        public void getCharacterSheetFromDatabase(String characterId) {

            Log.d(TAG, "getCharacterSheetFromDatabase called with characterId: " + characterId);

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

            dbRef.child("characterSheets").child(characterId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                CharacterSheet characterSheet = dataSnapshot.getValue(CharacterSheet.class);
                                Log.d(TAG, "CharacterSheet obtained from database: " + characterSheet);
                                characterSheetLiveData.setValue(characterSheet);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "get failed with ", databaseError.toException());
                        }
                    });
        }
    }