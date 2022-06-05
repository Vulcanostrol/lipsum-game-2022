package gamejam.objects.collidable.explosion;

import com.badlogic.gdx.graphics.Texture;
import gamejam.event.events.CollisionEvent;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.collidable.Player;
import gamejam.objects.collidable.enemies.AbstractEnemy;

public class Explosion extends Collidable {
    protected final boolean hurtsPlayer;
    protected final float damage;

    // Duration of explosion before removal in milliseconds
    protected final float durationMS;
    protected float timeLeft;

    protected int frame = 0;

    public Explosion(float x, float y, float spriteWidth, float spriteHeight, float collisionWidth,
                     float collisionHeight, float damage, boolean hurtsPlayer, float durationMS) {
        super(spriteWidth, spriteHeight, collisionWidth, collisionHeight);
        this.damage = damage;
        this.hurtsPlayer = hurtsPlayer;
        this.durationMS = durationMS;
        this.timeLeft = durationMS;
        setPosition(x, y);
        this.sprite = new Texture("entity/explosion.png");
    }

    @Override
    public void update(float timeDeltaMillis) {
        timeLeft -= timeDeltaMillis;
        if (timeLeft < 0) {
            despawn();
        }
        super.update(timeDeltaMillis);
    }

    @Override
    public void onCollisionEvent(CollisionEvent event) {
        super.onCollisionEvent(event);
        if (frame != 2) {
            return;
        }
        Collidable other;
        if (event.getCollidesWith() == this) {
            other = event.getCollidingObject();
        } else {
            other = event.getCollidesWith();
        }
        if (hurtsPlayer && other instanceof Player) {
            ((Player) other).damage(damage);
        }

        if (!hurtsPlayer && other instanceof AbstractEnemy) {
            ((AbstractEnemy) other).damage(damage);
        }
    }

    @Override
    public void afterDraw() {
        frame += 1;
        super.afterDraw();
    }
}
