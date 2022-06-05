package gamejam.objects.collidable.explosion;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
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

    private final Texture spriteSheet;
    private Animation<TextureRegion> animation;
    private TextureRegion currentSprite;

    public Explosion(float x, float y, float spriteWidth, float spriteHeight, float collisionWidth,
                     float collisionHeight, float damage, boolean hurtsPlayer, float durationMS,  Texture spriteSheet, int keyFrameWidth, int keyFrameHeight) {
        super(spriteWidth, spriteHeight, collisionWidth, collisionHeight);
        this.damage = damage;
        this.hurtsPlayer = hurtsPlayer;
        this.durationMS = durationMS;
        this.timeLeft = durationMS;
        this.spriteSheet = spriteSheet;
        TextureRegion[] frames = TextureRegion.split(spriteSheet, keyFrameWidth, keyFrameHeight)[0];
        animation = new Animation<>(0.2f, frames);
        setPosition(x, y);
    }

    public Explosion(float x, float y, float spriteWidth, float spriteHeight, float collisionWidth,
                     float collisionHeight, float damage, boolean hurtsPlayer, float durationMS) {
        this(x,y,spriteWidth,spriteHeight,collisionWidth,collisionHeight,damage,hurtsPlayer,durationMS,new Texture("entity/explosion.png"), 40, 32);
    }

    public Explosion(float x, float y) {
        this(x,y, 40 * 5, 32 * 5, 40 * 5, 32 * 5, 0, false, 600, new Texture("entity/explosion.png"), 40, 32);
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
    public void draw(Camera camera) {
        TextureRegion[] keyFrames = animation.getKeyFrames();
        int frame = Math.min(keyFrames.length - 1, (keyFrames.length - 1) - ((int) (keyFrames.length * (timeLeft / durationMS))));
        currentSprite = keyFrames[frame];

        camera.draw(currentSprite, x - spriteWidth / 2, y, spriteWidth, spriteHeight, false, false);
    }

    @Override
    public void afterDraw() {
        frame += 1;
        super.afterDraw();
    }
}
