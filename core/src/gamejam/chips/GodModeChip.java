package gamejam.chips;

import gamejam.chips.effects.BulletDamageEffect;
import gamejam.chips.effects.BulletSizeEffect;
import gamejam.chips.effects.ScreenShakeEffect;

public class GodModeChip extends Chip {
    public GodModeChip() {
        super("God Chip");
        addEffect(new BulletDamageEffect(50f));
        addEffect(new BulletSizeEffect(3f));
    }
}
