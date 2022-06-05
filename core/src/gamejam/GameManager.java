package gamejam;

import gamejam.chips.ChipManager;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.MenuChangeEvent;
import gamejam.event.events.PlayerDeathEvent;
import gamejam.event.events.ScoreEvent;
import gamejam.factories.EntityFactory;
import gamejam.factories.PlayerFactory;
import gamejam.factories.*;
import gamejam.factories.bullets.BulletFactory;
import gamejam.factories.enemies.AbstractEnemyFactory;
import gamejam.levels.Direction;
import gamejam.levels.Level;
import gamejam.config.LevelConfiguration;
import gamejam.objects.Entity;
import gamejam.objects.collidable.Player;
import gamejam.config.RoomConfiguration;
import gamejam.objects.collidable.SelfCollidable;
import gamejam.rooms.EnemySpawnTable;
import gamejam.ui.MenuManager;

import java.util.ArrayList;

public class GameManager {

    private static GameManager instance = null;
    private ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;
    private int currentNLevel;
    private long previousTime;
    private Camera camera;
    private int score;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private boolean gameActive;

    public void setupGame() {
        gameActive = true;
        score = 0;

        // Initialize a base level
        currentLevel = new Level();
        currentNLevel = 1;
        levels.clear();
        levels.add(currentLevel);

        camera = new Camera();
        //time
        previousTime = System.currentTimeMillis();

        // Entity creation
        int newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
        int newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;
        new Player(newPlayerX, newPlayerY);

        EventConsumer<PlayerDeathEvent> consumer = this::resetEntireGame;
        EventConsumer<ScoreEvent> consumerScore = this::getPoints;
        EventQueue.getInstance().registerConsumer(consumer, EventType.PLAYER_DEATH);
        EventQueue.getInstance().registerConsumer(consumerScore, EventType.SCORE_EVENT);
    }

    private void getPoints(ScoreEvent event){
        score += event.getPoints();
        System.out.println("Current score is: "+score);
    }

    public int getScore(){
        return score;
    }

    private void resetEntireGame(PlayerDeathEvent event) {
        if (gameActive) {
            ChipManager.getInstance().resetChips();
            PlayerFactory.getInstance().getPlayer().hardDisposePlayer();
            EntityFactory.getInstance().recursiveRemoveManagedObjects();
            EventQueue.getInstance().invoke(new MenuChangeEvent(MenuManager.MAIN_MENU));
            gameActive = false;
            score = 0;
        }
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
        EnemySpawnTable.getInstance().updateSpawnTable();

        // Entity creation
        int newPlayerX = RoomConfiguration.TILE_PIXEL_WIDTH * RoomConfiguration.ROOM_TILE_WIDTH / 2;
        int newPlayerY = RoomConfiguration.TILE_PIXEL_HEIGHT * RoomConfiguration.ROOM_TILE_HEIGHT / 2;
        PlayerFactory.getInstance().addManagedObject(player);
        player.setPosition(newPlayerX, newPlayerY);
    }

    public void moveToRoomByDirection(Direction direction) {
        Player player = PlayerFactory.getInstance().getPlayer();
        EntityFactory.getInstance().recursiveRemoveManagedObjects();
        boolean success = currentLevel.moveToRoomByDirection(direction);

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
