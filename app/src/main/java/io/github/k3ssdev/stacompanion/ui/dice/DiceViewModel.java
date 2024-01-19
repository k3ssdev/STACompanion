package io.github.k3ssdev.stacompanion.ui.dice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

// Esta clase representa el modelo de vista para el fragmento de bandeja de dados.
public class DiceViewModel extends ViewModel {

    // LiveData que contiene el texto a mostrar en el fragmento de bandeja de dados.
    private final MutableLiveData<String> mText;

    // Constructor de la clase.
    // Inicializa mText con el valor "This is dice tray fragment".
    public DiceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dice tray fragment");
    }

    // Este método devuelve la LiveData que contiene el texto a mostrar en el fragmento de bandeja de dados.
    public LiveData<String> getText() {
        return mText;
    }
}