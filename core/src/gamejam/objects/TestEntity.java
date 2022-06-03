package gamejam.objects;

import com.badlogic.gdx.graphics.Texture;

public class TestEntity extends Collidable implements Damageable {

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
    public float getHealth() {
        return health;
    }

    @Override
    public float getMaxHealth() {
        return 100;
    }
}
