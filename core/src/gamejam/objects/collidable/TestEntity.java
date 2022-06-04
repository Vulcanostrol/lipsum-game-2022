package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import gamejam.objects.Damageable;
import gamejam.objects.collidable.SelfCollidable;

public class TestEntity extends SelfCollidable implements Damageable {

    private float health = 100;

    public TestEntity(float x, float y) {
        super(25, 50, 25, 25);
        setPosition(x, y);
        setVelocity(0 ,0);
        this.sprite = new Texture("badlogic.jpg");
    }

    public TestEntity(float x, float y, float speedX, float speedY) {
        this(x, y);
        setVelocity(speedX, speedY);
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
        return 100;
    }
}
