package gamejam.objects.collidable.enemies;

import gamejam.objects.Damageable;
import gamejam.objects.collidable.SelfCollidable;

public class AbstractEnemy extends SelfCollidable implements Damageable {

    private final float maxHealth;
    private float health;

    public AbstractEnemy(float initialX, float initialY, float spriteWidth, float spriteHeight, float collisionWidth, float collisionHeight, float maxHealth) {
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
