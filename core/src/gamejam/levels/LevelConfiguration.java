package gamejam.levels;

import java.util.Random;

public class LevelConfiguration {

    public static int N_ROOMS = 16;

    public static float UPGRADE_ROOM_CHANCE = 0.25f;

    public static int SEED = new Random().nextInt(10000);

    public static double STRAIGHTNESS_FACTOR = 0.8;

}
