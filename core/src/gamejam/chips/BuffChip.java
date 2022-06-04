package gamejam.chips;

import gamejam.chips.effects.MoreMaxHealthEffect;
import gamejam.chips.effects.ScreenSwollEffect;

public class BuffChip extends Chip {
    public BuffChip() {
        super("Buff Chip");
        addEffect(new MoreMaxHealthEffect(1.1f));
        addEffect(new ScreenSwollEffect(0.9f));
    }
}
