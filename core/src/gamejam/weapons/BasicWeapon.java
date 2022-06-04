package gamejam.weapons;

import com.badlogic.gdx.math.Vector2;
import gamejam.objects.collidable.bullets.Bullet;

public class BasicWeapon extends Weapon {

    private static final int BASIC_WEAPON_BASE_DAMAGE = 10;
    private static final int BASIC_WEAPON_BASE_SPEED = 1000;

    private static final float BASIC_WEAPON_BULLET_SIZE = 50;
    private static final int BASIC_WEAPON_BULLET_AMOUNT = 1;
    private static final float BASIC_WEAPON_BULLET_ANGLE_SPREAD = 0;

//    private WeaponAugmenter augmenter = new BulletSizeAugmenter(1.1f);

    public BasicWeapon() {
        super(BASIC_WEAPON_BASE_DAMAGE, BASIC_WEAPON_BASE_SPEED, BASIC_WEAPON_BULLET_SIZE,
                BASIC_WEAPON_BULLET_AMOUNT, BASIC_WEAPON_BULLET_ANGLE_SPREAD);
    }

    @Override
    public void fire(float originX, float originY, float dx, float dy) {
        Vector2 vector2 = new Vector2(dx, dy).nor();
//        applyAugmentation(augmenter);

        if (this.bulletAmount == 1) {
            new Bullet(originX, originY, vector2.x * this.bulletSpeed, vector2.y * this.bulletSpeed, this.damage, bulletSize);
        } else {
            for (int i = 0; i < bulletAmount; i++) {
                float xOffset = random.nextFloat() * bulletAngleSpread - bulletAngleSpread;
                float yOffset = random.nextFloat() * bulletAngleSpread - bulletAngleSpread;
                new Bullet(originX, originY, (vector2.x + xOffset) * this.bulletSpeed, (vector2.y + yOffset) * this.bulletSpeed, this.damage, bulletSize);
            }
        }
    }
}
