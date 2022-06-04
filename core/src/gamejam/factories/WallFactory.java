package gamejam.factories;

import gamejam.objects.collidable.Wall;

public class WallFactory extends AbstractFactory<Wall> {
    private static WallFactory instance = null;

    static {
        SelfCollidableFactory.getInstance().addSubFactory(WallFactory.getInstance());
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
