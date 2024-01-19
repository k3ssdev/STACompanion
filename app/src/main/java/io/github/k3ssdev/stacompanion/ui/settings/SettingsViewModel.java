package io.github.k3ssdev.stacompanion.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// Esta clase representa el modelo de vista para el fragmento de configuración.
public class SettingsViewModel extends ViewModel {

    // LiveData que contiene el texto a mostrar en el fragmento de configuración.
    private final MutableLiveData<String> mText;

    // Constructor de la clase.
    // Inicializa mText con el valor "This is settings fragment".
    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is settings fragment");
    }

    // Este método devuelve la LiveData que contiene el texto a mostrar en el fragmento de configuración.
    public LiveData<String> getText() {
        return mText;
    }
}