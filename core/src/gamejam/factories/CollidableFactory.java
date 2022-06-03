package gamejam.factories;

import gamejam.objects.Collidable;
import gamejam.objects.Entity;

public class CollidableFactory extends AbstractFactory<Collidable> {
    private static CollidableFactory instance = null;

    public static CollidableFactory getInstance() {
        if (instance == null) {
            instance = new CollidableFactory();
        }
        return instance;
    }
}
