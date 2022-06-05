package gamejam.objects.collidable;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.GameManager;
import gamejam.KeyHoldWatcher;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.*;
import gamejam.objects.Damageable;
import gamejam.weapons.BasicWeapon;
import gamejam.weapons.Weapon;


/**
 * The player entity. Is NOT meant to hold the inventory etc!
 */
public class Player extends SelfCollidable implements Damageable, Traversable {

    public static final float BASE_SPEED = 300f;

    private float speed;

    private final KeyHoldWatcher keyHoldWatcher;
    private boolean lookingLeft = false;

    private Animation<TextureRegion> walkAnimation;
    private Texture spriteSheet;
    private TextureRegion currentSprite;
    private float animationTime = 0f;

    private float maxHealth = 100;
    private float health = maxHealth;

    private Weapon weapon;
    private boolean isFiringWeapon;
    private MouseEvent lastMouseEvent;

    private EventConsumer<MousePressEvent> mousePressConsumer;
    private EventConsumer<MouseReleaseEvent> mouseReleaseConsumer;
    private EventConsumer<MouseMoveEvent> mouseMoveConsumer;

    public Player(float x, float y) {
        super(65, 125, 50, 50);
        this.x = x;
        this.y = y;
        this.keyHoldWatcher = new KeyHoldWatcher();
        spriteSheet = new Texture("entity/Robot.png");
        this.speed = BASE_SPEED;

        mousePressConsumer = this::onMousePress;
        EventQueue.getInstance().registerConsumer(mousePressConsumer, EventType.MOUSE_PRESS_EVENT);

        mouseReleaseConsumer = this::onMouseRelease;
        EventQueue.getInstance().registerConsumer(mouseReleaseConsumer, EventType.MOUSE_RELEASE_EVENT);

        mouseMoveConsumer = this::onMouseMove;
        EventQueue.getInstance().registerConsumer(mouseMoveConsumer, EventType.MOUSE_MOVE_EVENT);


        this.weapon = new BasicWeapon();

        TextureRegion[] walkFrames = TextureRegion.split(spriteSheet, 13, 25)[0];
        walkAnimation = new Animation<>(0.2f, walkFrames);
    }


    @Override
    public void update(float timeDeltaMillis) {
        ///////////////////
        // Walking stuff //
        ///////////////////
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

        super.setVelocity(speed * dx, speed * dy);

        /* publish new position to listeners */
        if (Math.abs(dx) > 0.0f || Math.abs(dy) > 0.0f) {
            EventQueue.getInstance().invoke(new PlayerMoveEvent(x, y));
            animationTime += timeDeltaMillis * 0.001;
        }
        currentSprite = walkAnimation.getKeyFrame(animationTime,true);

        //////////////////
        // Weapon stuff //
        //////////////////
        if (this.isFiringWeapon && this.lastMouseEvent != null) {
            this.fireWeapon();
        }

        super.update(timeDeltaMillis);
    }

    @Override
    public void draw(Camera camera) {
        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, lookingLeft, false);
        super.drawHitBox(camera);
    }

    public void onCollisionEvent(CollisionEvent event) {
    }

    private void fireWeapon() {
        float dx = GameManager.getInstance().getCamera().getXfromEvent(this.lastMouseEvent) - getX();
        float dy = GameManager.getInstance().getCamera().getYfromEvent(this.lastMouseEvent) - getY();
        weapon.fire(this.x, this.y, dx, dy);
    }

    private void onMousePress(MousePressEvent event) {
        this.isFiringWeapon = true;
        this.lastMouseEvent = event;
    }

    private void onMouseRelease(MouseReleaseEvent event) {
        this.isFiringWeapon = false;
    }

    private void onMouseMove(MouseMoveEvent event) {
        this.lastMouseEvent = event;
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
    public void heal(float hp) {
        health += hp;
        if (health >= getMaxHealth()) {
            health = getMaxHealth();
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

    public void addMaxHealth(float hp) {
        maxHealth += hp;
    }

    public float getSpeed() {
        return speed;
    }

    public void addSpeed(float speed) {
        this.speed += speed;
    }

    @Override
    public void onDispose() {
        super.onDispose();
        // We intentionally do not do anything here anymore, because the player persists between rooms / levels. We only
        // reset the input handling.
        keyHoldWatcher.releaseAll();
        this.isFiringWeapon = false;
    }

    public void hardDisposePlayer() {
        // Here, we DO dispose stuff, because the whole game is resetting.
        EventQueue.getInstance().deregisterConsumer(mousePressConsumer, EventType.MOUSE_PRESS_EVENT);
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
