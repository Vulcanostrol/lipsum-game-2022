package gamejam.chips.effects;

import gamejam.Camera;
import gamejam.GameManager;
import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;

public class CameraDvdEffect implements ChipEffect {

    public static final int START_SPEED = 50;
    private final float multiplier;

    public CameraDvdEffect(float multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply() {
        Camera camera = GameManager.getInstance().getCamera();
        if (camera.getMovementSpeed() == 0) {
            camera.setMovementSpeed(START_SPEED);
        } else {
            camera.multiplyMovementSpeed(multiplier);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
        GameManager.getInstance().getCamera().setMovementSpeed(0);
        GameManager.getInstance().getCamera().resetMovementOffset();
    }

    @Override
    public String description() {
        return "Makes the screen a big DVD.";
    }
}
