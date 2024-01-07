package io.github.k3ssdev.stacompanion.ui.dice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Random;

import io.github.k3ssdev.stacompanion.R;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
//import io.github.k3ssdev.myapplication.databinding.FragmentDashboardBinding;

public class DiceFragment extends Fragment {

    private EditText editTextNumberOfDiceD6;
    private EditText editTextNumberOfDiceD20;
    private Button buttonMinusD6;
    private Button buttonPlusD6;
    private Button buttonMinusD20;
    private Button buttonPlusD20;
    private Button buttonRollDice;
    private TextView textViewDiceResult;
    private ImageView imageViewDice;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dice, container, false);

        editTextNumberOfDiceD6 = view.findViewById(R.id.editTextNumberOfDiceD6);
        editTextNumberOfDiceD20 = view.findViewById(R.id.editTextNumberOfDiceD20);
        buttonMinusD6 = view.findViewById(R.id.buttonMinusD6);
        buttonPlusD6 = view.findViewById(R.id.buttonPlusD6);
        buttonMinusD20 = view.findViewById(R.id.buttonMinusD20);
        buttonPlusD20 = view.findViewById(R.id.buttonPlusD20);
        buttonRollDice = view.findViewById(R.id.buttonRollDice);
        textViewDiceResult = view.findViewById(R.id.textViewDiceResult);
        imageViewDice = view.findViewById(R.id.imageViewDice);

        buttonRollDice.setOnClickListener(new View.OnClickListener() {
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

    private void decreaseNumberOfDice(EditText editText) {
        int currentValue = Integer.parseInt(editText.getText().toString());
        if (currentValue > 1) {
            editText.setText(String.valueOf(currentValue - 1));
        }
    }

    private void increaseNumberOfDice(EditText editText) {
        int currentValue = Integer.parseInt(editText.getText().toString());
        editText.setText(String.valueOf(currentValue + 1));
    }

    private void rollDice() {
        // Obtener la cantidad de dados a lanzar para d6
        int numberOfDiceD6 = Integer.parseInt(editTextNumberOfDiceD6.getText().toString());
        int totalResultD6 = rollDiceOfType(6, numberOfDiceD6);

        // Obtener la cantidad de dados a lanzar para d20
        int numberOfDiceD20 = Integer.parseInt(editTextNumberOfDiceD20.getText().toString());
        int totalResultD20 = rollDiceOfType(20, numberOfDiceD20);

        // Mostrar los resultados en el TextView
        StringBuilder resultText = new StringBuilder("Resultados d6: " + totalResultD6 + "\n");
        resultText.append("Resultados d20: " + totalResultD20);

        textViewDiceResult.setText(resultText.toString());

        // Agregar animación de lanzamiento de dado
        animateDiceRoll();
    }

    private int rollDiceOfType(int diceType, int numberOfDice) {
        int totalResult = 0;
        StringBuilder resultText = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < numberOfDice; i++) {
            int diceResult = (diceType == 6) ? rollD6() : random.nextInt(diceType) + 1;
            totalResult += diceResult;
            resultText.append(diceResult).append(", ");
        }

        resultText.append("\n");

        // Mostrar los resultados parciales en el TextView
        textViewDiceResult.append(resultText.toString());

        return totalResult;
    }

    private int rollD6() {
        // Lanzamiento de un dado d6 especial
        int diceResult = new Random().nextInt(6) + 1;
        return (diceResult >= 3 && diceResult <= 4) ? 0 : 1;
    }

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

    private void stopDiceAnimation() {
        imageViewDice.clearAnimation();

        // Aquí puedes realizar acciones adicionales después de que la animación se haya detenido
        // Por ejemplo, actualizar la vista con los resultados finales.
    }
}
