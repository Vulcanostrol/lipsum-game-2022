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
 * An angry golden shooty boy
 */
public class GoldenPyramidEnemy extends AbstractEnemy {

    public static final float BULLET_SPEED = 550;
    public static final float MAX_FIRE_TIME = 1750.0f;
    public static final float MAX_HEALTH = 500;
    public static final float DAMAGE = 30f;
    public static final float BULLET_SIZE = 65f;

    public static final float ANGLE_ADJUST_SPEED =  0.0004f;

    public static final float BULLET_SPAWN_HEIGHT = 12.0f * 5;
    private static final float MAX_RANGE = 2200.0f;
    private static final float SPEED = 1.0f;

    public static final int SPRITE_WIDTH = 13 * 7;
    public static final int SPRITE_HEIGHT = 24 * 7;
    public static final int COLLISION_WIDTH = 11 * 7;
    public static final int COLLISION_HEIGHT = 9 * 7;

    private static Texture spriteSheet = null;
    private final Random random;
    private TextureRegion currentSprite;

    private  Animation<TextureRegion> animation;

    private float animationTime = 0f;

    private float angle = 0.0f;

    private float fireTimer = MAX_FIRE_TIME;

    private boolean lookingLeft = false;

    public GoldenPyramidEnemy(float initialX, float initialY) {
        super(initialX, initialY, SPRITE_WIDTH, SPRITE_HEIGHT, COLLISION_WIDTH, COLLISION_HEIGHT, MAX_HEALTH);

        if (spriteSheet == null) {
            spriteSheet = new Texture("entity/golden_pyramid.png");
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
        super.draw(camera); // draw health bar (abstract enemy)
        currentSprite = animation.getKeyFrame(animationTime,true);

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, lookingLeft, false);
        super.drawHitBox(camera);
    }

    @Override
    public void update(float timeDeltaMillis) {
        fireTimer -= timeDeltaMillis;

        if (canFire()) {
            fire();
        }

        angle += timeDeltaMillis * ANGLE_ADJUST_SPEED * (random.nextFloat() - 0.5f) * 2.0f;
        angle %= Math.PI * 2.0f;
        setVelocity((float) Math.cos(angle) * SPEED * timeDeltaMillis,
                (float) Math.sin(angle) * SPEED * timeDeltaMillis);

        animationTime += timeDeltaMillis * 0.001;
        super.update(timeDeltaMillis);
    }

    private void fire() {
        new PyramidBullet(x, y + BULLET_SPAWN_HEIGHT, BULLET_SPEED, BULLET_SPEED, DAMAGE, BULLET_SIZE);
        new PyramidBullet(x, y + BULLET_SPAWN_HEIGHT, -BULLET_SPEED, BULLET_SPEED, DAMAGE, BULLET_SIZE);
        new PyramidBullet(x, y + BULLET_SPAWN_HEIGHT, BULLET_SPEED, -BULLET_SPEED, DAMAGE, BULLET_SIZE);
        new PyramidBullet(x, y + BULLET_SPAWN_HEIGHT, -BULLET_SPEED, -BULLET_SPEED, DAMAGE, BULLET_SIZE);
        fireTimer = MAX_FIRE_TIME;
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
