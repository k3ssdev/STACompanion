package io.github.k3ssdev.stacompanion.ui.dice;

// Esta clase representa el resultado de una tirada de dados.
public class DiceResult {
    private final int result;
    private final int drawable;

    // Constructor de la clase.
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
