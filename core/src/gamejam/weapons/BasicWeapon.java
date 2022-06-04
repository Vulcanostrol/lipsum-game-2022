package gamejam.weapons;

import com.badlogic.gdx.math.Vector2;
import gamejam.objects.collidable.Bullet;

public class BasicWeapon extends Weapon {

    private static final int BASIC_WEAPON_BASE_DAMAGE = 10;
    private static final int BASIC_WEAPON_BASE_SPEED = 1000;

    private static final float BASIC_WEAPON_BULLET_SIZE = 50;

//    private WeaponAugmenter augmenter = new BulletSizeAugmenter(1.1f);

    public BasicWeapon() {
        super(BASIC_WEAPON_BASE_DAMAGE, BASIC_WEAPON_BASE_SPEED, BASIC_WEAPON_BULLET_SIZE);
    }

    @Override
    public void fire(float originX, float originY, float dx, float dy) {
        super.fire(originX, originY, dx, dy);
        Vector2 vector2 = new Vector2(dx, dy).nor();
//        applyAugmentation(augmenter);

        new Bullet(originX, originY, vector2.x * this.bulletSpeed, vector2.y * this.bulletSpeed, this.damage, bulletSize);
    }
}
