package gamejam.chips;

import gamejam.chips.effects.BulletSizeEffect;
import gamejam.chips.effects.ScreenShakeEffect;

public class BulletChip extends Chip {
    public BulletChip() {
        super("Bullet Chip");
        addEffect(new BulletSizeEffect(1.2f));
        addEffect(new ScreenShakeEffect(1.5f));
    }
}
