package gamejam.weapons.augmentation;

import gamejam.weapons.Weapon;

public class BulletExplosionAugmenter extends WeaponAugmenter {

    private final int init;
    private final int expansion;

    public BulletExplosionAugmenter(int init, int expansion) {
        super("Gives bullets explosions");
        this.init = init;
        this.expansion = expansion;
    }

    @Override
    public void augment(Weapon weapon) {
        weapon.augmentExplosion(init, expansion);
    }
}
