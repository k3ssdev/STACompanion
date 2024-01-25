package io.github.k3ssdev.stacompanion.ui.dice;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

// Esta clase representa un decorador de elementos para RecyclerView.
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    // Este método se llama para obtener los desplazamientos de los elementos del RecyclerView.
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;

    }
}