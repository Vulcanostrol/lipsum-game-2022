package gamejam.levels;

import gamejam.Camera;
import gamejam.Util;
import gamejam.config.LevelConfiguration;
import gamejam.factories.WallFactory;
import gamejam.objects.collidable.Wall;
import gamejam.rooms.Room;
import gamejam.config.RoomConfiguration;

import java.util.List;

public class Level {


    // +2 so we have some margins in case we have a very rare straight line generation
    private int levelHeight = LevelConfiguration.N_ROOMS * 2 + 2;
    private int levelWidth = LevelConfiguration.N_ROOMS * 2 + 2;

    public Room[][] rooms = new Room[levelWidth][levelHeight];

    private Room currentRoom;

    public Level() {
        int initX = Math.round(levelWidth / 2);
        int initY = Math.round(levelHeight / 2);

        // Create a base room
        currentRoom = new Room(this, initX, initY);
        rooms[initX][initY] = currentRoom;

        // Initialize wall collision boxes so a player can't move out of the map
        initializeWallCollisionBoxes();

        // 4 For the number of directions one can branch, for the initial room we can branch in every direction
        List<Integer> branchNs = Util.n_random(LevelConfiguration.N_ROOMS - 1, 4);

        // Branch from here
        int nEastRooms = branchNs.get(0);
        currentRoom.eastRoom = new Room(this,initX + 1, initY);
        rooms[initX + 1][initY] = currentRoom.eastRoom;
        nEastRooms -= 1;

        int nWestRooms = branchNs.get(1);
        currentRoom.westRoom = new Room(this, initX - 1, initY);
        rooms[initX - 1][initY] = currentRoom.westRoom;
        nWestRooms -= 1;

        int nNorthRooms = branchNs.get(2);
        currentRoom.northRoom = new Room(this, initX, initY + 1);
        rooms[initX][initY + 1] = currentRoom.northRoom;
        nNorthRooms -= 1;

        int nSouthRooms = branchNs.get(3);
        currentRoom.southRoom = new Room(this, initX, initY - 1);
        rooms[initX][initY - 1] = currentRoom.southRoom;
        nSouthRooms -= 1;

        currentRoom.eastRoom.createBranches(nEastRooms, Direction.EAST);
        currentRoom.westRoom.createBranches(nWestRooms, Direction.WEST);
        currentRoom.northRoom.createBranches(nNorthRooms, Direction.NORTH);
        currentRoom.southRoom.createBranches(nSouthRooms, Direction.SOUTH);

        Room outerRoom = getOuterRoom();

        outerRoom.setFinalRoom();

        currentRoom.setup(true);
        currentRoom.isUpgradeRoom = false;
    }

    /**
     * Get room farthest from the center.
     */
    public Room getOuterRoom() {
        Room currentOuterRoom = currentRoom;
        int distanceCurrentOuterRoom = 1;

        int centerX = Math.round(rooms.length / 2);
        int centerY = Math.round(rooms[0].length / 2);
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[0].length; j++) {
                Room _currentRoom = rooms[i][j];
                if (_currentRoom != null) {
                    int xDist = Math.abs(centerX - i);
                    int yDist = Math.abs(centerY - j);
                    int manhattanDist = xDist + yDist;

                    if (manhattanDist > distanceCurrentOuterRoom) {
                        distanceCurrentOuterRoom = manhattanDist;
                        currentOuterRoom = _currentRoom;
                    }
                }
            }
        }
        return currentOuterRoom;
    }

    public boolean moveToRoomByDirection(Direction direction) {
        initializeWallCollisionBoxes();
        Room newRoom = null;
        switch (direction) {
            case SOUTH:
                newRoom = rooms[currentRoom.levelX][currentRoom.levelY - 1];
                break;
            case NORTH:
                newRoom = rooms[currentRoom.levelX][currentRoom.levelY + 1];
                break;
            case EAST:
                newRoom = rooms[currentRoom.levelX + 1][currentRoom.levelY];
                break;
            case WEST:
                newRoom = rooms[currentRoom.levelX - 1][currentRoom.levelY];
                break;
        }
        if (newRoom != null) {
            currentRoom = newRoom;
            currentRoom.setup(false);
            return true;
        } else {
            System.err.println("Trying to move to a location where there is no room! Direction: " + direction);
            return false;
        }
    }

    public void spawnEnemies(float currentSpawnRate) {
        currentRoom.spawnEnemies(currentSpawnRate);
    }

    private void initializeWallCollisionBoxes() {
        int PIXEL_CHIP = 20;
        WallFactory.getInstance().removeManagedObjects();

        // South wall
        new Wall(
                RoomConfiguration.ROOM_TILE_WIDTH * RoomConfiguration.TILE_PIXEL_WIDTH / 2,
                0,
                RoomConfiguration.ROOM_TILE_WIDTH * RoomConfiguration.TILE_PIXEL_WIDTH,
                RoomConfiguration.TILE_PIXEL_HEIGHT - PIXEL_CHIP);

        // North wall
        new Wall(
                RoomConfiguration.ROOM_TILE_WIDTH * RoomConfiguration.TILE_PIXEL_WIDTH / 2,
                (RoomConfiguration.ROOM_TILE_HEIGHT - 1) * RoomConfiguration.TILE_PIXEL_HEIGHT + PIXEL_CHIP,
                RoomConfiguration.ROOM_TILE_WIDTH * RoomConfiguration.TILE_PIXEL_WIDTH,
                RoomConfiguration.TILE_PIXEL_HEIGHT - PIXEL_CHIP);

        // West wall
       new Wall(
                RoomConfiguration.TILE_PIXEL_WIDTH / 2 - PIXEL_CHIP / 2,
                0,
                RoomConfiguration.TILE_PIXEL_WIDTH - PIXEL_CHIP,
                RoomConfiguration.ROOM_TILE_HEIGHT * RoomConfiguration.TILE_PIXEL_HEIGHT);

        // East wall
       new Wall(
                (RoomConfiguration.ROOM_TILE_WIDTH - 1) * RoomConfiguration.TILE_PIXEL_WIDTH + RoomConfiguration.TILE_PIXEL_WIDTH / 2 + PIXEL_CHIP / 2,
                0,
                RoomConfiguration.TILE_PIXEL_WIDTH - PIXEL_CHIP,
                RoomConfiguration.ROOM_TILE_HEIGHT * RoomConfiguration.TILE_PIXEL_HEIGHT);
    }

    public void render(Camera camera) {
        currentRoom.draw(camera);
    }

    public void printLevelLayout() {
        for (int i = 0; i < this.rooms.length; i++) {
            for (int j = 0; j < this.rooms[0].length; j++) {
                System.out.print(this.rooms[i][j]);
            }
            System.out.println("");
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}
