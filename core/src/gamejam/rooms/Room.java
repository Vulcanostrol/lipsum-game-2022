package gamejam.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Room {

    private SpriteBatch batch;
    // Assuming 1920x1080
    // MAX_TILE_WIDTH = 24;
    // MAX_TILE_HEIGHT = 13.5;
    // 0, 0 is bottom left
    private RoomTile[][] tiles = new RoomTile[RoomConfiguration.ROOM_TILE_WIDTH][RoomConfiguration.ROOM_TILE_HEIGHT];

    public Room() {
        batch = new SpriteBatch();

        // Setup base room tiles
        int max_tile_x = tiles.length - 1;
        int max_tile_y = tiles[0].length - 1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int minX = i * RoomConfiguration.TILE_PIXEL_WIDTH;
                int maxX = (i + 1) * RoomConfiguration.TILE_PIXEL_WIDTH;
                int minY = j * RoomConfiguration.TILE_PIXEL_HEIGHT;
                int maxY = (j + 1) * RoomConfiguration.TILE_PIXEL_HEIGHT;

                if (i == 0 && j == 0) {
                    tiles[i][j] = new Wall(WallTileType.WALL_SOUTHWEST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == 0 && j == max_tile_y) {
                    tiles[i][j] = new Wall(WallTileType.WALL_NORTHWEST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == max_tile_x && j == 0) {
                    tiles[i][j] = new Wall(WallTileType.WALL_SOUTHEAST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == max_tile_x && j == max_tile_y) {
                    tiles[i][j] = new Wall(WallTileType.WALL_NORTHEAST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == 0) {
                    tiles[i][j] = new Wall(WallTileType.WALL_WEST, minX, maxX, minY, maxY);
                    continue;
                }
                if (i == max_tile_x) {
                    tiles[i][j] = new Wall(WallTileType.WALL_EAST, minX, maxX, minY, maxY);
                    continue;
                }
                if (j == 0) {
                    tiles[i][j] = new Wall(WallTileType.WALL_SOUTH, minX, maxX, minY, maxY);
                    continue;
                }
                if (j == max_tile_y) {
                    tiles[i][j] = new Wall(WallTileType.WALL_NORTH, minX, maxX, minY, maxY);
                    continue;
                }
                tiles[i][j] = new Floor(minX, maxX, minY, maxY);
            }
        }

        // After this one can choose how to randomly instantiate rest objects in the room
    }

    public void draw() {
        for (RoomTile[] roomTiles : this.tiles) {
            for (RoomTile roomTile : roomTiles) {
                roomTile.draw(batch);
            }
        }
    }






}
