package gamejam.factories.enemies;

import gamejam.factories.AbstractFactory;
import gamejam.objects.collidable.enemies.PyramidEnemy;

public class PyramidEnemyFactory extends AbstractFactory<PyramidEnemy> {
    private static PyramidEnemyFactory instance = null;

    static {
        AbstractEnemyFactory.getInstance().addSubFactory(PyramidEnemyFactory.getInstance());
    }

    public PyramidEnemyFactory(){super(PyramidEnemy.class);}

    public static PyramidEnemyFactory getInstance() {
        if (instance == null) {
            instance = new PyramidEnemyFactory();
        }

        return instance;
    }
}
