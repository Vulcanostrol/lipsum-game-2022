package gamejam.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.Util;
import gamejam.factories.DoorFactory;
import gamejam.factories.WallFactory;
import gamejam.levels.Direction;
import gamejam.levels.Level;
import gamejam.levels.LevelConfiguration;
import gamejam.objects.Wall;
import gamejam.objects.collidable.Door;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {

    private SpriteBatch batch;
    // Assuming 1920x1080
    // MAX_TILE_WIDTH = 24;
    // MAX_TILE_HEIGHT = 13.5;
    // 0, 0 is bottom left
    private RoomTile[][] tiles = new RoomTile[RoomConfiguration.ROOM_TILE_WIDTH][RoomConfiguration.ROOM_TILE_HEIGHT];

    public int levelX;
    public int levelY;

    public Room northRoom;
    public Room southRoom;
    public Room eastRoom;
    public Room westRoom;

    public boolean visited;

    private static Random random = new Random(LevelConfiguration.SEED);

    private final Level levelParent;

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

    public void createBranches(int nNewRoomsLeft, Direction growthDirection) {
        if (nNewRoomsLeft <= 0) {
            return;
        }

        List<Direction> possibleBranchDirections = new ArrayList<>();

        if (eastRoom == null) {
            possibleBranchDirections.add(Direction.EAST);
        }
        if (westRoom == null) {
            possibleBranchDirections.add(Direction.WEST);
        }
        if (northRoom == null) {
            possibleBranchDirections.add(Direction.NORTH);
        }
        if (southRoom == null) {
            possibleBranchDirections.add(Direction.SOUTH);
        }

        // 80% chance to continue in current direction
        // TODO: Check if there are no rooms blocking
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
        // Setup base room tiles
        int max_tile_x = tiles.length - 1;
        int max_tile_y = tiles[0].length - 1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int minX = i * RoomConfiguration.TILE_PIXEL_WIDTH;
                int maxX = (i + 1) * RoomConfiguration.TILE_PIXEL_WIDTH;
                int minY = j * RoomConfiguration.TILE_PIXEL_HEIGHT;
                int maxY = (j + 1) * RoomConfiguration.TILE_PIXEL_HEIGHT;

                if (i == 0 && j == Math.round((max_tile_y / 2)) && westRoom != null) {
                    // West door
//                    tiles[i][j] = new Wall(WallTileType.WEST_DOOR, minX, maxX, minY, maxY);
                    DoorFactory.getInstance().addManagedObject(new Door(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.WEST));
                }

                if (i == max_tile_x && j == Math.round((max_tile_y / 2)) && eastRoom != null) {
                    // East door
//                    tiles[i][j] = new Wall(WallTileType.EAST_DOOR, minX, maxX, minY, maxY);
                    DoorFactory.getInstance().addManagedObject(new Door(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.EAST));
                }

                if (i == Math.round((max_tile_x / 2)) && j == 0 && southRoom != null) {
                    // South door
//                    tiles[i][j] = new Wall(WallTileType.SOUTH_DOOR, minX, maxX, minY, maxY);
                    DoorFactory.getInstance().addManagedObject(new Door(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.SOUTH));
                }

                if (i == Math.round((max_tile_x / 2)) && j == max_tile_y && northRoom != null) {
                    // North door
//                    tiles[i][j] = new Wall(WallTileType.NORTH_DOOR, minX, maxX, minY, maxY);
                    DoorFactory.getInstance().addManagedObject(new Door(minX + RoomConfiguration.TILE_PIXEL_WIDTH / 2, minY, Direction.NORTH));
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
                tiles[i][j] = new Floor(minX, maxX, minY, maxY);
            }
        }

        // After this one can choose how to randomly instantiate rest objects in the room
        if (!visited) {
            visited = true;
            // TODO: Implement initializing and storing objects in the room so they are remembered on next visit
        }
    }

    public Room(Level levelParent, int levelX, int levelY) {
        batch = new SpriteBatch();
        this.levelX = levelX;
        this.levelY = levelY;
        this.levelParent = levelParent;

        eastRoom = levelParent.rooms[levelX + 1][levelY];
        westRoom = levelParent.rooms[levelX - 1][levelY];
        northRoom = levelParent.rooms[levelX][levelY + 1];
        southRoom = levelParent.rooms[levelX][levelY - 1];
    }

    public void draw() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].draw(batch);
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

}
