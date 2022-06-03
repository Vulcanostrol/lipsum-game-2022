package gamejam.factories;

import gamejam.objects.Entity;

public class EntityFactory extends AbstractFactory<Entity> {
    private static EntityFactory instance = null;

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }
        return instance;
    }
}