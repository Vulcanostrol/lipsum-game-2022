package gamejam.factories;


import gamejam.objects.SelfCollidable;

public class SelfCollidableFactory extends AbstractFactory<SelfCollidable> {
    private static SelfCollidableFactory instance = null;

    static {
        CollidableFactory.getInstance().addSubFactory(SelfCollidableFactory.getInstance());
    }

    public static SelfCollidableFactory getInstance() {
        if (instance == null) {
            instance = new SelfCollidableFactory();
        }
        return instance;
    }
}