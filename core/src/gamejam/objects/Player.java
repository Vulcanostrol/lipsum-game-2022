package gamejam.objects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.KeyHoldWatcher;

/**
 * The player entity. Is NOT meant to hold the inventory etc!
 */
public class Player extends Collidable {
    public static final float SPEED = 0.3f;
    private final KeyHoldWatcher keyHoldWatcher;
    private boolean lookingLeft = false;
    private final Texture texture;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.keyHoldWatcher = new KeyHoldWatcher();
        texture = new Texture("Robot.png");
    }

    @Override
    public void update(float timeDeltaMillis) {
        float dx = 0;
        float dy = 0;
        if (keyHoldWatcher.isKeyHeld(Input.Keys.D) || keyHoldWatcher.isKeyHeld(Input.Keys.RIGHT)) {
            dx += 1;
            lookingLeft = false;
        }
        if (keyHoldWatcher.isKeyHeld(Input.Keys.A) || keyHoldWatcher.isKeyHeld(Input.Keys.LEFT)) {
            dx -= 1;
            lookingLeft = true;
        }
        if (keyHoldWatcher.isKeyHeld(Input.Keys.W) || keyHoldWatcher.isKeyHeld(Input.Keys.UP)) {
            dy += 1;
        }
        if (keyHoldWatcher.isKeyHeld(Input.Keys.S) || keyHoldWatcher.isKeyHeld(Input.Keys.DOWN)) {
            dy -= 1;
        }

        x += SPEED * dx * timeDeltaMillis;
        y += SPEED * dy * timeDeltaMillis;

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x, y, 40, 60, 0, 0, 20, 30, lookingLeft, false);
    }
}
