package gamejam.rooms;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.Camera;
import gamejam.TextureStore;
import gamejam.config.RoomConfiguration;

public class WallTile extends RoomTile {

    private final WallTileType wallTileType;
    private final boolean upgradeWall;

    public WallTile(WallTileType wallTileType, int minX, int maxX, int minY, int maxY, boolean upgrade) {
        super(RoomTileType.WALL, minX, maxX, minY, maxY);
        this.wallTileType = wallTileType;
        this.upgradeWall = upgrade;

        switch(wallTileType) {
            case WALL_EAST:
            case WALL_WEST:
                this.tileTexture = TextureStore.getTileTextureByName("wall_vertical" + upgradeRoomTextureSuffix());
                break;
            case WALL_SOUTH:
            case WALL_NORTH:
                this.tileTexture = TextureStore.getTileTextureByName("wall" + upgradeRoomTextureSuffix());
                break;
            case WALL_NORTHEAST:
            case WALL_NORTHWEST:
            case WALL_SOUTHEAST:
            case WALL_SOUTHWEST:
                this.tileTexture = TextureStore.getTileTextureByName("cornerwall" + upgradeRoomTextureSuffix());
                break;
            default:
                this.tileTexture = TextureStore.getTileTextureByName("bobbysoepkip");
                break;
        }
    }

    public WallTile(WallTileType wallTileType, int minX, int maxX, int minY, int maxY) {
        this(wallTileType, minX, maxX, minY, maxY, false);
    }

    @Override
    public void draw(Camera camera) {
        TextureRegion tr = new TextureRegion(tileTexture);
        switch (this.wallTileType) {
            case WALL_NORTHWEST:
            case WALL_NORTH:
            case WALL_EAST:
                break;
            case WALL_NORTHEAST:
            case WALL_WEST:
                tr.flip(true, false);
                break;
            case WALL_SOUTHEAST:
                tr.flip(true, true);
                break;
            case WALL_SOUTH:
            case WALL_SOUTHWEST:
                tr.flip(false, true);
                break;
            default:
                System.out.printf("ik weet niet wat deze wall tile type moet voorstellen..%s%n", this.wallTileType);
                return;
        }
        camera.draw(tr, minX, minY, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT, false, false);

    }

    private String upgradeRoomTextureSuffix() {
        return upgradeWall ? "_upgrade" : "";
    }
}
