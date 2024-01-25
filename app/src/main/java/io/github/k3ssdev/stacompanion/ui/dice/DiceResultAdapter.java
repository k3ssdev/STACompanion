package io.github.k3ssdev.stacompanion.ui.dice;


import android.os.Handler;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.k3ssdev.stacompanion.R;

public class DiceResultAdapter extends RecyclerView.Adapter<DiceResultAdapter.DiceResultViewHolder> {

    private List<DiceResult> diceResults;

    public DiceResultAdapter(List<DiceResult> diceResults) {
        this.diceResults = diceResults;
    }

    // Este método se llama cuando se crea un nuevo ViewHolder.
    @NonNull
    @Override
    public DiceResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new DiceResultViewHolder(imageView);
    }

    // Este método se llama cuando se enlaza un ViewHolder.
    @Override
    public void onBindViewHolder(@NonNull DiceResultViewHolder holder, int position) {
        DiceResult diceResult = diceResults.get(position);
        holder.imageView.setImageResource(diceResult.getDrawable());

        // Calcular el tamaño de la imagen
        int dpValue = 68; // replace with your value
        float d = holder.itemView.getContext().getResources().getDisplayMetrics().density;
        int sizeInPixels = (int)(dpValue * d); // size in pixels

        // Cambiar el tamaño de la imagen
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizeInPixels, sizeInPixels);
        holder.imageView.setLayoutParams(layoutParams);

        // Realizar animación de rotación
        Animation rotateAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.rotate_animation);
        holder.imageView.startAnimation(rotateAnimation);

        int diceRollDelay = 1500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.imageView.clearAnimation();
            }
        }, diceRollDelay);
    }

    @Override
    public int getItemCount() {
        return diceResults.size();
    }

    public class DiceResultViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public DiceResultViewHolder(@NonNull ImageView imageView) {
            super(imageView);
            this.imageView = imageView;
        }
    }

    private int getDiceDrawable(int diceResult) {
        // TODO: Implementar método
        return diceResult;
    }

    public void updateData(List<DiceResult> newDiceResults) {
        this.diceResults = newDiceResults;
        notifyDataSetChanged();
    }

}