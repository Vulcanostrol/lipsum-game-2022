package gamejam.factories.bullets;

import gamejam.factories.AbstractFactory;
import gamejam.objects.collidable.bullets.PyramidBullet;

public class PyramidEnemyBulletFactory extends AbstractFactory<PyramidBullet> {
    private static PyramidEnemyBulletFactory instance = null;

    static {
        BulletFactory.getInstance().addSubFactory(PyramidEnemyBulletFactory.getInstance());
    }

    public PyramidEnemyBulletFactory() {
        super(PyramidBullet.class);
    }

    public static PyramidEnemyBulletFactory getInstance() {
        if (instance == null) {
            instance = new PyramidEnemyBulletFactory();
        }
        return instance;
    }
}
