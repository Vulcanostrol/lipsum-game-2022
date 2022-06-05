package gamejam.factories;

import gamejam.objects.collidable.FinalDoor;

public class FinalDoorFactory extends AbstractFactory<FinalDoor> {
    private static FinalDoorFactory instance = null;

    static {
        CollidableFactory.getInstance().addSubFactory(FinalDoorFactory.getInstance());
    }

    public FinalDoorFactory() {
        super(FinalDoor.class);
    }

    public static FinalDoorFactory getInstance() {
        if (instance == null) {
            instance = new FinalDoorFactory();
        }
        return instance;
    }

}
