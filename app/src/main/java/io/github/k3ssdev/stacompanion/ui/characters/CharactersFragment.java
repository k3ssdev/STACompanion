package io.github.k3ssdev.stacompanion.ui.characters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    private static final String TAG = "CharactersFragment";

    private RecyclerView recyclerView;
    private CharacterSheetAdapter adapter;
    private View view;
    private CharacterSheet sheet;

    public interface OnItemClickListener {
        void onItemClick(CharacterSheet sheet);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);
        initializeRecyclerView();
        setupDatabaseConnection();
        return view;
    }

    private void initializeRecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CharacterSheetAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(sheetDestails -> {
            Toast.makeText(getContext(), "Opening " + sheetDestails.getCharacterName(), Toast.LENGTH_SHORT).show();
        });
    }

    private void setupDatabaseConnection() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance("https://stacompanion-a1286-default-rtdb.europe-west1.firebasedatabase.app/").getReference("characterSheets");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clearCharacterSheets();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    CharacterSheet sheet = childSnapshot.getValue(CharacterSheet.class);
                    if (sheet.getUserId().equals(userId)) {
                        adapter.addCharacterSheet(sheet);
                        Log.d(TAG, "onDataChange: " + sheet.getId() + " " + sheet.getUserId() + " " + sheet.getCharacterName());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
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