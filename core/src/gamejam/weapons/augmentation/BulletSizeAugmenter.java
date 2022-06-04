package gamejam.weapons.augmentation;

import gamejam.weapons.Weapon;

public class BulletSizeAugmenter extends WeaponAugmenter {

    private Float scaleFactor = null;
    private Integer flatFactor = null;

    public BulletSizeAugmenter(float scaleFactor) {
        super("Augments the size of the bullet by relative scale");
        this.scaleFactor = scaleFactor;
    }

    public BulletSizeAugmenter(int flatFactor) {
        super("Augments the size of the bullet by absolute scale");
        this.flatFactor = flatFactor;
    }

    @Override
    public void augment(Weapon weapon) {
        if (scaleFactor != null) {
            weapon.scaleBulletSize(scaleFactor);
        } else {
            weapon.scaleBulletSize(flatFactor);
        }
    }
}
