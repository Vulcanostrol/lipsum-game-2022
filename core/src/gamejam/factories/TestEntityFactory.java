package gamejam.factories;

import gamejam.objects.Entity;
import gamejam.objects.TestEntity;

public class TestEntityFactory extends AbstractFactory<TestEntity> {
    private static TestEntityFactory instance = null;

    static {
        SelfCollidableFactory.getInstance().addSubFactory(TestEntityFactory.getInstance());
    }

    public static TestEntityFactory getInstance() {
        if (instance == null) {
            instance = new TestEntityFactory();
        }
        return instance;
    }
}
