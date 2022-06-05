package gamejam.objects.collidable.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import gamejam.event.events.CollisionEvent;
import gamejam.objects.Damageable;
import gamejam.objects.collidable.Player;
import gamejam.objects.collidable.enemies.AbstractEnemy;

public class PyramidBullet extends Bullet {
    public PyramidBullet(float x, float y, float xVelocity, float yVelocity, float damage, float bulletSize) {
        super(x, y, xVelocity, yVelocity, damage, bulletSize);
        damagePlayer = true;
        sprite = new Texture("bullet_pyramid.png");
        spriteInsteadOfTexture = new Sprite(sprite);
    }

    @Override
    protected void handleDeSpawn(CollisionEvent event) {
        if (!(event.getCollidingObject() instanceof AbstractEnemy || event.getCollidesWith() instanceof AbstractEnemy)) {
            despawn();
        }
    }
}
