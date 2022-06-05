package gamejam.chips;

import gamejam.GameManager;
import gamejam.chips.effects.BulletAmountEffect;
import gamejam.chips.effects.BulletDamageEffect;
import gamejam.chips.effects.BulletSizeEffect;
import gamejam.chips.effects.ScreenShakeEffect;
import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;

public class BulletAmountChip extends Chip {

    public BulletAmountChip() {
        super("Bullet Amount Chip");
        addEffect(new BulletAmountEffect(1));
//        addEffect(new BulletDamageEffect(-5));
    }

}
