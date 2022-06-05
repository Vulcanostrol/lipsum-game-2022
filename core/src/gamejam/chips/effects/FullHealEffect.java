package gamejam.chips.effects;

import gamejam.factories.PlayerFactory;
import gamejam.objects.collidable.Player;

public class FullHealEffect implements ChipEffect {

    public FullHealEffect() {

    }

    @Override
    public void apply() {
        Player player = PlayerFactory.getInstance().getPlayer();
        player.heal(player.getMaxHealth());
    }

    @Override
    public void update() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public String description() {
        return "Heal to full health";
    }
}
