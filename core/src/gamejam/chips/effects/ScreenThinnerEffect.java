package gamejam.chips.effects;

import com.badlogic.gdx.Gdx;
import gamejam.GameManager;

public class ScreenThinnerEffect implements ChipEffect {

    private final float multiplier;

    public ScreenThinnerEffect(float multiplier) {
        if (multiplier >= 1f) {
            Gdx.app.log("EVENTS", "WARNING: Thinner effect works only with values < 1f.");
        }
        this.multiplier = multiplier;
    }

    @Override
    public void apply() {
        float currentHeight = GameManager.getInstance().getCamera().getWidth();
        GameManager.getInstance().getCamera().scale(multiplier, 1f);
        float newHeight = GameManager.getInstance().getCamera().getWidth();
        float halfDelta = 0.5f * (currentHeight - newHeight);
        GameManager.getInstance().getCamera().addMovementOffset(halfDelta, 0);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public String description() {
        return "Makes your screen kinda THICC.";
    }
}
