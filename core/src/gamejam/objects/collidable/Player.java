package gamejam.objects.collidable;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.KeyHoldWatcher;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.*;
import gamejam.factories.BulletFactory;
import gamejam.objects.Damageable;
import gamejam.objects.collidable.Bullet;
import gamejam.objects.collidable.SelfCollidable;

/**
 * The player entity. Is NOT meant to hold the inventory etc!
 */
public class Player extends SelfCollidable implements Damageable {
    public static final float SPEED = 0.3f;

    private final KeyHoldWatcher keyHoldWatcher;
    private boolean lookingLeft = false;
    private final Texture texture;

    private final float maxHealth = 100;
    private float health = maxHealth;

    public Player(float x, float y) {
        super(40, 60, 25, 25);
        this.x = x;
        this.y = y;
        this.keyHoldWatcher = new KeyHoldWatcher();
        texture = new Texture("entity/Robot.png");

        EventConsumer<CollisionEvent> collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);

        EventConsumer<KeyEvent> keyEventConsumer = this::onKeyEvent;
        EventQueue.getInstance().registerConsumer(keyEventConsumer, EventType.KEY_EVENT);

        EventConsumer<MousePressEvent> mousePressConsumer = this::onMousePress;
        EventQueue.getInstance().registerConsumer(mousePressConsumer, EventType.MOUSE_PRESS_EVENT);

        EventConsumer<MouseReleaseEvent> mouseReleaseConsumer = this::onMouseRelease;
        EventQueue.getInstance().registerConsumer(mouseReleaseConsumer, EventType.MOUSE_RELEASE_EVENT);

        EventConsumer<MouseMoveEvent> mouseMoveConsumer = this::onMouseMove;
        EventQueue.getInstance().registerConsumer(mouseMoveConsumer, EventType.MOUSE_MOVE_EVENT);
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

        /* publish new position to listeners */
        if (dx > 0 || dy > 0) {
            EventQueue.getInstance().invoke(new PlayerMoveEvent(x, y));
        }

        super.resetCollision();
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, x - spriteWidth / 2, y, spriteWidth, spriteHeight, 0, 0, 20, 30, lookingLeft, false);
        super.drawHitBox(spriteBatch);
    }

    private void onCollisionEvent(CollisionEvent event) {
    }

    private void onKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == Input.Keys.SPACE && event.isKeyDown()) {
            BulletFactory.getInstance().addManagedObject(new Bullet(this.x, this.y, 0, 1000));
        }
    }

    private void onMousePress(MousePressEvent event) {
        System.out.println(event);
    }

    private void onMouseRelease(MouseReleaseEvent event) {
        System.out.println(event);
    }

    private void onMouseMove(MouseMoveEvent event) {
//        System.out.println(event);
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
}