package gamejam.objects.collidable.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.Camera;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.event.events.EntityDeathEvent;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.Damageable;
import gamejam.objects.collidable.Player;

public class Bullet extends Collidable {

    public final float BULLET_DESPAWN_RANGE = 5000;

    private float damage;

    // TODO: When we want bullets that can damage the player, we can fuck with this. FOr now, leave this as is PLEASE!
    boolean damagePlayer = false;

    public Bullet(float x, float y, float xVelocity, float yVelocity, float damage, float bulletSize) {
        super(bulletSize, bulletSize, bulletSize, bulletSize);
        setPosition(x, y);
        setVelocity(xVelocity, yVelocity);
        sprite = new Texture("bullet.png");
        this.damage = damage;

    }

    /**
     * Checks if this bullet has collided with something, and if the other object is damageable. If so: damage it.
     */
    public void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidesWith() == this && event.getCollidingObject() instanceof Damageable) {
            tryDamageEntity(((Damageable) event.getCollidingObject()));
        } else if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Damageable) {
            tryDamageEntity(((Damageable) event.getCollidesWith()));
        }
    }

    private void tryDamageEntity(Damageable entity) {
        if (!damagePlayer && entity instanceof Player) return;
        if (damagePlayer && !(entity instanceof Player)) return;
        entity.damage(damage);
        despawn();
    }

    @Override
    public void draw(Camera camera) {
        super.draw(camera);
    }

    @Override
    public void update(float timeDeltaMillis) {
        super.update(timeDeltaMillis);
        if (Math.abs(x) + Math.abs(y) > BULLET_DESPAWN_RANGE) {
            despawn();
        }
    }
}
