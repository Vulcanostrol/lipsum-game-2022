package gamejam.objects.collidable.bullets;

import com.badlogic.gdx.graphics.Texture;
import gamejam.objects.Damageable;
import gamejam.objects.collidable.Player;

public class PyramidBullet extends Bullet {
    public PyramidBullet(float x, float y, float xVelocity, float yVelocity, float damage, float bulletSize) {
        super(x, y, xVelocity, yVelocity, damage, bulletSize);
        damagePlayer = true;
        sprite = new Texture("bullet_pyramid.png");
    }
}
