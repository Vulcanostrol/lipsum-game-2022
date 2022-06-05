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

    // firing logic stuffs
    protected int coolDownMs;
    protected long lastFireTime = 0;

    protected Random random = new Random();

    public Weapon(float damage, float bulletSpeed, float bulletSize, int bulletAmount, float bulletAngleSpread, int coolDownMs) {
        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
        this.bulletSize = bulletSize;
        this.bulletAmount = bulletAmount;
        this.bulletAngleSpread = bulletAngleSpread;
        this.coolDownMs = coolDownMs;
        EventConsumer<WeaponFireEvent> consumer = this::onWeaponFire;
        EventQueue.getInstance().registerConsumer(consumer, EventType.WEAPON_FIRED);
    }

    private void onWeaponFire(WeaponFireEvent event) {
        if (event.getWeapon() == this) {
            GameManager.getInstance().getCamera().startShake();
        }
    }

    /**
     * Wanneer de {@link gamejam.objects.collidable.Player} isFiringWeapon true is, dan wordt deze methode aangeroepen.
     * @return True als het wapen werd afgevuurd, anders false (cooldown tijd nog niet verstreken).
     */
    public boolean fire(float originX, float originY, float xVelocity, float yVelocity) {
        final long now = System.currentTimeMillis();
        if (now - this.lastFireTime > this.coolDownMs) {
            this.lastFireTime = now;
            EventQueue.getInstance().invoke(new WeaponFireEvent(this));
            return true;
        } else {
            return false;
        }
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

    public int getCoolDownMs() {
        return coolDownMs;
    }

    public void setCoolDownMs(int coolDownMs) {
        this.coolDownMs = coolDownMs;
    }
}
