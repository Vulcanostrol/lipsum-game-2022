package gamejam.factories;

import gamejam.objects.collidable.Bullet;

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
