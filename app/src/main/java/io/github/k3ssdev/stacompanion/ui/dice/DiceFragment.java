package io.github.k3ssdev.stacompanion.ui.dice;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.k3ssdev.stacompanion.R;


// Esta clase representa el fragmento de dados en la aplicación.
public class DiceFragment extends Fragment {

    // Variables
    private RecyclerView diceResultRecyclerView;
    private DiceResultAdapter diceResultAdapter;
    private final List<DiceResult> diceResultsList = new ArrayList<>();
    private EditText editTextNumberOfDiceD6;
    private EditText editTextNumberOfDiceD20;
    private Button buttonMinusD6;
    private Button buttonPlusD6;
    private Button buttonMinusD20;
    private Button buttonPlusD20;
    private ImageView buttonRollDice6;
    private ImageView buttonRollDice20;
    private TextView textViewDiceResult;
    private ImageView imageViewDice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Este método se llama cuando el fragmento se hace visible para el usuario.
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("");
    }

    // Este método se llama cuando se crea el fragmento.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dice_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Acciones de los elementos del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            resetView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Este método se llama para inflar el diseño del fragmento.
    private void resetView() {
        editTextNumberOfDiceD6.setText("0");
        editTextNumberOfDiceD20.setText("0");
        textViewDiceResult.setText("");
        diceResultsList.clear();
        diceResultAdapter.updateData(diceResultsList);
    }

    // Este método se llama para inflar el diseño del fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_dice, container, false);

        ImageView clearDiceD6 = view.findViewById(R.id.clearDiceD6);
        ImageView clearDiceD20 = view.findViewById(R.id.clearDiceD20);

        clearDiceD6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNumberOfDiceD6.setText("0");
            }
        });

        clearDiceD20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextNumberOfDiceD20.setText("0");
            }
        });

        diceResultRecyclerView = view.findViewById(R.id.diceResultRecyclerView);

        // Mejora el rendimiento
        diceResultRecyclerView.setHasFixedSize(true);

        // Usar grid layout manager
        int numberOfColumns = 4;
        diceResultRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        int spaceInPixels = 12;
        diceResultRecyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        // Especificar un adaptador
        diceResultAdapter = new DiceResultAdapter(diceResultsList);
        diceResultRecyclerView.setAdapter(diceResultAdapter);

        // Obtener referencias a los elementos de la interfaz de usuario
        editTextNumberOfDiceD6 = view.findViewById(R.id.editTextNumberOfDiceD6);
        editTextNumberOfDiceD20 = view.findViewById(R.id.editTextNumberOfDiceD20);
        buttonMinusD6 = view.findViewById(R.id.buttonMinusD6);
        buttonPlusD6 = view.findViewById(R.id.buttonPlusD6);
        buttonMinusD20 = view.findViewById(R.id.buttonMinusD20);
        buttonPlusD20 = view.findViewById(R.id.buttonPlusD20);
        buttonRollDice6 = view.findViewById(R.id.buttonRollDice6);
        buttonRollDice20 = view.findViewById(R.id.buttonRollDice20);
        textViewDiceResult = view.findViewById(R.id.textViewDiceResult);


        // Listener para los botones
        buttonRollDice6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice6();
            }
        });

        buttonRollDice20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice20();
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

    // Metodo para restar dados al pool
    private void decreaseNumberOfDice(EditText editText) {
        int currentValue = Integer.parseInt(editText.getText().toString());
        if (currentValue >= 1) {
            editText.setText(String.valueOf(currentValue - 1));
        }
    }

    // Metodo para sumar dados al pool
    private void increaseNumberOfDice(EditText editText) {
        int currentValue = Integer.parseInt(editText.getText().toString());
        editText.setText(String.valueOf(currentValue + 1));
    }

    // Metodo para lanzar dados de 6 caras
    private void rollDice6() {
        textViewDiceResult.setText("");
        int numberOfDiceD6 = getNumberOfDice(editTextNumberOfDiceD6);
        rollSingleDiceType(6, numberOfDiceD6, "d6");
        animateDiceRoll();
    }

    // Metodo para lanzar dados de 20 caras
    private void rollDice20() {
        textViewDiceResult.setText("");
        int numberOfDiceD20 = getNumberOfDice(editTextNumberOfDiceD20);
        rollSingleDiceType(20, numberOfDiceD20, "d20");
        animateDiceRoll();
    }


    // Este método devuelve el número de dados a lanzar.
    private int getNumberOfDice(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Este método lanza un dado de un tipo específico y muestra el resultado en la interfaz de usuario.
    private void rollSingleDiceType(int diceType, int numberOfDice, String diceTypeName) {
        // Crear un StringBuilder para almacenar el texto del resultado
        StringBuilder resultText = new StringBuilder("Resultados " +  "\n");

        Random random = new Random();

        // Limpiar la lista de resultados de dados
        diceResultsList.clear();

        // Lanzar el dado y evaluar el resultado
        for (int i = 0; i < numberOfDice; i++) {
            int diceResult = random.nextInt(diceType) + 1;
            int diceDrawable = 0;

            if (diceType == 6) {
                diceDrawable = getDice6Drawable(diceResult);
                switch (diceResult) {
                    case 1:
                        resultText.append("1");
                        break;
                    case 2:
                        resultText.append("2");
                        break;
                    case 3:
                    case 4:
                        resultText.append("0");
                        break;
                    case 5:
                    case 6:
                        resultText.append("1*");
                        break;
                    default:
                        resultText.append("0");
                        break;
                }
            } else if (diceType == 20) {
                diceDrawable = getDice20Drawable(diceResult);
                resultText.append(diceResult);
            }

            // Agregar el resultado a la lista de resultados de dados
            diceResultsList.add(new DiceResult(diceResult, diceDrawable));
            resultText.append(", ");
        }

        // Mostrar el resultado en la interfaz de usuario
        resultText.delete(resultText.length() - 2, resultText.length());
        textViewDiceResult.append(resultText.toString());

        // Actualizar RecyclerView
        diceResultAdapter.updateData(diceResultsList);

        Log.d("DiceFragment", "rollSingleDiceType: " + diceTypeName + diceResultsList);
    }

    // Este método agrega una animación de rotación a la imagen del dado de 6 caras.
    private void animateDiceRoll() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_animation);

        int diceRollDelay = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopDiceAnimation();
            }
        }, diceRollDelay);
    }

    // Este método sirve para obtener drawable del dado de 6 caras.
    private int getDice6Drawable(int diceResult) {
        switch (diceResult) {
            case 1:
                return R.drawable.dice6_success;
            case 2:
                return R.drawable.dice6_success_double;
            case 3:
            case 4:
                return R.drawable.dice6_fail;
            case 5:
            case 6:
                return R.drawable.dice6_special; // replace with your actual drawable resource for special effect
            default:
                return R.drawable.dice6_fail;
        }
    }

    // Este método sirve para obtener drawable del dado de 20 caras.
    private int getDice20Drawable(int diceResult) {
        switch (diceResult) {
            case 1:
                return R.drawable.dice20_01;
            case 2:
                return R.drawable.dice20_02;
            case 3:
                return R.drawable.dice20_03;
            case 4:
                return R.drawable.dice20_04;
            case 5:
                return R.drawable.dice20_05;
            case 6:
                return R.drawable.dice20_06;
            case 7:
                return R.drawable.dice20_07;
            case 8:
                return R.drawable.dice20_08;
            case 9:
                return R.drawable.dice20_09;
            case 10:
                return R.drawable.dice20_10;
            case 11:
                return R.drawable.dice20_11;
            case 12:
                return R.drawable.dice20_12;
            case 13:
                return R.drawable.dice20_13;
            case 14:
                return R.drawable.dice20_14;
            case 15:
                return R.drawable.dice20_15;
            case 16:
                return R.drawable.dice20_16;
            case 17:
                return R.drawable.dice20_17;
            case 18:
                return R.drawable.dice20_18;
            case 19:
                return R.drawable.dice20_19;
            case 20:
                return R.drawable.dice20_20;
            default:
                return R.drawable.dice20_01;
        }
    }

    // Interrumpir la animación de rotación
    private void stopDiceAnimation() {
        buttonRollDice6.clearAnimation();
        buttonRollDice20.clearAnimation();
    }
}