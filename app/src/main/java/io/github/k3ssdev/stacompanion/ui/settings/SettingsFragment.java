package io.github.k3ssdev.stacompanion.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.k3ssdev.stacompanion.LoginActivity;

// Esta clase representa el fragmento de configuración en la aplicación.
public class SettingsFragment extends Fragment {

    private io.github.k3ssdev.stacompanion.databinding.FragmentAccountBinding binding;
    private FirebaseAuth mAuth;

    // Este método se llama cuando se crea el fragmento.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("");
    }

    // Este método se llama para inflar el diseño del fragmento.
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        requireActivity().setTitle("");

        binding = io.github.k3ssdev.stacompanion.databinding.FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Mostrar información del usuario
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            // Actualizar la interfaz de usuario con la información del usuario
            TextView userNameTextView = binding.userName;
            TextView userEmailTextView = binding.userEmail;
            userNameTextView.setText(name);
            userEmailTextView.setText(email);
        }

        // Configurar el botón de cierre de sesión
        Button signOutButton = binding.signOutButton;
        signOutButton.setOnClickListener(v -> {
            mAuth.signOut();
            // Volver a login
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

            // Mostrar mensaje de confirmación
            Toast.makeText(getActivity(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

        });

        // Configurar el botón de eliminar cuenta
        Button deleteAccountButton = binding.deleteAccountButton;
        deleteAccountButton.setOnClickListener(v -> {
            currentUser.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Actualizar la interfaz de usuario para el estado de cuenta eliminada
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                            // Mostrar mensaje de confirmación
                            Toast.makeText(getActivity(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        return root;
    }

    // Este método se llama cuando se destruye la vista del fragmento.
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}