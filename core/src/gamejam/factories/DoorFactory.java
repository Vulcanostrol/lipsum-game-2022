package gamejam.factories;

import gamejam.objects.collidable.Door;

public class DoorFactory extends AbstractFactory<Door> {
    private static DoorFactory instance = null;

    static {
        CollidableFactory.getInstance().addSubFactory(DoorFactory.getInstance());
    }

    public static DoorFactory getInstance() {
        if (instance == null) {
            instance = new DoorFactory();
        }
        return instance;
    }
}
