package gamejam.chips.effects;

import gamejam.GameManager;

public class ScreenShakeEffect implements ChipEffect {

    private final float multiplier;

    public ScreenShakeEffect(float multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply() {
        GameManager.getInstance().getCamera().multiplyShake(multiplier);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public String description() {
        return "Shakes the screen.";
    }
}
