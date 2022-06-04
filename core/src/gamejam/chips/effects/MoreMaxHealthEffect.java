package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;

public class MoreMaxHealthEffect implements ChipEffect {

    private final float multiplier;

    public MoreMaxHealthEffect(float multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public void apply() {
        Player player = PlayerFactory.getInstance().getPlayer();
        float extraHealth = (multiplier - 1f) * player.getMaxHealth();
        player.addMaxHealth(extraHealth);
        player.heal(extraHealth);
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public String description() {
        return "Increases your max health.";
    }
}
