package gamejam.config;

import java.util.Random;

public class LevelConfiguration {

    public static int N_ROOMS = 16;
    public static float UPGRADE_ROOM_CHANCE = 0.25f;
    public static int SEED = new Random().nextInt(1000000);
    public static double STRAIGHTNESS_FACTOR = 0.8;
    public static float SPAWN_RATE = 0.01f;
    public static float SPAWN_RATE_GROWTH = 1.1f;

}
