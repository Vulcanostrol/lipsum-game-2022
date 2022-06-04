package gamejam.chips.effects;

import gamejam.chips.Chip;

public class SniperChip extends Chip {
    public SniperChip() {
        super("Sniper Chip");
        addEffect(new BulletDamageEffect(1.5f));
        addEffect(new ScreenThinnerEffect(0.9f));
    }
}
