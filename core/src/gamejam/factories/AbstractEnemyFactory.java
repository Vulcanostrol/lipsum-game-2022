package gamejam.factories;

import gamejam.objects.collidable.enemies.AbstractEnemy;

public class AbstractEnemyFactory extends AbstractFactory<AbstractEnemy> {
    private static AbstractEnemyFactory instance = null;

    static {
        SelfCollidableFactory.getInstance().addSubFactory(AbstractEnemyFactory.getInstance());
    }

    public AbstractEnemyFactory(){super(AbstractEnemy.class);}

    public static AbstractEnemyFactory getInstance() {
        if (instance == null) {
            instance = new AbstractEnemyFactory();
        }

        return instance;
    }
}
