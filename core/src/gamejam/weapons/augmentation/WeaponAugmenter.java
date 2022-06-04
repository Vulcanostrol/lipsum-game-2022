package gamejam.weapons.augmentation;

import gamejam.weapons.Weapon;

public abstract class WeaponAugmenter {

    public String description;

    public WeaponAugmenter(String description) {
        this.description = description;
    }

    public abstract void augment(Weapon weapon);

}
