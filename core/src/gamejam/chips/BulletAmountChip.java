package gamejam.chips;

import gamejam.chips.effects.BulletAmountEffect;

public class BulletAmountChip extends Chip {

    public BulletAmountChip() {
        super("Bullet Amount Chip");
        addEffect(new BulletAmountEffect(1, 1f));
    }

}
