package gamejam.factories;

import gamejam.objects.collidable.enemies.DroneEnemy;

public class DroneEnemyFactory extends AbstractFactory<DroneEnemy> {
    private static DroneEnemyFactory instance = null;

    static {
        AbstractEnemyFactory.getInstance().addSubFactory(DroneEnemyFactory.getInstance());
    }

    public static DroneEnemyFactory getInstance() {
        if (instance == null) {
            instance = new DroneEnemyFactory();
        }

        return instance;
    }
}
