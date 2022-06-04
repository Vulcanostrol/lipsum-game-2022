package gamejam.factories.bullets;

import gamejam.factories.AbstractFactory;
import gamejam.factories.CollidableFactory;
import gamejam.objects.collidable.bullets.Bullet;

public class BulletFactory extends AbstractFactory<Bullet> {
    private static BulletFactory instance = null;

    static {
        CollidableFactory.getInstance().addSubFactory(BulletFactory.getInstance());
    }

    public BulletFactory() {
        super(Bullet.class);
    }

    public static BulletFactory getInstance() {
        if (instance == null) {
            instance = new BulletFactory();
        }
        return instance;
    }
}
