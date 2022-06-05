package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;
import gamejam.weapons.augmentation.BulletAmountAugmenter;
import gamejam.weapons.augmentation.BulletExplosionAugmenter;

public class SplashBulletEffect implements ChipEffect {

    private final int amount;
    private final int initAmount;

    public SplashBulletEffect(int initialAmount, int addAmount) {
        // TODO: Reduce damage to normalize by bullet amount?
        this.initAmount = initialAmount;
        this.amount = addAmount;
    }

    @Override
    public void apply() {
        BulletExplosionAugmenter augmenter = new BulletExplosionAugmenter(initAmount, amount);
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
        return "Your bullets now EXPLODE!";
    }
}
