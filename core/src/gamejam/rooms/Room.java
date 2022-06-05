package gamejam.rooms;

import gamejam.Camera;
import gamejam.GameManager;
import gamejam.Util;
import gamejam.config.RoomConfiguration;
import gamejam.factories.CollidableFactory;
import gamejam.levels.Direction;
import gamejam.levels.Level;
import gamejam.config.LevelConfiguration;
import gamejam.objects.collidable.Collidable;
import gamejam.objects.collidable.Door;
import gamejam.objects.collidable.FinalDoor;
import gamejam.objects.collidable.Pillar;
import gamejam.objects.collidable.enemies.AbstractEnemy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Room {
    // Assuming 1920x1080
    // MAX_TILE_WIDTH = 24;
    // MAX_TILE_HEIGHT = 13.5;
    // 0, 0 is bottom left
    private RoomTile[][] tiles = new RoomTile[RoomConfiguration.ROOM_TILE_WIDTH][RoomConfiguration.ROOM_TILE_HEIGHT];

    private boolean[][] pillars = new boolean[RoomConfiguration.ROOM_TILE_WIDTH][RoomConfiguration.ROOM_TILE_HEIGHT];

    public int levelX;
    public int levelY;

    public Room northRoom;
    public Room southRoom;
    public Room eastRoom;
    public Room westRoom;

    public boolean visited;
    public boolean cleared;
    public boolean isUpgradeRoom;

    private boolean isFinalRoom;

    private static Random random = new Random(LevelConfiguration.SEED);

    private final Level levelParent;

    public Room(Level levelParent, int levelX, int levelY) {
        if (new Random().nextFloat() < LevelConfiguration.UPGRADE_ROOM_CHANCE) {
            isUpgradeRoom = true;
        }

        this.levelX = levelX;
        this.levelY = levelY;
        this.levelParent = levelParent;

        this.updateNeighbourRooms();

        int i = 0;
        while(i<RoomConfiguration.MAX_PILLARS){
            int x = random.nextInt(RoomConfiguration.ROOM_TILE_WIDTH-4)+2;
            int y = random.nextInt(RoomConfiguration.ROOM_TILE_HEIGHT-4)+2;
            int cnt = 0;
            int dx = -1;
            while(dx<=1){
                int dy = -1;
                while(dy<=1){
                    if(pillars[x+dx][y+dy]){
                        cnt+=1;
                    }
                    dy++;
                }
                dx++;
            }
            i++;
            if(cnt <= 1 && !pillars[x][y]){
                pillars[x][y]=true;
            }
        }
    }

    public void updateNeighbourRooms() {
        eastRoom = levelParent.rooms[levelX + 1][levelY];
        westRoom = levelParent.rooms[levelX - 1][levelY];
        northRoom = levelParent.rooms[levelX][levelY + 1];
        southRoom = levelParent.rooms[levelX][levelY - 1];
    }

    public void grow(int nNewRoomsLeft, Direction growthDirection) {
        if (growthDirection == Direction.EAST) {
            if (nNewRoomsLeft > 0) {
                this.eastRoom = new Room(levelParent, levelX + 1, levelY);
                levelParent.rooms[levelX + 1][levelY] = eastRoom;
                nNewRoomsLeft -= 1;
                this.eastRoom.createBranches(nNewRoomsLeft, growthDirection);
            }
            return;
        }

        if (growthDirection == Direction.WEST) {
            if (nNewRoomsLeft > 0) {
                this.westRoom = new Room(levelParent, levelX - 1, levelY);
                levelParent.rooms[levelX - 1][levelY] = westRoom;
                nNewRoomsLeft -= 1;
                this.westRoom.createBranches(nNewRoomsLeft, growthDirection);
            }
        }

        if (growthDirection == Direction.NORTH) {
            if (nNewRoomsLeft > 0) {
                this.northRoom = new Room(levelParent, levelX, levelY + 1);
                levelParent.rooms[levelX][levelY + 1] = northRoom;
                nNewRoomsLeft -= 1;
                this.northRoom.createBranches(nNewRoomsLeft, growthDirection);
            }
        }

        if (growthDirection == Direction.SOUTH) {
            if (nNewRoomsLeft > 0) {
                this.southRoom = new Room(levelParent, levelX, levelY - 1);
                levelParent.rooms[levelX][levelY - 1] = southRoom;
                nNewRoomsLeft -= 1;
                this.southRoom.createBranches(nNewRoomsLeft, growthDirection);
            }
        }
    }

    public ArrayList<Direction> getPossibleBranchDirections() {
        ArrayList<Direction> result = new ArrayList<>();

        if (eastRoom == null) {
            result.add(Direction.EAST);
        }
        if (westRoom == null) {
            result.add(Direction.WEST);
        }
        if (northRoom == null) {
            result.add(Direction.NORTH);
        }
        if (southRoom == null) {
            result.add(Direction.SOUTH);
        }

        return result;
    }

    public void createBranches(int nNewRoomsLeft, Direction growthDirection) {
        if (nNewRoomsLeft <= 0) {
            return;
        }

        List<Direction> possibleBranchDirections = getPossibleBranchDirections();

        // 80% chance to continue in current direction
        if (random.nextFloat() > (1 - LevelConfiguration.STRAIGHTNESS_FACTOR)) {
            if (possibleBranchDirections.contains(growthDirection)) {
                grow(nNewRoomsLeft, growthDirection);
            } else {
                getRoomByDirection(growthDirection).grow(nNewRoomsLeft, growthDirection);
            }
        } else {
            List<Integer> branchNs = Util.n_random(nNewRoomsLeft, possibleBranchDirections.size());

            if (possibleBranchDirections.contains(Direction.EAST)) {
                int newNs = branchNs.get(0);
                branchNs.remove(0);
                if (newNs > 0) {
                    this.eastRoom = new Room(levelParent, levelX + 1, levelY);
                    levelParent.rooms[levelX + 1][levelY] = eastRoom;
                    newNs -= 1;
                    this.eastRoom.createBranches(newNs, Direction.EAST);
                }
            }

            if (possibleBranchDirections.contains(Direction.WEST)) {
                int newNs = branchNs.get(0);
                branchNs.remove(0);
                if (newNs > 0) {
                    this.westRoom = new Room(levelParent, levelX - 1, levelY);
                    levelParent.rooms[levelX - 1][levelY] = westRoom;
                    newNs -= 1;
                    this.westRoom.createBranches(newNs, Direction.WEST);
                }
            }

            if (possibleBranchDirections.contains(Direction.NORTH)) {
                int newNs = branchNs.get(0);
                branchNs.remove(0);
                if (newNs > 0) {
                    this.northRoom = new Room(levelParent, levelX, levelY + 1);
                    levelParent.rooms[levelX][levelY + 1] = northRoom;
                    newNs -= 1;
                    this.northRoom.createBranches(newNs, Direction.NORTH);
                }
            }

            if (possibleBranchDirections.contains(Direction.SOUTH)) {
                int newNs = branchNs.get(0);
                branchNs.remove(0);
                if (newNs > 0) {
                    this.southRoom = new Room(levelParent, levelX, levelY - 1);
                    levelParent.rooms[levelX][levelY - 1] = southRoom;
                    newNs -= 1;
                    this.southRoom.createBranches(newNs, Direction.SOUTH);
                }
            }
        }
    }

    public void setup() {
        this.updateNeighbourRooms();
        Direction finalDoorDirection = null;
        if (isFinalRoom) {
            List<Direction> possibleDirections = getPossibleBranchDirections();
            finalDoorDirection = possibleDirections.get(new Random().nextInt(possibleDirections.size()));
        }

        // Setup base room tiles
        int max_tile_x = tiles.length - 1;
        int max_tile_y = tiles[0].length - 1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int minX = i * RoomConfiguration.TILE_PIXEL_WIDTH;
                int maxX = (i + 1) * RoomConfiguration.TILE_PIXEL_WIDTH;
                int minY = j * RoomConfiguration.TILE_PIXEL_HEIGHT;
                int maxY = (j + 1) * RoomConfiguration.TILE_PIXEL_HEIGHT;
                if(pillars[i][j]){
                    new Pillar(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY);
                }

                if (i == 0 && j == Math.round((max_tile_y / 2))) {
                    // West door
                    if (westRoom != null) {
                        new Door(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.WEST, westRoom.isUpgradeRoom);
                    }
                    if (finalDoorDirection == Direction.WEST) {
                        new FinalDoor(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.WEST);
                    }
                }


                if (i == max_tile_x && j == Math.round((max_tile_y / 2))) {
                    // East door
                    if (eastRoom != null) {
                        new Door(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.EAST, eastRoom.isUpgradeRoom);
                    }
                    if (finalDoorDirection == Direction.EAST) {
                        new FinalDoor(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.EAST);
                    }
                }

                if (i == Math.round((max_tile_x / 2)) && j == 0) {
                    // South door
                    if (southRoom != null) {
                        new Door(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.SOUTH, southRoom.isUpgradeRoom);
                    }
                    if (finalDoorDirection == Direction.SOUTH) {
                        new FinalDoor(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.SOUTH);
                    }
                }

                if (i == Math.round((max_tile_x / 2)) && j == max_tile_y) {
                    // North door
                    if (northRoom != null) {
                        new Door(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.NORTH, northRoom.isUpgradeRoom);
                    }
                    if (finalDoorDirection == Direction.NORTH) {
                        new FinalDoor(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.NORTH);
                    }
                }

                if (i == 0 && j == 0) {
                    tiles[i][j] = new WallTile(WallTileType.WALL_SOUTHWEST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == 0 && j == max_tile_y) {
                    tiles[i][j] = new WallTile(WallTileType.WALL_NORTHWEST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == max_tile_x && j == 0) {
                    tiles[i][j] = new WallTile(WallTileType.WALL_SOUTHEAST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == max_tile_x && j == max_tile_y) {
                    tiles[i][j] = new WallTile(WallTileType.WALL_NORTHEAST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == 0) {
                    tiles[i][j] = new WallTile(WallTileType.WALL_WEST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == max_tile_x) {
                    tiles[i][j] = new WallTile(WallTileType.WALL_EAST, minX, maxX, minY, maxY);
                    continue;
                }
                if (j == 0) {
                    tiles[i][j] = new WallTile(WallTileType.WALL_SOUTH, minX, maxX, minY, maxY);
                    continue;
                }
                if (j == max_tile_y) {
                    tiles[i][j] = new WallTile(WallTileType.WALL_NORTH, minX, maxX, minY, maxY);
                    continue;
                }
                tiles[i][j] = new FloorTile(minX, maxX, minY, maxY);
            }
        }

        // After this one can choose how to randomly instantiate rest objects in the room
        if (!visited) {
            visited = true;
            // TODO: Implement initializing and storing objects in the room so they are remembered on next visit
            GameManager.getInstance().spawnEnemies();
        }
    }

    public void spawnEnemies(float currentSpawnRate) {
        for (int i = 1; i < tiles.length - 1; i++) {
            for (int j = 1; j < tiles[0].length - 1; j++) {
                if (pillars[i][j]) {
                    continue;
                }

                int xOffset = Math.round(random.nextFloat() * RoomConfiguration.TILE_PIXEL_WIDTH / 2) - RoomConfiguration.TILE_PIXEL_WIDTH / 2;
                int yOffset = Math.round(random.nextFloat() * RoomConfiguration.TILE_PIXEL_HEIGHT / 2) - RoomConfiguration.TILE_PIXEL_HEIGHT / 2;

                if (random.nextFloat() < currentSpawnRate) {
                    Class<? extends AbstractEnemy> cls = EnemySpawnTable.getInstance().next();
                    AbstractEnemy potentialNewEnemy = null;
                    try {
                        float spawnX = (float) i * RoomConfiguration.TILE_PIXEL_WIDTH + xOffset;
                        float spawnY = (float) j * RoomConfiguration.TILE_PIXEL_HEIGHT + yOffset;
                        potentialNewEnemy = cls.getDeclaredConstructor(float.class, float.class).newInstance(spawnX, spawnY);
                        AbstractEnemy finalPotentialNewEnemy = potentialNewEnemy;
                        Stream<Collidable> collidedEnemies = CollidableFactory.getInstance().getAllManagedObjects().filter(collidable -> collidable.checkCollision(finalPotentialNewEnemy));
                        if (collidedEnemies.count() >= 2) {
                            potentialNewEnemy.despawn();
                        }
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public void draw(Camera camera) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].draw(camera);
            }
        }
    }

    public Room getRoomByDirection(Direction direction) {
        switch(direction) {
            case EAST:
                return eastRoom;
            case WEST:
                return westRoom;
            case NORTH:
                return northRoom;
            case SOUTH:
                return southRoom;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(levelX) + ", " + String.valueOf(levelY) + ")";
    }

    public void setFinalRoom() {
        isFinalRoom = true;
    }
}
