package gamejam.factories.enemies;

import gamejam.factories.AbstractFactory;
import gamejam.objects.collidable.enemies.CannonEnemy;

public class CannonEnemyFactory extends AbstractFactory<CannonEnemy> {
    private static CannonEnemyFactory instance = null;

    static {
        AbstractEnemyFactory.getInstance().addSubFactory(CannonEnemyFactory.getInstance());
    }

    public CannonEnemyFactory(){super(CannonEnemy.class);}

    public static CannonEnemyFactory getInstance() {
        if (instance == null) {
            instance = new CannonEnemyFactory();
        }

        return instance;
    }
}
