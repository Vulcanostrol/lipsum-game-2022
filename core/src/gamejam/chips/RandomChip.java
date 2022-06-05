package gamejam.chips;

import gamejam.chips.effects.*;
import gamejam.weapons.augmentation.BulletExplosionAugmenter;

import java.util.Random;

public class RandomChip extends Chip {

    public static final String[] CHIP_NAMES = {
            "Sniper", "Bullet", "Red", "Blue", "Omega", "Delta", "Mauk", "Koen", "Gerben", "Chris", "Jan Douwe",
            "Green", "Yellow", "Brick", "Beer", "Banana", "Screening", "Ultra", "Small", "Account", "Binding",
    };

    public RandomChip(int positiveEffects, int negativeEffects) {
        super(CHIP_NAMES[new Random().nextInt(CHIP_NAMES.length)] + " Chip");
        for (int i = 0; i < positiveEffects; i++) {
            addEffect(getRandomPositiveEffect());
        }
        for (int i = 0; i < negativeEffects; i++) {
            addEffect(getRandomNegativeEffect());
        }
    }

    public RandomChip() {
        this(1, 1);
    }

    private ChipEffect getRandomPositiveEffect() {
        ChipEffect[] effects = {
                new BulletDamageEffect(1.5f),
                new BulletSizeEffect(1.2f),
                new MoreMaxHealthEffect(1.1f),
                new BulletAmountEffect(1, 0.6f),
                new BulletSpeedEffect(50),
                new FullHealEffect(),
                new PlayerSpeedEffect(1.1f),
                new SplashBulletEffect(150, 100),
        };
        return effects[new Random().nextInt(effects.length)];
    }

    private ChipEffect getRandomNegativeEffect() {
        ChipEffect[] effects = {
                new ScreenShakeEffect(1.7f),
                new ScreenThinnerEffect(0.9f),
                new ScreenSwollEffect(0.9f),
                new CameraDvdEffect(1.3f),
                new FlipScreenEffect(),
        };
        return effects[new Random().nextInt(effects.length)];
    }
}
