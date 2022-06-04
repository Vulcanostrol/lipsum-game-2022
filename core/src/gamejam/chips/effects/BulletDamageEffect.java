package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;
import gamejam.weapons.augmentation.BulletDamageAugmenter;
import gamejam.weapons.augmentation.BulletSizeAugmenter;

public class BulletDamageEffect implements ChipEffect {

    private final float strength;

    public BulletDamageEffect(float strength) {
        this.strength = strength;
    }

    @Override
    public void apply() {
        BulletDamageAugmenter augmenter = new BulletDamageAugmenter(strength);
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
