package gamejam.weapons.augmentation;

import gamejam.weapons.Weapon;

public class BulletSpeedAugmenter extends WeaponAugmenter {

    private Integer flatFactor;

    public BulletSpeedAugmenter(int flatFactor) {
        super("Augments the speed of the bullets fired.");
        this.flatFactor = flatFactor;
    }

    @Override
    public void augment(Weapon weapon) {
        weapon.addBulletSpeed(flatFactor);
    }
}
