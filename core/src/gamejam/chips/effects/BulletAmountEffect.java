package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;
import gamejam.weapons.augmentation.BulletAmountAugmenter;
import gamejam.weapons.augmentation.BulletDamageAugmenter;

public class BulletAmountEffect implements ChipEffect {

    private final int amount;

    public BulletAmountEffect(int amount) {
        // TODO: Reduce damage to normalize by bullet amount?
        this.amount = amount;
    }

    @Override
    public void apply() {
        BulletAmountAugmenter augmenter = new BulletAmountAugmenter(amount);
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
        return "Fire more bullets!";
    }
}
