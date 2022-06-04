package gamejam.chips;

import gamejam.chips.Chip;
import gamejam.chips.effects.BulletDamageEffect;
import gamejam.chips.effects.ScreenThinnerEffect;

public class SniperChip extends Chip {
    public SniperChip() {
        super("Sniper Chip");
        addEffect(new BulletDamageEffect(1.5f));
        addEffect(new ScreenThinnerEffect(0.9f));
    }
}
