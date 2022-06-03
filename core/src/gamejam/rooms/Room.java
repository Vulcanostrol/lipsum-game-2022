package gamejam.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Room {

    private final int TILE_PIXEL_WIDTH = 80;
    private final int TILE_PIXEL_HEIGHT = 80;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    // Assuming 1920x1080
    // MAX_TILE_WIDTH = 24;
    // MAX_TILE_HEIGHT = 13.5;
    // 0, 0 is bottom left
    private RoomTile[][] tiles = new RoomTile[24][13];

    public Room() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Setup base room tiles
        int max_tile_x = tiles.length - 1;
        int max_tile_y = tiles[0].length - 1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                int minX = i * TILE_PIXEL_WIDTH;
                int maxX = (i + 1) * TILE_PIXEL_WIDTH;
                int minY = j * TILE_PIXEL_HEIGHT;
                int maxY = (j + 1) * TILE_PIXEL_HEIGHT;

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
                    tiles[i][j] = new Wall(WallTileType.WALL_EAST, minX, maxX, minY, maxY);
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
                roomTile.draw(batch, shapeRenderer);
            }
        }
    }






}
