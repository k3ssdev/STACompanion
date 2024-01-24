package io.github.k3ssdev.stacompanion.ui.dice;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.k3ssdev.stacompanion.R;

// Esta clase representa el fragmento de dados en la aplicación.
public class DiceFragment extends Fragment {

    // Definición de los elementos de la interfaz de usuario.
    private EditText editTextNumberOfDiceD6;
    private EditText editTextNumberOfDiceD20;
    private Button buttonMinusD6;
    private Button buttonPlusD6;
    private Button buttonMinusD20;
    private Button buttonPlusD20;
    private Button buttonRollDice6;

    private Button buttonRollDice20;
    private TextView textViewDiceResult;
    private ImageView imageViewDice;


    // Este método se llama cuando se crea el fragmento.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("");
    }
    // Este método se llama para inflar el diseño del fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dice, container, false);

        requireActivity().setTitle("");

        // Inicialización de los elementos de la interfaz de usuario.
        editTextNumberOfDiceD6 = view.findViewById(R.id.editTextNumberOfDiceD6);
        editTextNumberOfDiceD20 = view.findViewById(R.id.editTextNumberOfDiceD20);
        buttonMinusD6 = view.findViewById(R.id.buttonMinusD6);
        buttonPlusD6 = view.findViewById(R.id.buttonPlusD6);
        buttonMinusD20 = view.findViewById(R.id.buttonMinusD20);
        buttonPlusD20 = view.findViewById(R.id.buttonPlusD20);
        // boton es un imageview
        ImageView buttonRollDice6 = (ImageView) view.findViewById(R.id.buttonRollDice6);
        buttonRollDice6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 rollDice();
            }
        });
        ImageView buttonRollDice20 = (ImageView) view.findViewById(R.id.buttonRollDice20);
        buttonRollDice6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

        textViewDiceResult = view.findViewById(R.id.textViewDiceResult);
        imageViewDice = view.findViewById(R.id.imageViewDice);

        // Configuración de los listeners de los botones.
        buttonRollDice6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

        buttonMinusD6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseNumberOfDice(editTextNumberOfDiceD6);
            }
        });

        buttonPlusD6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseNumberOfDice(editTextNumberOfDiceD6);
            }
        });

        buttonMinusD20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseNumberOfDice(editTextNumberOfDiceD20);
            }
        });

        buttonPlusD20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseNumberOfDice(editTextNumberOfDiceD20);
            }
        });

        return view;
    }

    // Este método disminuye el número de dados en el EditText proporcionado.
    private void decreaseNumberOfDice(EditText editText) {
        int currentValue = Integer.parseInt(editText.getText().toString());
        if (currentValue >= 1) {
            editText.setText(String.valueOf(currentValue - 1));
        }
    }

    // Este método aumenta el número de dados en el EditText proporcionado.
    private void increaseNumberOfDice(EditText editText) {
        int currentValue = Integer.parseInt(editText.getText().toString());
        editText.setText(String.valueOf(currentValue + 1));
    }

    // Este método maneja el lanzamiento de los dados.
    private void rollDice() {
        // Eliminar los resultados anteriores
        textViewDiceResult.setText("");

        // Obtener la cantidad de dados a lanzar para d6
        int numberOfDiceD6 = getNumberOfDice(editTextNumberOfDiceD6);
        rollSingleDiceType(6, numberOfDiceD6, "d6");

        // Obtener la cantidad de dados a lanzar para d20
        int numberOfDiceD20 = getNumberOfDice(editTextNumberOfDiceD20);
        rollSingleDiceType(20, numberOfDiceD20, "d20");

        // Agregar animación de lanzamiento de dado
        animateDiceRoll();
    }

    // Este método obtiene el número de dados del EditText proporcionado.
    private int getNumberOfDice(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            // Manejar el caso en que el usuario ingresó un valor no válido
            return 0;
        }
    }

    // Este método maneja el lanzamiento de un solo tipo de dado.
    private void rollSingleDiceType(int diceType, int numberOfDice, String diceTypeName) {
        // Mostrar los resultados parciales en el TextView
        StringBuilder resultText = new StringBuilder("Resultados " + diceTypeName + ": \n");
        List<Integer> diceResultsList = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < numberOfDice; i++) {
            int diceResult = random.nextInt(diceType) + 1;
            boolean specialEffect = false;

            if (diceType == 6) {
                // Lanzamiento de dado d6 especial
                switch (diceResult) {
                    case 1:
                        // 1 Éxito
                        diceResult = 1;
                        break;
                    case 2:
                        // 2 Éxitos
                        diceResult = 2;
                        break;
                    case 3:
                    case 4:
                        // 0 Éxitos
                        diceResult = 0;
                        break;
                    case 5:
                    case 6:
                        // 1 Éxito y un Efecto Especial
                        diceResult = 6;
                        specialEffect = true;
                        break;
                }
            }

            diceResultsList.add(diceResult);

            if (diceResult == 6) {
                diceResult = 1;
                resultText.append("*");
            }
            resultText.append(diceResult);

            resultText.append(", ");
        }

        resultText.delete(resultText.length() - 2, resultText.length()); // Eliminar la última coma y espacio
        resultText.append("\n\n");

        // Mostrar los resultados parciales en el TextView
        textViewDiceResult.append(resultText.toString());

        Log.d("DiceFragment", "rollSingleDiceType: " + diceTypeName + diceResultsList);
    }

    // Este método agrega una animación de rotación a la imagen del dado.
    private void animateDiceRoll() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation);
        imageViewDice.startAnimation(rotateAnimation);

        // Simular el tiempo que tardaría un lanzamiento de dado
        int diceRollDelay = 1500; // en milisegundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopDiceAnimation();
            }
        }, diceRollDelay);
    }

    // Este método detiene la animación de rotación del dado.
    private void stopDiceAnimation() {
        imageViewDice.clearAnimation();

        // Aquí puedes realizar acciones adicionales después de que la animación se haya detenido
        // Por ejemplo, actualizar la vista con los resultados finales.
    }
}