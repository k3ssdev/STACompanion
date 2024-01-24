package io.github.k3ssdev.stacompanion.ui.characters.charactersheet;

import static io.github.k3ssdev.stacompanion.util.PdfUtil.createCharacterSheetPdf;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.io.File;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.data.CharacterSheet;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetEditActivity;
import io.github.k3ssdev.stacompanion.ui.characters.CharacterSheetViewModel;
import io.github.k3ssdev.stacompanion.util.PdfUtil;

// Esta clase representa el fragmento de la hoja de personaje en la aplicación.
public class CharacterSheetFragment extends Fragment {

    private static final int WRITE_REQUEST_CODE = 1;
    private static final int PERMISSION_GRANTED = 1;
    private CharacterSheetViewModel mViewModel;
    ActionBar actionBar;
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso concedido, puedes proceder a guardar el archivo PDF

                } else {
                    // Permiso denegado, muestra un mensaje al usuario
                    Toast.makeText(getContext(), "Permiso denegado para escribir en el almacenamiento externo.", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // Aquí puedes manejar otros casos de 'case' si tienes más permisos que solicitar

        }
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "CharacterSheet.pdf");
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                createCharacterSheetPdf(getContext(), uri, mViewModel.getCharacterSheetLiveData().getValue());
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_edit) {
            // Abre la nueva actividad de edición de la hoja de personaje
            Intent intent = new Intent(getActivity(), CharacterSheetEditActivity.class);
            intent.putExtra("userId", getArguments().getString("userId"));
            intent.putExtra("characterId", getArguments().getString("characterId"));
            startActivityForResult(intent, WRITE_REQUEST_CODE);
            return true;
        } else if (itemId == R.id.action_export_pdf) {
            // Define el nombre del archivo PDF
            String fileName = "myFile.pdf";
            // Crea un archivo en el directorio público "Documents" del almacenamiento externo
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
            String filePath = file.getAbsolutePath();

            // Obtiene la hoja de personaje actual
            CharacterSheet characterSheet = mViewModel.getCharacterSheetLiveData().getValue();

            // Crea el PDF
            //createCharacterSheetPdf(getContext(), filePath, characterSheet);
            openFileChooser();

/*            // Crea un Intent para compartir el archivo PDF
            Uri fileUri = FileProvider.getUriForFile(
                    getContext(),
                    getContext().getApplicationContext().getPackageName() + ".provider",
                    file
            );
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.setType("application/pdf");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "Share PDF"));*/

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

//DEPRECATED
/*
    private void saveData() {
        // Get the root view of the fragment
        View rootView = getView();
        if (rootView == null) {
            return;
        }

        // Get userId and characterId from the arguments
        String userId = getArguments().getString("userId");
        String characterId = getArguments().getString("characterId");

        // Get the character sheet from the ViewModel
        CharacterSheet characterSheet = mViewModel.getCharacterSheetLiveData().getValue();

        // Get the data from the views
        characterSheet.setCharacterName(((EditText) rootView.findViewById(R.id.editTextCharacterName)).getText().toString());

        characterSheet.setStress(Integer.parseInt(((EditText) rootView.findViewById(R.id.editTextStress)).getText().toString()));

        characterSheet.setControl(Integer.parseInt(((EditText) rootView.findViewById(R.id.editTextControl)).getText().toString()));



        // Save the data in the Firebase Realtime Database
        mViewModel.saveCharacterSheetToDatabase(userId, characterId, characterSheet);
    }
*/

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //muestra el titulo de la barra de herramientas
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            // Habilita el titulo de la barra de herramientas solo en CharacterSheetFragment
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (actionBar != null) {
            // Habilita el titulo de la barra de herramientas solo en CharacterSheetFragment
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (actionBar != null) {
            // Habilita el titulo de la barra de herramientas solo en CharacterSheetFragment
            actionBar.setDisplayShowTitleEnabled(false);
        }

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
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Cargando...");
        }

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

        return view;
    }

    // Este método se llama cuando la actividad del fragmento ha sido creada.
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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
                        return DataTabFragment.newInstance(userId, characterId);
                    } else {
                        return DataTabFragment.newInstance(userId, characterId);
                    }
                case 1:
                    if (editMode) {
                        return StatusFragment.newInstance(userId, characterId);
                    } else {
                        return StatusFragment.newInstance(userId, characterId);
                    }
                case 2:
                    if (editMode) {
                        return SkillsFragment.newInstance(userId, characterId);
                    } else {
                        return SkillsFragment.newInstance(userId, characterId);
                    }
                case 3:
                    if (editMode) {
                        return OthersFragment.newInstance(userId, characterId);
                    } else {
                        return OthersFragment.newInstance(userId, characterId);
                    }
                default:
                    return null;
            }
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