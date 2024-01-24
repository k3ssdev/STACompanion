package io.github.k3ssdev.stacompanion.ui.dice;

public class DiceResult {
    private int result;
    private int drawable;

    public DiceResult(int result, int drawable) {
        this.result = result;
        this.drawable = drawable;
    }

    public int getResult() {
        return result;
    }

    public int getDrawable() {
        return drawable;
    }

}
