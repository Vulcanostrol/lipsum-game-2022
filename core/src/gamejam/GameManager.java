package gamejam;

import gamejam.factories.EntityFactory;
import gamejam.levels.Direction;
import gamejam.levels.Level;
import gamejam.levels.LevelConfiguration;
import gamejam.objects.collidable.Player;
import gamejam.objects.collidable.TestEntity;
import gamejam.objects.collidable.enemies.DroneEnemy;
import gamejam.rooms.RoomConfiguration;

import java.util.ArrayList;

public class GameManager {

    private static GameManager instance = null;
    private ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;
    private int currentNLevel;
    private long previousTime;
    private Camera camera;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private boolean gameActive;

    public void setupGame() {
        gameActive = true;

        // Initialize a base level
        currentLevel = new Level();
        currentNLevel = 1;
        levels.add(currentLevel);

        camera = new Camera();
        //time
        previousTime = System.currentTimeMillis();

        // Entity creation
        int newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
        int newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;
        new Player(newPlayerX, newPlayerY);
    }

    public void spawnEnemies() {
        float currentSpawnRate = LevelConfiguration.SPAWN_RATE * currentNLevel * LevelConfiguration.SPAWN_RATE_GROWTH;
        if (currentLevel != null) {
            currentLevel.spawnEnemies(currentSpawnRate);
        }
    }

    public void moveToNextLevel() {
        EntityFactory.getInstance().recursiveRemoveManagedObjects();
        currentLevel = new Level();
        currentNLevel += 1;
        levels.add(currentLevel);

        // Entity creation
        int newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
        int newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;
        new Player(newPlayerX, newPlayerY);
    }

    public void moveToRoomByDirection(Direction direction) {
        EntityFactory.getInstance().recursiveRemoveManagedObjects();
        boolean success = currentLevel.moveToRoomByDirection(direction);

//        PlayerFactory.getInstance().removeManagedObjects();
//        TestEntityFactory.getInstance().removeManagedObjects();

        if (success) {
            double newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
            double newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;

            switch (direction) {
                case EAST:
                    newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * 1.5;
                    newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2 - RoomConfiguration.TILE_PIXEL_HEIGHT/2;
                    break;
                case WEST:
                    newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * (RoomConfiguration.ROOM_TILE_WIDTH - 1.5);
                    newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2 - RoomConfiguration.TILE_PIXEL_HEIGHT/2;
                    break;
                case NORTH:
                    newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2 - RoomConfiguration.TILE_PIXEL_WIDTH/2;
                    newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * 1;
                    break;
                case SOUTH:
                    newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2 - RoomConfiguration.TILE_PIXEL_WIDTH/2;
                    newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * (RoomConfiguration.ROOM_TILE_HEIGHT - 2);
                    break;
            }

            // Entity creation
            Player player = new Player((float) newPlayerX,(float) newPlayerY);
        }
    }

    public void draw() {
        if (!gameActive) return;


        // Update
        long newTime = System.currentTimeMillis();
        long deltaTimeMillis = newTime - previousTime;
        EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.update(deltaTimeMillis));
        camera.begin(deltaTimeMillis);
        previousTime = newTime;

        //Draw
        currentLevel.render(camera);
        EntityFactory.getInstance().getAllManagedObjects().forEach(e -> e.draw(camera));
        camera.end();
    }

    public Camera getCamera() {
        return camera;
    }

    public int getCurrentNLevel() {
        return currentNLevel;
    }
}
