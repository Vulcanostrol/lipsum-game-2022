package gamejam.factories.explosion;

import gamejam.factories.AbstractFactory;
import gamejam.factories.CollidableFactory;
import gamejam.objects.collidable.explosion.BombExplosion;
import gamejam.objects.collidable.explosion.Explosion;

public class BombExplosionFactory extends AbstractFactory<BombExplosion> {
    private static BombExplosionFactory instance = null;

    static {
        ExplosionFactory.getInstance().addSubFactory(BombExplosionFactory.getInstance());
    }

    public BombExplosionFactory() {
        super(BombExplosion.class);
    }

    public static BombExplosionFactory getInstance() {
        if (instance == null) {
            instance = new BombExplosionFactory();
        }
        return instance;
    }
}
