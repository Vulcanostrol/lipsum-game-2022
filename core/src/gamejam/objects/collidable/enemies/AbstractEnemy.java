package gamejam.objects.collidable.enemies;

import gamejam.objects.Damageable;
import gamejam.objects.collidable.SelfCollidable;

public class AbstractEnemy extends SelfCollidable implements Damageable {

    private final float maxHealth;
    private float health;

    public int spawnRate;
    public int spawnRateMutate;

    public AbstractEnemy(float initialX, float initialY, float spriteWidth, float spriteHeight, float collisionWidth,
                         float collisionHeight, float maxHealth) {
        super(spriteWidth, spriteHeight, collisionWidth, collisionHeight);
        this.x = initialX;
        this.y = initialY;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public void damage(float damage) {
        health -= damage;
        if (health < 0) {
            // DEATH
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
}
