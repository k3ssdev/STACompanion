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
        // inflar la vista del fragmento y obtener una referencia al RecyclerView
        view = inflater.inflate(R.layout.fragment_character, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        // inicializar el adaptador y establecerlo en el RecyclerView
        adapter = new CharacterSheetAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // usar un administrador de diseño lineal
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener el ID del usuario actualmente autenticado
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Crear una referencia a la ubicación de los datos del personaje en Firebase Database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("D de documento/" + userId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CharacterSheet sheet = dataSnapshot.getValue(CharacterSheet.class);
                // añadir la hoja de personaje a la lista y notificar al adaptador
                adapter.addCharacterSheet(sheet);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Se produjo un error al leer los datos
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // Esto hace que el fragmento reciba llamadas a onCreateOptionsMenu() y onOptionsItemSelected()
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
            // Aquí manejas el clic en el botón de añadir ficha
            // Por ejemplo, puedes abrir una nueva actividad o mostrar un diálogo
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