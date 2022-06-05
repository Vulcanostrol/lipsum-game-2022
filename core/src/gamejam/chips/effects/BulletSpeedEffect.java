package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;
import gamejam.weapons.augmentation.BulletAmountAugmenter;
import gamejam.weapons.augmentation.BulletSpeedAugmenter;

public class BulletSpeedEffect implements ChipEffect {

    private final int amount;

    public BulletSpeedEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply() {
        BulletSpeedAugmenter augmenter = new BulletSpeedAugmenter(amount);
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
        return "Fire faster bullets!";
    }
}
