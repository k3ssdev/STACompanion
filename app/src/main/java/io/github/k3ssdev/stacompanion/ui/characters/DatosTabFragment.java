package io.github.k3ssdev.stacompanion.ui.characters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import io.github.k3ssdev.stacompanion.R;
import io.github.k3ssdev.stacompanion.databinding.FragmentDatosTabBinding;

public class DatosTabFragment extends Fragment {

    private CharacterSheetViewModel viewModel;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DatosTabFragment() {
        // Required empty public constructor
    }

    public static DatosTabFragment newInstance(String param1, String param2) {
        DatosTabFragment fragment = new DatosTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Use Data Binding to inflate the view
        FragmentDatosTabBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_datos_tab, container, false);

        // Check if binding is null
        if (binding == null) {
            throw new RuntimeException("Data Binding error occurred!");
        }

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(CharacterSheetViewModel.class);

        // Set the ViewModel in your binding
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Rest of your code...

        return binding.getRoot();
    }
}