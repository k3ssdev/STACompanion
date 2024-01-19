package io.github.k3ssdev.stacompanion;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.github.k3ssdev.stacompanion.databinding.ActivityMainBinding;

// Esta clase representa la actividad principal de la aplicación.
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // Este método se llama cuando se crea la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Infla el diseño para esta actividad
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configura la barra de navegación inferior
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configura la barra de aplicaciones con el controlador de navegación
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    // Este método se llama cuando se destruye la actividad.
    @Override
    public boolean onSupportNavigateUp() {
        // Configura el controlador de navegación
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}