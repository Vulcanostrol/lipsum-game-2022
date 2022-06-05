package gamejam.factories;

import gamejam.objects.collidable.Pillar;
import gamejam.objects.collidable.Wall;

public class PillarFactory extends AbstractFactory<Pillar> {
    private static PillarFactory instance = null;

    static {
        CollidableFactory.getInstance().addSubFactory(PillarFactory.getInstance());
    }

    public PillarFactory() {
        super(Pillar.class);
    }

    public static PillarFactory getInstance() {
        if (instance == null) {
            instance = new PillarFactory();
        }
        return instance;
    }

}
