package gamejam.factories;

import gamejam.objects.Player;

/**
 *
 */
public class PlayerFactory extends AbstractFactory<Player> {
    private static PlayerFactory instance = null;

    public static PlayerFactory getInstance() {
        if (instance == null) {
            instance = new PlayerFactory();
        }
        return instance;
    }
}
