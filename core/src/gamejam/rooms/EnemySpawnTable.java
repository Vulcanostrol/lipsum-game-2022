package gamejam.rooms;

import gamejam.GameManager;
import gamejam.objects.collidable.enemies.AbstractEnemy;
import gamejam.objects.collidable.enemies.CannonEnemy;
import gamejam.objects.collidable.enemies.DroneEnemy;
import gamejam.objects.collidable.enemies.PyramidEnemy;

public class EnemySpawnTable<E> {

    private static final float DRONE_ENEMY_BASE_SPAWN_RATE = 10;
    private static final float DRONE_ENEMY_SPAWN_RATE_MUTATION_FACTOR = 1;
    private static final float DRONE_LEVEL_APPEARANCE = 1;

    private static final float PYRAMID_ENEMY_BASE_SPAWN_RATE = 20;
    private static final float PYRAMID_ENEMY_SPAWN_RATE_MUTATION_FACTOR = 10;
    private static final float PYRAMID_LEVEL_APPEARANCE = 2;

    private static final float CANNON_ENEMY_BASE_SPAWN_RATE = 200;
    private static final float CANNON_ENEMY_SPAWN_RATE_MUTATION_FACTOR = 10;
    private static final float CANNON_LEVEL_APPEARANCE = 0;

    private static EnemySpawnTable instance;
    private SpawnTableCollection spawnTableCollection;

    public static EnemySpawnTable getInstance() {
        if (instance == null) {
            instance = new EnemySpawnTable();
        }
        return instance;
    }

    public EnemySpawnTable() {
        spawnTableCollection = new SpawnTableCollection();
        updateSpawnTable();
    }

    /**
     * New enemies must be added to the spawn table
     */
    public void updateSpawnTable() {
        spawnTableCollection.clear();
        int currentNLevel = GameManager.getInstance().getCurrentNLevel();
        if (currentNLevel >= DRONE_LEVEL_APPEARANCE) {
            spawnTableCollection = spawnTableCollection.add(DRONE_ENEMY_BASE_SPAWN_RATE + DRONE_ENEMY_SPAWN_RATE_MUTATION_FACTOR * (currentNLevel - DRONE_LEVEL_APPEARANCE), DroneEnemy.class);
        }
        if (currentNLevel >= PYRAMID_LEVEL_APPEARANCE) {
            spawnTableCollection = spawnTableCollection.add(PYRAMID_ENEMY_BASE_SPAWN_RATE + PYRAMID_ENEMY_SPAWN_RATE_MUTATION_FACTOR * (currentNLevel - PYRAMID_LEVEL_APPEARANCE), PyramidEnemy.class);
        }
        if (currentNLevel >= CANNON_LEVEL_APPEARANCE) {
            spawnTableCollection = spawnTableCollection.add(CANNON_ENEMY_BASE_SPAWN_RATE + CANNON_ENEMY_SPAWN_RATE_MUTATION_FACTOR * (currentNLevel - CANNON_LEVEL_APPEARANCE), CannonEnemy.class);
        }
    }

    public Class<? extends AbstractEnemy> next() {
        return spawnTableCollection.next();
    }

    public SpawnTableCollection getSpawnTableCollection() {
        return spawnTableCollection;
    }
}
