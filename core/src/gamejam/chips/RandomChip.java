package gamejam.chips;

import gamejam.chips.effects.*;

import java.util.Random;

public class RandomChip extends Chip {

    public static final String[] CHIP_NAMES = {
            "Sniper", "Bullet", "Red", "Blue", "Omega", "Delta", "Mauk", "Koen", "Gerben", "Chris", "Jan Douwe",
            "Green", "Yellow", "Brick", "Beer", "Banana", "Screening", "Ultra", "Small", "Account", "Binding",
    };

    public RandomChip() {
        super(CHIP_NAMES[new Random().nextInt(CHIP_NAMES.length)] + " Chip");
        addEffect(getRandomPositiveEffect());
        addEffect(getRandomNegativeEffect());
    }

    private ChipEffect getRandomPositiveEffect() {
        ChipEffect[] effects = {
                new BulletDamageEffect(1.5f),
                new BulletSizeEffect(1.2f),
                new MoreMaxHealthEffect(1.1f),
        };
        return effects[new Random().nextInt(effects.length)];
    }

    private ChipEffect getRandomNegativeEffect() {
        ChipEffect[] effects = {
                new ScreenShakeEffect(1.5f),
                new ScreenThinnerEffect(0.9f),
                new ScreenSwollEffect(0.9f),
                new CameraDvdEffect(1.3f),
        };
        return effects[new Random().nextInt(effects.length)];
    }
}
