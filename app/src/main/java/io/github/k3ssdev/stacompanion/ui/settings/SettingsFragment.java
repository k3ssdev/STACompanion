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

public class SettingsFragment extends Fragment {

    private io.github.k3ssdev.stacompanion.databinding.FragmentAccountBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = io.github.k3ssdev.stacompanion.databinding.FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Display user information
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            // Update your UI with the user information
            TextView userNameTextView = binding.userName;
            TextView userEmailTextView = binding.userEmail;
            userNameTextView.setText(name);
            userEmailTextView.setText(email);
        }

        // Set up sign out button
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

        // Set up delete account button
        Button deleteAccountButton = binding.deleteAccountButton;
        deleteAccountButton.setOnClickListener(v -> {
            currentUser.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Update your UI for the account deleted state
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}