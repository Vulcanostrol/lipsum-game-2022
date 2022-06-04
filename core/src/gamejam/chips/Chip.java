package gamejam.chips;

import gamejam.chips.effects.ChipEffect;

import java.util.ArrayList;
import java.util.List;

public abstract class Chip {

    private final String chipName;
    private final List<ChipEffect> chipEffects;

    public Chip(String chipName) {
        this.chipName = chipName;
        chipEffects = new ArrayList<>();
    }

    protected void addEffect(ChipEffect effect) {
        chipEffects.add(effect);
    }

    /**
     * Called once when the chip is first applied.
     */
    public void applyEffects() {
        for (ChipEffect effect : chipEffects) {
            effect.apply();
        }
    }

    /**
     * Called every render/draw call.
     */
    public void updateEffects() {
        for (ChipEffect effect : chipEffects) {
            effect.update();
        }
    }

    /**
     * Called when the ChipManager resets all chips and the effects should be disposed.
     */
    public void disposeEffects() {
        for (ChipEffect effect : chipEffects) {
            effect.dispose();
        }
    }

    public String getChipName() {
        return chipName;
    }

    public List<ChipEffect> getChipEffects() {
        return chipEffects;
    }
}
