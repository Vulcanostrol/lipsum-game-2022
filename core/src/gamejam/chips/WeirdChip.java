package gamejam.chips;

import gamejam.chips.effects.CameraDvdEffect;
import gamejam.chips.effects.MoreMaxHealthEffect;
import gamejam.chips.effects.ScreenSwollEffect;

public class WeirdChip extends Chip {
    public WeirdChip() {
        super("Weird Chip");
        addEffect(new MoreMaxHealthEffect(1.1f));
        addEffect(new CameraDvdEffect(1.3f));
    }
}
