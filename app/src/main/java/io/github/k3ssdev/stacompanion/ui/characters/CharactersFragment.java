package io.github.k3ssdev.stacompanion.ui.characters;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import io.github.k3ssdev.stacompanion.data.CharacterFragmentAdapter;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;
import io.github.k3ssdev.stacompanion.ui.characters.charactersheet.CharacterSheetFragment;

// Esta clase representa el fragmento de personajes en la aplicación.
public class CharactersFragment extends Fragment {

    private static final String TAG = "CharactersFragment";

    private RecyclerView recyclerView;
    private CharacterFragmentAdapter adapter;
    private View view;
    private CharacterSheet sheet;

    // Esta interfaz define un método para manejar los clics en los elementos de la lista.
    public interface OnItemClickListener {
        void onItemClick(CharacterSheet sheet);
    }

    // Este método se llama para inflar el diseño del fragmento.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);
        initializeRecyclerView();
        setupDatabaseConnection();
        return view;
    }

    // Este método inicializa el RecyclerView que muestra la lista de personajes.
    private void initializeRecyclerView() {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CharacterFragmentAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(sheetDetails -> {
            // Crear una nueva instancia de CharacterSheetFragment
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            CharacterSheetFragment characterSheetFragment = CharacterSheetFragment.newInstance(userId, sheetDetails.getId(), false);
            Bundle args = new Bundle();
            args.putString("userId", userId);
            args.putString("characterId", sheetDetails.getId());
            characterSheetFragment.setArguments(args);

            // Utilizar NavController para navegar al CharacterSheetFragment
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.characterSheetFragment, args);
        });
    }


    // Este método se llama cuando se crea el fragmento.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    // Este método se llama para inflar el menú de opciones.
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

    // Este método se llama cuando se selecciona un elemento del menú de opciones.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {

            Toast.makeText(getContext(), "Añadir personaje", Toast.LENGTH_SHORT).show();

            // Crear un nuevo objeto CharacterSheet con los datos del personaje
            CharacterSheet newCharacter = new CharacterSheet();
            // Obtener el ID del usuario actual
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Crear una referencia a la base de datos
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + userId + "/characterSheets");

            // Genera un ID único para la nueva hoja de personaje
            String newId = ref.push().getKey();

            newCharacter.setId(newId);
            newCharacter.setUserId(userId);

            newCharacter.setCharacterName("Nombre del personaje");
            newCharacter.setSpecies("Especie");
            newCharacter.setCreationTimestamp(System.currentTimeMillis());


            // Añadir la hoja de personaje a la base de datos
            addCharacterSheet(newCharacter);
            return true;
        }

        if (item.getItemId() == R.id.action_delete) {
            // Preguntar al usuario si está seguro de que quiere eliminar el personaje en popup
            new AlertDialog.Builder(getContext())
                    .setTitle("Eliminar personaje")
                    .setMessage("¿Estás seguro de que quieres eliminar este personaje?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Si el usuario confirma, eliminar el personaje de la base de datos
                            deleteCharacterSheet(sheet);
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Este método establece la conexión con la base de datos y recupera los datos de los personajes.
    private void setupDatabaseConnection() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance("https://stacompanion-a1286-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users/" + userId + "/characterSheets");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clearCharacterSheets();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    CharacterSheet sheet = childSnapshot.getValue(CharacterSheet.class);
                    if (sheet.getUserId() != null && sheet.getUserId().equals(userId)) {
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


    // Metodo para añadir un personaje a la base de datos
    public void addCharacterSheet(CharacterSheet sheet) {
        this.sheet = sheet;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + userId + "/characterSheets");
        ref.child(sheet.getId()).setValue(sheet);
    }


    // Metodo para eliminar un personaje de la base de datos
    public void deleteCharacterSheet(CharacterSheet sheet) {
        this.sheet = sheet;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + userId + "/characterSheets");
        ref.child(sheet.getId()).removeValue();
    }


    // Editar un personaje
    public void editCharacterSheet(CharacterSheet sheet) {
        this.sheet = sheet;
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + userId + "/characterSheets");
        ref.child(sheet.getId()).setValue(sheet);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }
}