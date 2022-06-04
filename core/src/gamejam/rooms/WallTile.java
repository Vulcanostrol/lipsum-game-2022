package gamejam.rooms;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.TextureStore;

public class WallTile extends RoomTile {

    private WallTileType wallTileType;

    public WallTile(WallTileType wallTileType, int minX, int maxX, int minY, int maxY) {
        super(RoomTileType.WALL, minX, maxX, minY, maxY);
        this.wallTileType = wallTileType;

        switch(wallTileType) {
            case WALL_EAST:
            case WALL_WEST:
                this.tileTexture = TextureStore.getTileTextureByName("wall_vertical");
                break;
            case WALL_SOUTH:
            case WALL_NORTH:
                this.tileTexture = TextureStore.getTileTextureByName("wall");
                break;
            case WALL_NORTHEAST:
            case WALL_NORTHWEST:
            case WALL_SOUTHEAST:
            case WALL_SOUTHWEST:
                this.tileTexture = TextureStore.getTileTextureByName("cornerwall");
                break;
            case SOUTH_DOOR:
                this.tileTexture = TextureStore.getTileTextureByName("southdoor");
                break;
            case NORTH_DOOR:
                this.tileTexture = TextureStore.getTileTextureByName("northdoor");
                break;
            case EAST_DOOR:
                this.tileTexture = TextureStore.getTileTextureByName("eastdoor");
                break;
            case WEST_DOOR:
                this.tileTexture = TextureStore.getTileTextureByName("westdoor");
                break;
            default:
                this.tileTexture = TextureStore.getTileTextureByName("bobbysoepkip");
                break;
        }
    }

    @Override
    public void draw(Camera camera) {
        TextureRegion tr = new TextureRegion(tileTexture);
        switch (this.wallTileType) {
            case WALL_NORTHWEST:
            case WALL_NORTH:
            case WALL_EAST:
                camera.draw(tileTexture, minX, minY, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT);
                break;
            case WALL_NORTHEAST:
            case WALL_SOUTH:
                camera.draw(tr, minX, minY, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, true, false);
                break;
            case WALL_WEST:
                camera.draw(tr, minX, minY, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, false, true);
                break;
            default:
                System.out.printf("ik weet niet wat deze wall tile type moet voorstellen..%s%n", this.wallTileType);
        }

    }
}
