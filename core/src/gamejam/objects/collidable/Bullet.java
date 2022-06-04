package gamejam.objects.collidable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.CollisionEvent;
import gamejam.objects.Damageable;

public class Bullet extends Collidable {

    public static final float DAMAGE = 30;
    public final float BULLET_DESPAWN_RANGE = 5000;

    // TODO: When we want bullets that can damage the player, we can fuck with this. FOr now, leave this as is PLEASE!
    private final boolean damagePlayer = false;

    public Bullet(float x, float y, float xVelocity, float yVelocity) {
        super(25, 25, 25, 25);
        setPosition(x, y);
        setVelocity(xVelocity, yVelocity);
        sprite = new Texture("bullet.png");

        EventConsumer<CollisionEvent> collisionConsumer = this::onCollisionEvent;
        EventQueue.getInstance().registerConsumer(collisionConsumer, EventType.COLLISION_EVENT);
    }

    /**
     * Checks if this bullet has collided with something, and if the other object is damageable. If so: damage it.
     */
    private void onCollisionEvent(CollisionEvent event) {
        if (event.getCollidesWith() == this && event.getCollidingObject() instanceof Damageable) {
            tryDamageEntity(((Damageable) event.getCollidingObject()));
        } else if (event.getCollidingObject() == this && event.getCollidesWith() instanceof Damageable) {
            tryDamageEntity(((Damageable) event.getCollidesWith()));
        }
    }

    private void tryDamageEntity(Damageable entity) {
        if (!damagePlayer && entity instanceof Player) return;
        entity.damage(DAMAGE);
        despawn();
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        super.draw(spriteBatch);
    }
}
