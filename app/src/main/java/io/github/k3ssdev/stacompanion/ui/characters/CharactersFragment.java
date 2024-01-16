package io.github.k3ssdev.stacompanion.ui.characters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;
import io.github.k3ssdev.stacompanion.data.CharacterSheetAdapter;

public class CharactersFragment extends Fragment {

    private RecyclerView recyclerView;
    private CharacterSheetAdapter adapter;
    private View view;
    private CharacterSheet sheet;

    private static final String TAG = "CharactersFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        adapter = new CharacterSheetAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

       // String documentId = "Tx5ZOk7KScRUVStDeaLzDAmsi1U2";

        CharacterSheet sheet = new CharacterSheet(
                "2", // id
                userId, // userId
                "Jonh Connor", // characterName
                "Human", // species
                "Earth", // environment
                "Captain", // rank
                "Rural", // upbringing
                "Starship Command", // assignment
                "Brave", // traits
                35, // control
                7, // fitness
                8, // presence
                9, // daring
                6, // insight
                7, // reason
                8, // command
                9, // security
                7, // science
                6, // conn
                8, // engineering
                7, // medicine
                35, // age
                "Black", // skin
                "Black", // hair
                75, // weight
                180, // height
                "Blue", // eyes
                6, // reputation
                9, // privilege
                8, // responsibility
                "Starship Command", // focuses
                7, // determination
                "Duty, Honor, Loyalty", // values
                "Leadership", // talents
                "Phaser", // attacks
                "Starship", // equipment
                7, // stress
                5, // currentStress
                10, // maxStress
                8, // resistance
                "Starfleet Medal of Honor", // notesAndAwards
                "None", // injuries
                "Starfleet Academy", // academy
                "Starfleet Officer", // career
                "First Contact", // event1
                "Promotion to Captain" // event2
        );

        // Conexión a la base de datos y guardado de la hoja de personaje de prueba
        DatabaseReference ref = FirebaseDatabase.getInstance("https://stacompanion-a1286-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        ref.child("characterSheets").child(sheet.getId().toString()).setValue(sheet);

        // Lectura de la base de datos
        ref = FirebaseDatabase.getInstance("https://stacompanion-a1286-default-rtdb.europe-west1.firebasedatabase.app/").getReference("characterSheets");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clearCharacterSheets(); // Limpia los datos del adaptador
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    CharacterSheet sheet = childSnapshot.getValue(CharacterSheet.class);
                    if (sheet.getUserId().equals(userId)) {
                        adapter.addCharacterSheet(sheet);
                        Log.d(TAG, "onDataChange: " + sheet.getId() + " " + sheet.getUserId() + " " + sheet.getCharacterName());
                    }
                }
                adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveCharacterSheet(CharacterSheet sheet) {
        this.sheet = sheet;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("characterSheets");
        ref.child(sheet.getCharacterName()).setValue(sheet);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }
}