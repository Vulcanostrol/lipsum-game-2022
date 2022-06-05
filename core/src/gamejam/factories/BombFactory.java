package gamejam.factories;

import gamejam.objects.Bomb;

public class BombFactory extends AbstractFactory<Bomb> {
    private static BombFactory instance = null;

    static {
        EntityFactory.getInstance().addSubFactory(BombFactory.getInstance());
    }

    public BombFactory() {
        super(Bomb.class);
    }

    public static BombFactory getInstance() {
        if (instance == null) {
            instance = new BombFactory();
        }
        return instance;
    }
}
