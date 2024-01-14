package io.github.k3ssdev.stacompanion;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // Constante para el mensaje extra
    //public static final String EXTRA_MESSAGE = "io.github.k3ssdev.loginformsqliteandroid.MESSAGE";

    // Atributos para la autenticación con Firebase
    FirebaseAuth mAuth;

    // Atributos para Google One Tap
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    // ...

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    String username = credential.getId();
                    String password = credential.getPassword();
                    if (idToken != null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d(TAG, "Got ID token.");
                    } else if (password != null) {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d(TAG, "Got password.");
                    }
                } catch (ApiException e) {
                    // ...
                }
                break;
        }
    }

    // Método onCreate de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Inicializa Google One Tap
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(
                        BeginSignInRequest.PasswordRequestOptions.builder()
                                .setSupported(true)
                                .build()
                )
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                                .setSupported(true)
                                .setServerClientId(getString(R.string.server_client_id)) // replace with your server client ID
                                .build()
                )
                .build();

        // Obtiene referencias a los elementos de la interfaz de usuario
        EditText editTextUsername = findViewById(R.id.editTextUsername);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> {
            String email = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                // Manejar el caso en que el correo electrónico o la contraseña estén vacíos
                Toast.makeText(LoginActivity.this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                return;
            }

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

        // Configurar un escuchador de clic para el botón de registro
        Button buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(v -> {
            // Iniciar RegisterActivity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Configurar un escuchador de clic para el botón de registro


    }

    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                    handleSignInResult(credential);
                } catch (ApiException e) {
                    // Handle error
                }
            }
    );

    private void handleSignInResult(SignInCredential credential) {
        // Handle sign in
        // You can get the user's Google ID token with `credential.getGoogleIdToken()`
        String googleIdToken = credential.getGoogleIdToken();

        // You can get the user's email with `credential.getId()`
        String email = credential.getId();

        // Now you can use googleIdToken and email as needed
        // For example, you can authenticate the user with Firebase
        if (googleIdToken != null) {
            mAuth.signInWithCustomToken(googleIdToken)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "¡Login correcto!", Toast.LENGTH_SHORT).show();
                            // Start MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "¡Login incorrecto!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

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

    @Override
    protected void onStop() {
        super.onStop();
        // Sign out from One Tap
        oneTapClient.signOut().addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Log.d(TAG, "Signed out from One Tap");
            } else {
                Log.d(TAG, "Failed to sign out from One Tap");
            }
        });
    }
}