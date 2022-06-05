package gamejam.weapons.augmentation;

import gamejam.weapons.Weapon;

public class BulletAmountAugmenter extends WeaponAugmenter {

    private Integer flatFactor;

    public BulletAmountAugmenter(int flatFactor) {
        super("Augments the amount of bullets one fires");
        this.flatFactor = flatFactor;
    }

    @Override
    public void augment(Weapon weapon) {
        weapon.addBulletAmount(flatFactor);
    }
}
