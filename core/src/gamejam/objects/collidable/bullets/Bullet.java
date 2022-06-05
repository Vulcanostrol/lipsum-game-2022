package gamejam.objects.collidable.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.config.RoomConfiguration;
import gamejam.event.events.CollisionEvent;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.Damageable;
import gamejam.objects.collidable.Player;
import gamejam.objects.collidable.Traversable;
import gamejam.objects.collidable.enemies.AbstractEnemy;

public class Bullet extends Collidable implements Traversable {

    public final float BULLET_DESPAWN_RANGE = 5000;

    public static float HEIGHT = 15;

    private float damage;

    private float alpha;

    protected Sprite spriteInsteadOfTexture;

    // TODO: When we want bullets that can damage the player, we can fuck with this. FOr now, leave this as is PLEASE!
    boolean damagePlayer = false;

    public Bullet(float x, float y, float xVelocity, float yVelocity, float damage, float bulletSize) {
        super(bulletSize, bulletSize, bulletSize, bulletSize);
        alpha = 1;
        y -= bulletSize/2;
        setPosition(x, y);
        setVelocity(xVelocity, yVelocity);
        sprite = new Texture("bullet.png");
        spriteInsteadOfTexture = new Sprite(sprite);
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
        handleDeSpawn(event);
    }

    protected void handleDeSpawn(CollisionEvent event) {
        if (
                event.getCollidingObject() instanceof AbstractEnemy || event.getCollidesWith() instanceof AbstractEnemy
        ) {
            despawn();
        }
    }

    private void tryDamageEntity(Damageable entity) {
        if (!damagePlayer && entity instanceof Player) return;
        if (damagePlayer && !(entity instanceof Player)) return;
        entity.damage(damage);
    }

    @Override
    public void draw(Camera camera) {
        if(alpha > 0){
            camera.spriteDraw(spriteInsteadOfTexture, x - spriteWidth / 2, y + HEIGHT, spriteWidth, spriteHeight, false, false);
        }
        super.drawHitBox(camera);
        drawShadow(camera);
    }

    @Override
    protected DropShadowType getDropShadowType() {
        return DropShadowType.SMALL;
    }

    @Override
    public void update(float timeDeltaMillis) {
        super.update(timeDeltaMillis);
        int screenWidth = RoomConfiguration.ROOM_TILE_WIDTH*RoomConfiguration.TILE_PIXEL_WIDTH;
        int screenHeight = RoomConfiguration.ROOM_TILE_HEIGHT*RoomConfiguration.TILE_PIXEL_HEIGHT;
        if(
                x+spriteWidth/2 < 0 || x-spriteWidth/2 > screenWidth ||
                y+spriteHeight < 0 || y > screenHeight
        ) {
            alpha -= timeDeltaMillis/1000;
            if (alpha < 0){
                despawn();
            }
            spriteInsteadOfTexture.setAlpha(alpha);
        }

    }
}
