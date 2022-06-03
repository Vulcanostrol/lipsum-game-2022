package gamejam.objects;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.KeyHoldWatcher;

/**
 * The player entity. Is NOT meant to hold the inventory etc!
 */
public class Player extends Collidable {
    private final KeyHoldWatcher keyHoldWatcher;
    private boolean lookingLeft = false;
    private static final Texture TEXTURE = new Texture("Robot.png");

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.keyHoldWatcher = new KeyHoldWatcher();
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

        x += dx * timeDeltaMillis;
        y += dy * timeDeltaMillis;

    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(TEXTURE, x, y, 40, 60, 0, 0, 20, 30, lookingLeft, false);
    }
}
