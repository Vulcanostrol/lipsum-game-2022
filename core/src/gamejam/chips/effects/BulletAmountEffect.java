package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;
import gamejam.weapons.augmentation.BulletAmountAugmenter;
import gamejam.weapons.augmentation.BulletDamageAugmenter;

public class BulletAmountEffect implements ChipEffect {

    private final int amount;
    private final float damageMultiplier;

    public BulletAmountEffect(int amount, float damageMultiplier) {
        // TODO: Reduce damage to normalize by bullet amount?
        this.amount = amount;
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public void apply() {
        BulletAmountAugmenter augmenter = new BulletAmountAugmenter(amount);
        Player player = PlayerFactory.getInstance().getPlayer();
        player.getWeapon().applyAugmentation(augmenter);
        BulletDamageAugmenter damageAugmenter = new BulletDamageAugmenter(damageMultiplier);
        player.getWeapon().applyAugmentation(damageAugmenter);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public String description() {
        return "Fire more bullets, but decrease damage!";
    }
}
