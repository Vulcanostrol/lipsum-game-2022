package gamejam;

import gamejam.factories.EntityFactory;
import gamejam.factories.PlayerFactory;
import gamejam.levels.Direction;
import gamejam.levels.Level;
import gamejam.levels.LevelConfiguration;
import gamejam.objects.Entity;
import gamejam.objects.collidable.Player;
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
        Player player = PlayerFactory.getInstance().getPlayer();
        EntityFactory.getInstance().recursiveRemoveManagedObjects();
        currentLevel = new Level();
        currentNLevel += 1;
        levels.add(currentLevel);

        // Entity creation
        int newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
        int newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;
        PlayerFactory.getInstance().addManagedObject(player);
        player.setPosition(newPlayerX, newPlayerY);
    }

    public void moveToRoomByDirection(Direction direction) {
        if (currentLevel.moveToRoomByDirection(direction)) {
            Player player = PlayerFactory.getInstance().getPlayer();
            EntityFactory.getInstance().recursiveRemoveManagedObjects();
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
            PlayerFactory.getInstance().addManagedObject(player);
            player.setVelocity(0, 0);
            player.setPosition((float) newPlayerX,(float) newPlayerY);
        } else {
            System.err.println("Tried to move to an invalid room location. Staying in the current room(?)");
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

        // After draw (don't question, just believe)
        EntityFactory.getInstance().getAllManagedObjects().forEach(Entity::afterDraw);
    }

    public Camera getCamera() {
        return camera;
    }

    public int getCurrentNLevel() {
        return currentNLevel;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
