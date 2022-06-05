package gamejam.objects.collidable.enemies;

import com.badlogic.gdx.graphics.Texture;
import gamejam.Camera;
import gamejam.TextureStore;
import gamejam.event.EventQueue;
import gamejam.event.events.ScoreEvent;
import gamejam.objects.Damageable;
import gamejam.objects.collidable.SelfCollidable;
import gamejam.objects.collidable.Traversable;

public class AbstractEnemy extends SelfCollidable implements Damageable, Traversable {

    private final float maxHealth;
    private float health;

    private Texture healthBarGreenTexture;
    private Texture healthBarRedTexture;

    public int spawnRate;
    public int spawnRateMutate;

    public AbstractEnemy(float initialX, float initialY, float spriteWidth, float spriteHeight, float collisionWidth,
                         float collisionHeight, float maxHealth) {
        super(spriteWidth, spriteHeight, collisionWidth, collisionHeight);
        this.x = initialX;
        this.y = initialY;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.healthBarGreenTexture = TextureStore.getTileTextureByName("health_bar_green");
        this.healthBarRedTexture = TextureStore.getTileTextureByName("health_bar_red");
    }

    @Override
    public void damage(float damage) {
        health -= damage;
        if (health < 0) {
            // DEATH
            EventQueue.getInstance().invoke(new ScoreEvent(10));
            despawn();
        }
    }

    @Override
    public void update(float timeDeltaMillis) {
        super.update(timeDeltaMillis);
    }

    @Override
    public void heal(float hp) {
        health += hp;
        if (health > getMaxHealth()) {
            health = getMaxHealth();
        }
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public float getMaxHealth() {
        return this.maxHealth;
    }

    public void attack() {

    }

    /**
     * Draws a health bar for the enemy
     * @param camera Ik neem aan dat je wel kan verzinnen wat dit moet zijn.
     */
    @Override
    public void draw(Camera camera) {
        drawShadow(camera);
        final float xLeft = this.x - this.collisionWidth / 2;
        final float healthLeftWidth = this.collisionWidth * (this.health / this.maxHealth);
        camera.draw(this.healthBarRedTexture, xLeft, this.y - 5f, this.collisionWidth, 5f);
        camera.draw(this.healthBarGreenTexture, xLeft, this.y - 5f, healthLeftWidth, 5f);
    }

    @Override
    protected DropShadowType getDropShadowType() {
        return DropShadowType.NORMAL;
    }
}
