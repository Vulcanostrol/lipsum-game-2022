package gamejam.weapons;

import gamejam.weapons.augmentation.WeaponAugmenter;

import java.util.Random;

public abstract class Weapon {

    protected float damage;
    protected float bulletSpeed;
    protected float bulletSize;
    protected int bulletAmount;
    protected float bulletAngleSpread;

    protected Random random = new Random();

    public Weapon(float damage, float bulletSpeed, float bulletSize, int bulletAmount, float bulletAngleSpread) {
        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
        this.bulletSize = bulletSize;
        this.bulletAmount = bulletAmount;
        this.bulletAngleSpread = bulletAngleSpread;
    }

    public abstract void fire(float originX, float originY, float xVelocity, float yVelocity);

    public void applyAugmentation(WeaponAugmenter weaponAugmenter) {
        weaponAugmenter.augment(this);
    }

    public void scaleBulletSize(float scale) {
        bulletSize = bulletSize * scale;
    }

    public void scaleBulletSize(int scale) {
        bulletSize += scale;
    }

}
