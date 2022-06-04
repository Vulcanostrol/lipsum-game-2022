package gamejam.weapons.augmentation;

import gamejam.weapons.Weapon;

public class BulletDamageAugmenter extends WeaponAugmenter {

    private Float scaleFactor = null;
    private Integer flatFactor = null;

    public BulletDamageAugmenter(float scaleFactor) {
        super("Augments the damage of the bullet by relative scale");
        this.scaleFactor = scaleFactor;
    }

    public BulletDamageAugmenter(int flatFactor) {
        super("Augments the damage of the bullet by absolute scale");
        this.flatFactor = flatFactor;
    }

    @Override
    public void augment(Weapon weapon) {
        if (scaleFactor != null) {
            weapon.scaleBulletDamage(scaleFactor);
        } else {
            weapon.scaleBulletDamage(flatFactor);
        }
    }
}
