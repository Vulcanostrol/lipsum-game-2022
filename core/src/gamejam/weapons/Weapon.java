package gamejam.weapons;

import com.badlogic.gdx.Game;
import gamejam.GameManager;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.WeaponFireEvent;
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
        EventConsumer<WeaponFireEvent> consumer = this::onWeaponFire;
        EventQueue.getInstance().registerConsumer(consumer, EventType.WEAPON_FIRED);
    }

    private void onWeaponFire(WeaponFireEvent event) {
        if (event.getWeapon() == this) {
            GameManager.getInstance().getCamera().startShake();
        }
    }

    public void fire(float originX, float originY, float xVelocity, float yVelocity) {
        EventQueue.getInstance().invoke(new WeaponFireEvent(this));
    }

    public void applyAugmentation(WeaponAugmenter weaponAugmenter) {
        weaponAugmenter.augment(this);
    }

    public void scaleBulletSize(float scale) {
        bulletSize = bulletSize * scale;
    }

    public void scaleBulletSize(int scale) {
        bulletSize += scale;
    }

    public void scaleBulletDamage(float scale) {
        damage = damage * scale;
    }

    public void scaleBulletDamage(int scale) {
        damage += scale;
    }

    public void addBulletAmount(int amount) {
        this.bulletAmount += amount;
    }
    public void addBulletSpeed(int amount) {
        this.bulletSpeed += amount;
    }

    public float getDamage() {
        return damage;
    }
}
