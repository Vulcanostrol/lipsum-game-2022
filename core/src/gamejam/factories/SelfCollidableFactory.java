package gamejam.factories;


import gamejam.objects.collidable.SelfCollidable;

public class SelfCollidableFactory extends AbstractFactory<SelfCollidable> {
    private static SelfCollidableFactory instance = null;

    static {
        CollidableFactory.getInstance().addSubFactory(SelfCollidableFactory.getInstance());
    }

    public SelfCollidableFactory() {
        super(SelfCollidable.class);
    }

    public static SelfCollidableFactory getInstance() {
        if (instance == null) {
            instance = new SelfCollidableFactory();
        }
        return instance;
    }
}
