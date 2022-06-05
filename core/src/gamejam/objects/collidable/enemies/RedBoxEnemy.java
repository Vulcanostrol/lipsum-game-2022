package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.config.ScoreConfiguration;
import gamejam.event.events.CollisionEvent;
import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.bullets.PyramidBullet;

import java.util.Random;

/**
 * An angry golden shooty boy
 */
public class RedBoxEnemy extends AbstractEnemy {

    public static final float BULLET_SPEED = 1500;
    public static final float MAX_FIRE_TIME = 3000.0f;
    public static final float MAX_HEALTH = 500;
    public static final float DAMAGE = 120f;
    public static final float BULLET_SIZE = 25f;
    public static final float ANGLE_ADJUST_SPEED =  0.0004f;
    public static final float BULLET_SPAWN_HEIGHT = 12.0f * 5;
    private static final float MAX_RANGE = 3200.0f;
    private static final float SPEED = 8.0f;

    private int nShotsRemaining = 0;
    private float currentShotDelay = 0;
    private static final int N_SHOTS = 6;
    private static final float SHOT_DELAY = 150f;
    private boolean isShooting = false;

    private static final int TILE_WIDTH = 14;
    private static final int TILE_HEIGHT = 24;

    public static final int SPRITE_WIDTH = TILE_WIDTH * 5;
    public static final int SPRITE_HEIGHT = 24 * 5;
    public static final int COLLISION_WIDTH = 11 * 5;
    public static final int COLLISION_HEIGHT = 9 * 5;

    private static Texture baseSpriteSheet = null;
    private static Texture chargingSpriteSheet = null;
    private static Texture shootingSpriteSheet = null;
    private final Random random;
    private TextureRegion currentSprite;

    private static final float FRAME_DURATION = 0.1f;
    private  Animation<TextureRegion> baseAnimation;
    private  Animation<TextureRegion> chargingAnimation;
    private  Animation<TextureRegion> shootingAnimation;

    private float angle = 0.0f;
    private boolean charging;

    private float animationTime = 0f;

    private float fireTimer = MAX_FIRE_TIME;
    private static final float CHARGE_RATIO = 0.3f;

    private boolean lookingLeft = false;

    @Override
    public int getPoints(){
        return ScoreConfiguration.RED_BOX;
    }

    public RedBoxEnemy(float initialX, float initialY) {
        super(initialX, initialY, SPRITE_WIDTH, SPRITE_HEIGHT, COLLISION_WIDTH, COLLISION_HEIGHT, MAX_HEALTH);

        if (baseSpriteSheet == null) {
            baseSpriteSheet = new Texture("entity/red_box.png");
        }
        if (chargingSpriteSheet == null) {
            chargingSpriteSheet = new Texture("entity/red_box_charging.png");
        }
        if (shootingSpriteSheet == null) {
            shootingSpriteSheet = new Texture("entity/red_box_shooting.png");
        }

        charging = false;
        random = new Random();

        angle = (float) (random.nextFloat() * 2 * Math.PI);
        fireTimer += random.nextFloat() * MAX_FIRE_TIME;
        animationTime += random.nextFloat();

        TextureRegion[] baseFrames = TextureRegion.split(baseSpriteSheet, TILE_WIDTH, TILE_HEIGHT)[0];
        baseAnimation = new Animation<>(FRAME_DURATION, baseFrames);

        TextureRegion[] chargingFrames = TextureRegion.split(chargingSpriteSheet, TILE_WIDTH, TILE_HEIGHT)[0];
        chargingAnimation = new Animation<>(FRAME_DURATION, chargingFrames);

        TextureRegion[] shootingFrames = TextureRegion.split(shootingSpriteSheet, TILE_WIDTH, TILE_HEIGHT)[0];
        shootingAnimation = new Animation<>(FRAME_DURATION, shootingFrames);
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera); // draw health bar (abstract enemy)
        if (charging) {
            currentSprite = chargingAnimation.getKeyFrame(animationTime, true);
        } else if (isShooting) {
            currentSprite = shootingAnimation.getKeyFrame(animationTime, true);
        } else {
            currentSprite = baseAnimation.getKeyFrame(animationTime, true);
        }

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, lookingLeft, false);
        super.drawHitBox(camera);
    }

    @Override
    public void update(float timeDeltaMillis) {
        if (fireTimer / MAX_FIRE_TIME < CHARGE_RATIO) {
            charging = true;
        } else {
            charging = false;
        }
        fireTimer -= timeDeltaMillis;

        if (isShooting) {
            currentShotDelay -= timeDeltaMillis;
            if (currentShotDelay <= 0) {
                nShotsRemaining -= 1;
                currentShotDelay = SHOT_DELAY;
                fireAtPlayer();
            }
            if (nShotsRemaining <= 0) {
                isShooting = false;
            }
        }

        if (canFire()) {
            isShooting = true;
            nShotsRemaining = N_SHOTS;
            currentShotDelay = 0;
            fireTimer = MAX_FIRE_TIME;
        }

        angle += timeDeltaMillis * ANGLE_ADJUST_SPEED * (random.nextFloat() - 0.5f) * 2.0f;
        angle %= Math.PI * 2.0f;
        setVelocity((float) Math.cos(angle) * SPEED * timeDeltaMillis,
                (float) Math.sin(angle) * SPEED * timeDeltaMillis);

        animationTime += timeDeltaMillis * 0.001;
        super.update(timeDeltaMillis);

//        fireAtPlayer();
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

            new PyramidBullet(x, y + BULLET_SPAWN_HEIGHT, vx, vy, DAMAGE, BULLET_SIZE);
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
