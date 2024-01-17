package io.github.k3ssdev.stacompanion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

/**
 * LoginActivity es una clase que representa la pantalla de inicio de sesión de la aplicación.
 * Esta clase se encarga de autenticar al usuario utilizando Firebase Auth y Google Sign-In.
 */
public class LoginActivity extends AppCompatActivity {
    // Atributos para la autenticación con Firebase
    FirebaseAuth mAuth;

    /**
     * signInLauncher es un lanzador de actividad que maneja el resultado de la actividad de inicio de sesión.
     * Si el inicio de sesión es exitoso, el usuario es redirigido a MainActivity.
     */
    private final ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        IdpResponse response = IdpResponse.fromResultIntent(result.getData());

                        if (result.getResultCode() == RESULT_OK) {
                            // Inicio de sesión correcto
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // Ir a MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            // Mostrar mensaje de confirmación
                            Toast.makeText(LoginActivity.this, "¡Login correcto!", Toast.LENGTH_SHORT).show();

                        } else {
                            // Mostrar mensaje de error
                            Toast.makeText(LoginActivity.this, "¡Error en login!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

    /**
     * Método onCreate de la actividad.
     * Este método se llama cuando se crea la actividad. Se encarga de inicializar Firebase Auth,
     * configurar los escuchadores de clics para los botones de inicio de sesión y registro,
     * y configurar Google Sign-In.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Obtiene referencias a los elementos de la interfaz de usuario
        EditText editTextUsername = findViewById(R.id.username);
        EditText editTextPassword = findViewById(R.id.password);
        Button buttonLogin = findViewById(R.id.loginButton);

        // Configura el escuchador de clics para el botón de inicio de sesión
        buttonLogin.setOnClickListener(v -> {
            String email = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                // Manejar el caso en que el correo electrónico o la contraseña estén vacíos
                Toast.makeText(LoginActivity.this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Intenta iniciar sesión con el correo electrónico y la contraseña proporcionados
            try {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Inicio de sesión exitoso
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(LoginActivity.this, "¡Login correcto!", Toast.LENGTH_SHORT).show();
                                // Iniciar MainActivity
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                Toast.makeText(LoginActivity.this, "¡Error!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura el escuchador de clics para el botón de registro
        TextView buttonRegister = findViewById(R.id.signupText);
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        // Configura el escuchador de clics para el botón de inicio de sesión de Google
        SignInButton googleButton = findViewById(R.id.sign_in_button);
        googleButton.setOnClickListener(v -> {
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Collections.singletonList(
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            // Create and launch sign-in intent
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();

            signInLauncher.launch(signInIntent);
        });
    }

    /**
     * Este método se llama cuando la actividad se inicia.
     * Comprueba si el usuario ya ha iniciado sesión y, en caso afirmativo, redirige al usuario a MainActivity.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    /**
     * Este método actualiza la interfaz de usuario en función del estado de inicio de sesión del usuario.
     * Si el usuario ha iniciado sesión, se redirige al usuario a MainActivity.
     * Si el usuario no ha iniciado sesión, se muestra el formulario de inicio de sesión.
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            // User is signed in, navigate to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is signed out, show login form
            // This might be unnecessary if you're already in LoginActivity
            // and the login form is always visible when the user is signed out
        }
    }
}