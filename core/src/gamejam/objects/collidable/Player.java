package gamejam.objects.collidable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.KeyHoldWatcher;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.MousePressEvent;
import gamejam.event.events.PlayerDeathEvent;
import gamejam.event.events.PlayerMoveEvent;
import gamejam.objects.Damageable;
import gamejam.weapons.BasicWeapon;
import gamejam.weapons.Weapon;

/**
 * The player entity. Is NOT meant to hold the inventory etc!
 */
public class Player extends SelfCollidable implements Damageable {
    public static final float SPEED = 300f;

    private final KeyHoldWatcher keyHoldWatcher;
    private boolean lookingLeft = false;

    private Animation<TextureRegion> walkAnimation;
    private Texture spriteSheet;
    private TextureRegion currentSprite;
    private float animationTime = 0f;

    private final float maxHealth = 100;
    private float health = maxHealth;

    private Weapon weapon;

    private EventConsumer<CollisionEvent> collisionConsumer;

    private EventConsumer<MousePressEvent> mousePressConsumer;

    public Player(float x, float y) {
        super(65, 125, 50, 50);
        this.x = x;
        this.y = y;
        this.keyHoldWatcher = new KeyHoldWatcher();
        spriteSheet = new Texture("entity/Robot.png");

        collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);

        mousePressConsumer = this::onMousePress;
        EventQueue.getInstance().registerConsumer(mousePressConsumer, EventType.MOUSE_PRESS_EVENT);

        this.weapon = new BasicWeapon();

        TextureRegion[] walkFrames = TextureRegion.split(spriteSheet, 13, 25)[0];
        walkAnimation = new Animation<>(0.2f, walkFrames);
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

        super.setVelocity(SPEED * dx, SPEED * dy);

        /* publish new position to listeners */
        if (Math.abs(dx) > 0 || Math.abs(dy) > 0) {
            EventQueue.getInstance().invoke(new PlayerMoveEvent(x, y));
            animationTime += timeDeltaMillis * 0.001;
        }
        currentSprite = walkAnimation.getKeyFrame(animationTime,true);

        super.update(timeDeltaMillis);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        float xScale = lookingLeft ? -1f : 1f;

        spriteBatch.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth / 2, spriteHeight, spriteWidth, spriteHeight, xScale, 1f, 0f);
        super.drawHitBox(spriteBatch);
    }

    private void onCollisionEvent(CollisionEvent event) {
    }

    private void onMousePress(MousePressEvent event) {
        // TODO: Translate the screen coordinates of the mouse to world coordinates.
        float dx = event.getScreenX() - getX();
        float dy = (Gdx.graphics.getHeight() - event.getScreenY()) - getY();
        weapon.fire(this.x, this.y, dx, dy);
    }

    @Override
    public void damage(float damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
            EventQueue.getInstance().invoke(new PlayerDeathEvent());
        }
    }

    @Override
    public float getHealth() {
        return health;
    }

    @Override
    public float getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void onDispose() {
        super.onDispose();
        keyHoldWatcher.dispose();
        EventQueue.getInstance().deregisterConsumer(collisionConsumer, EventType.COLLISION_EVENT);
        EventQueue.getInstance().deregisterConsumer(mousePressConsumer, EventType.MOUSE_PRESS_EVENT);
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
