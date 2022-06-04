package gamejam.chips;

import gamejam.chips.effects.BulletSizeEffect;

public class BulletChip extends Chip {
    public BulletChip() {
        super("Bullet Chip");
        addEffect(new BulletSizeEffect(2f));
    }
}
