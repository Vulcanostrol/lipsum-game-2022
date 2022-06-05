package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.event.events.CollisionEvent;
import gamejam.objects.collidable.bullets.PyramidBullet;

import java.util.ArrayList;
import java.util.Random;

/**
 * An angry golden shooty boy
 */
public class HexEnemy extends AbstractEnemy {

    public static final float BULLET_SPEED = 550;
    public static final float FRENZY_MAX_COOLDOWN = 10000.0f;
    public static final float FRENZY_DURATION = 2500f;
    public static final float FRENZY_FIRE_INTERVAL = 250f;
    private float currentFireCooldown = 0f;
    private float currentFrenzyTime = 0;
    public static final float MAX_HEALTH = 500;
    public static final float DAMAGE = 30f;
    public static final float BULLET_SIZE = 65f;

    public static final float ANGLE_ADJUST_SPEED =  0.0004f;
    public static final float ANGLE_ADJUST_CHANCE = 0.01f;
    public static final float FRENZY_MOVE_SPEED =  40f;
    public static final float IDLE_MOVE_SPEED =  4f;

    public static final float BULLET_SPAWN_HEIGHT = 12.0f * 5;
    private static final float MAX_RANGE = 2200.0f;
    private static final float FRENZY_SPEED = 50.0f;

    public static final int SPRITE_WIDTH = 13 * 7;
    public static final int SPRITE_HEIGHT = 24 * 7;
    public static final int COLLISION_WIDTH = 11 * 7;
    public static final int COLLISION_HEIGHT = 9 * 7;

    private static Texture idleSpriteSheet = null;
    private static Texture frenzySpriteSheet = null;
    private final Random random;
    private TextureRegion currentSprite;

    private  Animation<TextureRegion> idleAnimation;
    private  Animation<TextureRegion> frenzyAnimation;
    private float IDLE_FRAME_DURATION = 0.2f;
    private float FRENZY_FRAME_DURATION = 0.05f;

    private boolean isIdle = true;
    private boolean isFrenzied = false;

    private float animationTime = 0f;

    private float angle = 0.0f;

    private float frenzyTimer = FRENZY_MAX_COOLDOWN;

    private boolean lookingLeft = false;

    public HexEnemy(float initialX, float initialY) {
        super(initialX, initialY, SPRITE_WIDTH, SPRITE_HEIGHT, COLLISION_WIDTH, COLLISION_HEIGHT, MAX_HEALTH);

        if (idleSpriteSheet == null) {
            idleSpriteSheet = new Texture("entity/hex_idle.png");
        }
        if (frenzySpriteSheet == null) {
            frenzySpriteSheet = new Texture("entity/hex_frenzy.png");
        }

        random = new Random();

        angle = (float) (random.nextFloat() * 2 * Math.PI);
        frenzyTimer += random.nextFloat() * FRENZY_MAX_COOLDOWN;
        animationTime += random.nextFloat();

        TextureRegion[] idleFrames = TextureRegion.split(idleSpriteSheet, 13, 24)[0];
        idleAnimation = new Animation<>(IDLE_FRAME_DURATION, idleFrames);

        TextureRegion[] frenzyFrames = TextureRegion.split(frenzySpriteSheet, 13, 24)[0];
        frenzyAnimation = new Animation<>(FRENZY_FRAME_DURATION, frenzyFrames);
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera); // draw health bar (abstract enemy)
        if (isIdle) {
            currentSprite = idleAnimation.getKeyFrame(animationTime, true);
        }
        else if (isFrenzied) {
            currentSprite = frenzyAnimation.getKeyFrame(animationTime, true);
        }
        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, lookingLeft, false);
        super.drawHitBox(camera);
    }

    @Override
    public void update(float timeDeltaMillis) {
        frenzyTimer -= timeDeltaMillis;
        if (canFrenzy()) {
            startFrenzy();
        }

        if (isIdle) {
            angle += timeDeltaMillis * ANGLE_ADJUST_SPEED * (random.nextFloat() - 0.5f) * 2.0f;
            angle %= Math.PI * 2.0f;
            setVelocity((float) Math.cos(angle) * IDLE_MOVE_SPEED * timeDeltaMillis,
                    (float) Math.sin(angle) * IDLE_MOVE_SPEED * timeDeltaMillis);
        } else if (isFrenzied) {
            currentFrenzyTime -= timeDeltaMillis;
            currentFireCooldown -= timeDeltaMillis;

            if (currentFireCooldown <= 0) {
                fire();
            }

            if (random.nextFloat() < ANGLE_ADJUST_CHANCE) {
                angle += timeDeltaMillis * ANGLE_ADJUST_SPEED * (random.nextFloat() - 0.5f) * 2.0f;
                angle %= Math.PI * 2.0f;
            }
            setVelocity((float) Math.cos(angle) * FRENZY_MOVE_SPEED * timeDeltaMillis,
                    (float) Math.sin(angle) * FRENZY_MOVE_SPEED * timeDeltaMillis);
            if (currentFrenzyTime <= 0) {
                endFrenzy();
            }
        }


        animationTime += timeDeltaMillis * 0.001;
        super.update(timeDeltaMillis);
    }

    private void fire() {
        currentFireCooldown = FRENZY_FIRE_INTERVAL;
        angle = (float) (random.nextFloat() * 2 * Math.PI);

        new PyramidBullet(x, y + BULLET_SPAWN_HEIGHT, (float) Math.cos(angle) * BULLET_SPEED, (float) Math.sin(angle) * BULLET_SPEED, DAMAGE, BULLET_SIZE);
    }

    private void endFrenzy() {
        isFrenzied = false;
        isIdle = true;
        currentFrenzyTime = FRENZY_MAX_COOLDOWN;
    }

    private void startFrenzy() {
        currentFrenzyTime = FRENZY_DURATION;
        isFrenzied = true;
        isIdle = false;
        frenzyTimer = FRENZY_MAX_COOLDOWN;
    }

    private boolean canFrenzy() {
        return frenzyTimer <= 0;
    }

    @Override
    public void onCollisionEvent(CollisionEvent event) {
        super.onCollisionEvent(event);
        angle = (float) (random.nextFloat() * 2 * Math.PI);
    }
}
