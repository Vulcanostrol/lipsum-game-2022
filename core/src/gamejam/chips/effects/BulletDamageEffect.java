package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;
import gamejam.weapons.augmentation.BulletDamageAugmenter;
import gamejam.weapons.augmentation.BulletSizeAugmenter;

public class BulletDamageEffect implements ChipEffect {

    private final Float strength;
    private final Integer strengthInt;

    public BulletDamageEffect(float strength) {
        this.strength = strength;
        this.strengthInt = null;
    }

    public BulletDamageEffect(int strengthInt) {
        this.strengthInt = strengthInt;
        this.strength = null;
    }

    @Override
    public void apply() {
        BulletDamageAugmenter augmenter;
        if (strength != null) {
            augmenter = new BulletDamageAugmenter(strength);
        } else {
            augmenter = new BulletDamageAugmenter(strengthInt);
        }
        Player player = PlayerFactory.getInstance().getPlayer();
        player.getWeapon().applyAugmentation(augmenter);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public String description() {
        return "Makes your bullets do more damage.";
    }
}
