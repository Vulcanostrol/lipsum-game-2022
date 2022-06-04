package gamejam.weapons;

import gamejam.weapons.augmentation.WeaponAugmenter;

public abstract class Weapon {

    protected float damage;
    protected float bulletSpeed;
    protected float bulletSize;

    public Weapon(float damage, float bulletSpeed, float bulletSize) {
        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
        this.bulletSize = bulletSize;
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
