package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;

public class PlayerSpeedEffect implements ChipEffect {

    private final float multiplier;
    private float added;;

    public PlayerSpeedEffect(float multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply() {
        Player player = PlayerFactory.getInstance().getPlayer();
        float extraSpeed = (multiplier - 1f) * player.getSpeed();
        added += extraSpeed;
        player.addSpeed(extraSpeed);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
        Player player = PlayerFactory.getInstance().getPlayer();
        player.addSpeed(-added);
    }

    @Override
    public String description() {
        return "Makes the player move faster.";
    }
}
