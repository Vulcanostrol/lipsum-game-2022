package gamejam.rooms;

import gamejam.GameManager;
import gamejam.objects.collidable.enemies.AbstractEnemy;
import gamejam.objects.collidable.enemies.DroneEnemy;
import gamejam.objects.collidable.enemies.PyramidEnemy;

import java.util.ArrayList;
import java.util.HashMap;

public class EnemySpawnTable {

    private static EnemySpawnTable instance;

//    private HashMap<AbstractEnemy, Integer> spawnTable;
    private ArrayList<Class<? extends AbstractEnemy>> spawnTable;


    public static EnemySpawnTable getInstance() {
        if (instance == null) {
            instance = new EnemySpawnTable();
        }
        return instance;
    }

    public EnemySpawnTable() {
//        spawnTable = new HashMap<>();
        spawnTable = new ArrayList<>();
        populateSpawnTable();
    }

    public void populateSpawnTable() {
//        int currentNLevel = GameManager.getInstance().getCurrentNLevel();
        spawnTable.add(DroneEnemy.class);
        spawnTable.add(PyramidEnemy.class);
    }

    public ArrayList<Class<? extends AbstractEnemy>> getSpawnTable() {
        return spawnTable;
    }
}
