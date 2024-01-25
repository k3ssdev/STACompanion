package io.github.k3ssdev.stacompanion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
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
                        // Obtiene la respuesta del intento de inicio de sesión
                        //IdpResponse response = IdpResponse.fromResultIntent(result.getData());

                        if (result.getResultCode() == RESULT_OK) {
                            // Inicio de sesión correcto
                            // Obtiene el usuario actual
                            //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            // Crea un intent para iniciar MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            // Inicia MainActivity
                            startActivity(intent);
                            // Finaliza LoginActivity
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
            // Obtiene el correo electrónico y la contraseña del usuario
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
                                // Obtiene el usuario actual
                                //FirebaseUser user = mAuth.getCurrentUser();
                                // Muestra un mensaje de confirmación
                                Toast.makeText(LoginActivity.this, "¡Login correcto!", Toast.LENGTH_SHORT).show();
                                // Crea un intent para iniciar MainActivity
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                // Inicia MainActivity
                                startActivity(intent);
                                // Finaliza LoginActivity
                                finish();
                            } else {
                                // Muestra un mensaje de error
                                Toast.makeText(LoginActivity.this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e) {
                // Muestra un mensaje de error
                Toast.makeText(LoginActivity.this, "¡Error!", Toast.LENGTH_SHORT).show();
            }
        });

        // Configura el escuchador de clics para el botón de registro
        TextView buttonRegister = findViewById(R.id.signupText);
        buttonRegister.setOnClickListener(v -> {
            // Define los proveedores de inicio de sesión
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build()
            );

            // Crea el intento de inicio de sesión
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setTheme(R.style.Theme_STA_Companion)
                    .setAvailableProviders(providers)
                    .build();

            // Inicia el intento de inicio de sesión
            signInLauncher.launch(signInIntent);
        });

        // Configura el escuchador de clics para el botón de inicio de sesión de Google
        SignInButton googleButton = findViewById(R.id.sign_in_button);
        googleButton.setOnClickListener(v -> {
            // Define los proveedores de inicio de sesión
            List<AuthUI.IdpConfig> providers = Collections.singletonList(
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            // Crea el intento de inicio de sesión
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setTheme(R.style.Theme_STA_Companion)
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(true)
                    .build();

            // Inicia el intento de inicio de sesión
            signInLauncher.launch(signInIntent);
        });

/*        TextView legalLink = findViewById(R.id.legalLink);
        legalLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LegalActivity.class);
                intent.putExtra("filename", "privacy_policy.html"); // Cambia esto por el nombre del archivo que quieras mostrar
                startActivity(intent);
            }
        });*/
    }

    /**
     * Este método se llama cuando la actividad se inicia.
     * Comprueba si el usuario ya ha iniciado sesión y, en caso afirmativo, redirige al usuario a MainActivity.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Comprueba si el usuario ya ha iniciado sesión
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Actualiza la interfaz de usuario en función del estado de inicio de sesión del usuario
        updateUI(currentUser);
    }

    /**
     * Este método actualiza la interfaz de usuario en función del estado de inicio de sesión del usuario.
     * Si el usuario ha iniciado sesión, se redirige al usuario a MainActivity.
     * Si el usuario no ha iniciado sesión, se muestra el formulario de inicio de sesión.
     */
    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            // Si el usuario ya ha iniciado sesión, redirigir al usuario a MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            // Inicia MainActivity
            startActivity(intent);
            // Finaliza LoginActivity
            finish();
        } else {

            // Mensaje de depuración
            Log.d("LoginActivity", "updateUI: currentUser is null");
        }
    }
}