package io.github.k3ssdev.stacompanion.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.k3ssdev.stacompanion.LoginActivity;
import io.github.k3ssdev.stacompanion.R;

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

        Drawable icon = ContextCompat.getDrawable(getContext(), android.R.drawable.ic_dialog_alert); // Obtener el icono de alerta
        if (icon != null) { // Si el icono no es nulo, establecer el color del filtro
            icon.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), R.color.google_red), PorterDuff.Mode.SRC_IN));
        }
        // Configurar el botón de eliminar cuenta
        Button deleteAccountButton = binding.deleteAccountButton;
        deleteAccountButton.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert) // Icono de advertencia
                    .setTitle("Eliminar cuenta")
                    .setMessage("¿Estás seguro de que quieres eliminar tu cuenta?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
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
                        }
                    })
                    .setNegativeButton("No", null) // Foco inicial en el botón "No"
                    .show();
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