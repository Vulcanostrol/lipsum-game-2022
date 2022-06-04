package gamejam.factories;

import gamejam.objects.Wall;

public class WallFactory extends AbstractFactory<Wall> {
    private static WallFactory instance = null;

    static {
        CollidableFactory.getInstance().addSubFactory(WallFactory.getInstance());
    }

    public WallFactory() {
        super(Wall.class);
    }

    public static WallFactory getInstance() {
        if (instance == null) {
            instance = new WallFactory();
        }
        return instance;
    }

}
