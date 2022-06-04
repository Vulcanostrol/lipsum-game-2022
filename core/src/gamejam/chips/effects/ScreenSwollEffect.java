package gamejam.chips.effects;

import com.badlogic.gdx.Gdx;
import gamejam.GameManager;

public class ScreenSwollEffect implements ChipEffect {

    private final float multiplier;

    public ScreenSwollEffect(float multiplier) {
        if (multiplier >= 1f) {
            Gdx.app.log("EVENTS", "WARNING: Swoll effect works only with values < 1f.");
        }
        this.multiplier = multiplier;
    }

    @Override
    public void apply() {
        float currentHeight = GameManager.getInstance().getCamera().getHeight();
        GameManager.getInstance().getCamera().scale(1f, multiplier);
        float newHeight = GameManager.getInstance().getCamera().getHeight();
        float halfDelta = 0.5f * (currentHeight - newHeight);
        GameManager.getInstance().getCamera().addMovementOffset(0, halfDelta);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
        GameManager.getInstance().getCamera().setScaleY(1f);
        GameManager.getInstance().getCamera().resetMovementOffset();
    }

    @Override
    public String description() {
        return "Makes your screen kinda THICC.";
    }
}
