package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.bullets.PyramidBullet;

import java.util.Random;

/**
 * An angry shooty boy
 */
public class PyramidEnemy extends AbstractEnemy {

    public static final float BULLET_SPEED = 500;
    public static final float MAX_FIRE_TIME = 2000.0f;

    public static final float ANGLE_ADJUST_SPEED =  0.0004f;

    public static final float BULLET_SPAWN_HEIGHT = 12.0f * 5;
    private static final float MAX_RANGE = 1200.0f;
    private static final float SPEED = 2.0f;

    public static final int SPRITE_WIDTH = 13 * 5;
    public static final int SPRITE_HEIGHT = 24 * 5;
    public static final int COLLISION_WIDTH = 11 * 5;
    public static final int COLLISION_HEIGHT = 9 * 5;

    private static Texture spriteSheet = null;
    private final Random random;
    private TextureRegion currentSprite;

    private  Animation<TextureRegion> animation;

    private float animationTime = 0f;

    private float angle = 0.0f;

    private float fireTimer = MAX_FIRE_TIME;

    private boolean lookingLeft = false;

    public PyramidEnemy(float initialX, float initialY) {
        super(initialX, initialY, SPRITE_WIDTH, SPRITE_HEIGHT, COLLISION_WIDTH, COLLISION_HEIGHT, 100);

        if (spriteSheet == null) {
            spriteSheet = new Texture("entity/pyramid.png");
        }

       random = new Random();

        angle = (float) (random.nextFloat() * 2 * Math.PI);
        fireTimer += random.nextFloat() * MAX_FIRE_TIME;
        animationTime += random.nextFloat();

        TextureRegion[] frames = TextureRegion.split(spriteSheet, 13, 24)[0];
        animation = new Animation<>(0.2f, frames);
    }

    @Override
    public void draw(Camera camera) {
        currentSprite = animation.getKeyFrame(animationTime,true);

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, lookingLeft, false);
        super.drawHitBox(camera);
    }

    @Override
    public void update(float timeDeltaMillis) {
        fireTimer -= timeDeltaMillis;

        if (canFire()) {
            fireAtPlayer();
        }

        angle += timeDeltaMillis * ANGLE_ADJUST_SPEED * (random.nextFloat() - 0.5f) * 2.0f;
        angle %= Math.PI * 2.0f;
        setVelocity((float) Math.cos(angle) * SPEED * timeDeltaMillis,
                (float) Math.sin(angle) * SPEED * timeDeltaMillis);

        animationTime += timeDeltaMillis * 0.001;
        super.update(timeDeltaMillis);
    }

    private void fireAtPlayer() {
        float playerX = PlayerFactory.getInstance().getPlayer().getX();
        float playerY = PlayerFactory.getInstance().getPlayer().getY();

        float dx = playerX - x;
        float dy = playerY - (y + BULLET_SPAWN_HEIGHT);

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance != 0 && distance < MAX_RANGE) {
            float vx = BULLET_SPEED * dx / distance;
            float vy = BULLET_SPEED * dy / distance;

            new PyramidBullet(x, y + BULLET_SPAWN_HEIGHT, vx, vy, 10.0f, 50.0f);
            fireTimer = MAX_FIRE_TIME;
        }
    }

    private boolean canFire() {
        return fireTimer <= 0;
    }

    @Override
    public void onCollisionEvent(CollisionEvent event) {
        super.onCollisionEvent(event);
        angle = (float) (random.nextFloat() * 2 * Math.PI);
    }
}
