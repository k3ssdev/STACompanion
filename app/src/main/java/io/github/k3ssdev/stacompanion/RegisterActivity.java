package io.github.k3ssdev.stacompanion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    // Implementación de la autenticación con Firebase
    FirebaseAuth mAuth;

    private final ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        IdpResponse response = IdpResponse.fromResultIntent(result.getData());

                        if (result.getResultCode() == RESULT_OK) {
                            // Successfully signed in
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "¡Login correcto!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Mostrar mensaje de error
                            Toast.makeText(RegisterActivity.this, "¡Error en login!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_register);

        // Inicialización de la instancia de autenticación con Firebase
        mAuth = FirebaseAuth.getInstance();

        // Verificar si el usuario ya está autenticado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // El usuario ya está autenticado, por lo que se inicia la actividad principal
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        // Iniciar el flujo de inicio de sesión cuando sea apropiado, por ejemplo, en un escuchador de clics de botón
        startSignInFlow();

        finish();

    }

    private void startSignInFlow() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();

        signInLauncher.launch(signInIntent);

    }
}