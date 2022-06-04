package gamejam.chips.effects;

public interface ChipEffect {
    /**
     * Called once when the chip is first applied.
     */
    void apply();

    /**
     * Called every render/draw call.
     */
    void update();

    /**
     * Called when the ChipManager resets all chips and the effects should be disposed.
     */
    void dispose();

    /**
     * Returns the description of this effect.
     */
    String description();
}
