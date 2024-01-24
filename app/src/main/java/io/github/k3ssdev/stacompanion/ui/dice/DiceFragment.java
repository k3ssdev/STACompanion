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


public class DiceFragment extends Fragment {

    private RecyclerView diceResultRecyclerView;
    private DiceResultAdapter diceResultAdapter;
    private List<DiceResult> diceResultsList = new ArrayList<>();
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

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().setTitle("");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dice_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            resetView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetView() {
        editTextNumberOfDiceD6.setText("0");
        editTextNumberOfDiceD20.setText("0");
        textViewDiceResult.setText("");
        diceResultsList.clear();
        diceResultAdapter.updateData(diceResultsList);
    }
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

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        diceResultRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        //diceResultRecyclerView.setLayoutManager(layoutManager);
        //diceResultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        int numberOfColumns = 4; // replace with your desired number of columns
        diceResultRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));

        int spaceInPixels = 12; // replace with your desired space in pixels
        diceResultRecyclerView.addItemDecoration(new SpaceItemDecoration(spaceInPixels));

        // Specify an adapter
        diceResultAdapter = new DiceResultAdapter(diceResultsList);
        diceResultRecyclerView.setAdapter(diceResultAdapter);

        editTextNumberOfDiceD6 = view.findViewById(R.id.editTextNumberOfDiceD6);
        editTextNumberOfDiceD20 = view.findViewById(R.id.editTextNumberOfDiceD20);
        buttonMinusD6 = view.findViewById(R.id.buttonMinusD6);
        buttonPlusD6 = view.findViewById(R.id.buttonPlusD6);
        buttonMinusD20 = view.findViewById(R.id.buttonMinusD20);
        buttonPlusD20 = view.findViewById(R.id.buttonPlusD20);
        buttonRollDice6 = view.findViewById(R.id.buttonRollDice6);
        buttonRollDice20 = view.findViewById(R.id.buttonRollDice20);
        textViewDiceResult = view.findViewById(R.id.textViewDiceResult);


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

    private void decreaseNumberOfDice(EditText editText) {
        int currentValue = Integer.parseInt(editText.getText().toString());
        if (currentValue >= 1) {
            editText.setText(String.valueOf(currentValue - 1));
        }
    }

    private void increaseNumberOfDice(EditText editText) {
        int currentValue = Integer.parseInt(editText.getText().toString());
        editText.setText(String.valueOf(currentValue + 1));
    }

    private void rollDice6() {
        textViewDiceResult.setText("");
        int numberOfDiceD6 = getNumberOfDice(editTextNumberOfDiceD6);
        rollSingleDiceType(6, numberOfDiceD6, "d6");
        animateDiceRoll();
    }

    private void rollDice20() {
        textViewDiceResult.setText("");
        int numberOfDiceD20 = getNumberOfDice(editTextNumberOfDiceD20);
        rollSingleDiceType(20, numberOfDiceD20, "d20");
        animateDiceRoll();
    }

    private int getNumberOfDice(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void rollSingleDiceType(int diceType, int numberOfDice, String diceTypeName) {
        //StringBuilder resultText = new StringBuilder("Resultados " + diceTypeName + ": \n");

        StringBuilder resultText = new StringBuilder("Resultados " +  "\n");

        Random random = new Random();

        // Clear previous dice results
        diceResultsList.clear();

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
                resultText.append(String.valueOf(diceResult));
            }

            diceResultsList.add(new DiceResult(diceResult, diceDrawable));
            resultText.append(", ");
        }

        resultText.delete(resultText.length() - 2, resultText.length());
        textViewDiceResult.append(resultText.toString());

        // Update the RecyclerView
        diceResultAdapter.updateData(diceResultsList);

        Log.d("DiceFragment", "rollSingleDiceType: " + diceTypeName + diceResultsList);
    }

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

    // Este método agrega una animación de rotación a la imagen del dado de 6 caras.
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

    // Este método agrega una animación de rotación a la imagen del dado de 20 caras.
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

    private void stopDiceAnimation() {
        buttonRollDice6.clearAnimation();
        buttonRollDice20.clearAnimation();
    }
}