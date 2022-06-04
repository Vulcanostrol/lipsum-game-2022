package gamejam.factories;

import gamejam.objects.collidable.Collidable;

public class CollidableFactory extends AbstractFactory<Collidable> {
    private static CollidableFactory instance = null;

    static {
        EntityFactory.getInstance().addSubFactory(CollidableFactory.getInstance());
    }

    public CollidableFactory() {
        super(Collidable.class);
    }

    public static CollidableFactory getInstance() {
        if (instance == null) {
            instance = new CollidableFactory();
        }
        return instance;
    }
}
