package gamejam.factories.explosion;

import gamejam.factories.AbstractFactory;
import gamejam.factories.CollidableFactory;
import gamejam.objects.collidable.explosion.Explosion;

public class ExplosionFactory extends AbstractFactory<Explosion> {
    private static ExplosionFactory instance = null;

    static {
        CollidableFactory.getInstance().addSubFactory(ExplosionFactory.getInstance());
    }

    public ExplosionFactory() {
        super(Explosion.class);
    }

    public static ExplosionFactory getInstance() {
        if (instance == null) {
            instance = new ExplosionFactory();
        }
        return instance;
    }
}
