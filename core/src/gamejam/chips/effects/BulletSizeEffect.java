package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;
import gamejam.weapons.augmentation.BulletSizeAugmenter;

public class BulletSizeEffect implements ChipEffect {

    private final float strength;

    public BulletSizeEffect(float strength) {
        this.strength = strength;
    }

    @Override
    public void apply() {
        BulletSizeAugmenter augmenter = new BulletSizeAugmenter(strength);
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
        return "Makes your bullets BUFF.";
    }
}
