package io.github.k3ssdev.stacompanion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;


// autenticación con Firebase
// https://firebase.google.com/docs/auth/android/start?hl=es-419
// https://firebase.google.com/docs/auth/android/password-auth?hl=es-419
// https://firebase.google.com/docs/auth/android/manage-users?hl=es-419
// https://firebase.google.com/docs/auth/android/phone-auth?hl=es-419
// https://firebase.google.com/docs/auth/android/google-signin?hl=es-419
// https://firebase.google.com/docs/auth/android/facebook-login?hl=es-419

import io.github.k3ssdev.stacompanion.R;

public class LoginActivity extends AppCompatActivity {
    // Constante para el mensaje extra
    //public static final String EXTRA_MESSAGE = "io.github.k3ssdev.loginformsqliteandroid.MESSAGE";

    // Atributos para la autenticación con Firebase
    FirebaseAuth mAuth;


    // Método onCreate de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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

    }
}