package gamejam.chips;

import gamejam.chips.effects.FlipScreenEffect;
import gamejam.chips.effects.MoreMaxHealthEffect;
import gamejam.chips.effects.ScreenSwollEffect;

public class ScuffedChip extends Chip {
    public ScuffedChip() {
        super("Scuffed Chip");
        addEffect(new FlipScreenEffect());
    }
}
