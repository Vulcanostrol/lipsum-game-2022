package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;
import gamejam.factories.PlayerFactory;
import gamejam.objects.Bomb;

/**
 * Bomby boi shoots bombs and teleports all over the map just to annoy you
 */
public class CannonEnemy extends AbstractEnemy {
    public static final float FIRE_RATE = 3000;

    private float fireTimer;

    public CannonEnemy(float initialX, float initialY) {
        super(initialX, initialY, 20*5, 15*5, 18*5, 13*5, 30);
        this.sprite = new Texture("entity/cannon.png");
        fireTimer = FIRE_RATE;
    }

    @Override
    public void update(float timeDeltaMillis) {
        fireTimer -= timeDeltaMillis;
        if (fireTimer <= 0) {
            new Bomb(x, y,
                    PlayerFactory.getInstance().getPlayer().getX(),
                    PlayerFactory.getInstance().getPlayer().getY());
            fireTimer = FIRE_RATE;
        }
        super.update(timeDeltaMillis);
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera);
        camera.draw(sprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight);
    }
}
