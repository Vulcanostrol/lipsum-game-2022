package gamejam.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import gamejam.KeyHoldWatcher;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.*;
import gamejam.factories.BulletFactory;
import gamejam.weapons.BasicWeapon;
import gamejam.weapons.Weapon;

/**
 * The player entity. Is NOT meant to hold the inventory etc!
 */
public class Player extends SelfCollidable implements Damageable {
    public static final float SPEED = 300f;

    private final KeyHoldWatcher keyHoldWatcher;
    private boolean lookingLeft = false;
    private final Texture texture;

    private final float maxHealth = 100;
    private float health = maxHealth;

    private Weapon weapon;

    private EventConsumer<CollisionEvent> collisionConsumer;

    private EventConsumer<MousePressEvent> mousePressConsumer;

    public Player(float x, float y) {
        super(40, 60, 25, 25);
        this.x = x;
        this.y = y;
        this.keyHoldWatcher = new KeyHoldWatcher();
        texture = new Texture("Robot.png");

        collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);

        mousePressConsumer = this::onMousePress;
        EventQueue.getInstance().registerConsumer(mousePressConsumer, EventType.MOUSE_PRESS_EVENT);

        this.weapon = new BasicWeapon();
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

        super.setVelocity(SPEED*dx, SPEED*dy);

        super.update(timeDeltaMillis);
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x - spriteWidth / 2, y, spriteWidth, spriteHeight, 0, 0, 20, 30, lookingLeft, false);
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
