package gamejam.chips.effects;

import gamejam.GameManager;

public class FlipScreenEffect implements ChipEffect {

    public FlipScreenEffect() {
    }

    @Override
    public void apply() {
        GameManager.getInstance().getCamera().inverseFlipX();
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
        GameManager.getInstance().getCamera().resetFlips();
    }

    @Override
    public String description() {
        return "Flips the screen.";
    }
}
