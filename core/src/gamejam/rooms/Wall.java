package gamejam.rooms;

import gamejam.TextureStore;

public class Wall extends RoomTile {

    private WallTileType wallTileType;

    public Wall(WallTileType wallTileType, int minX, int maxX, int minY, int maxY) {
        super(RoomTileType.WALL, minX, maxX, minY, maxY);
        this.wallTileType = wallTileType;

        switch(wallTileType) {
            case WALL_EAST:
                this.tileTexture = TextureStore.getTileTextureByName("eastwall");
                break;
            case WALL_WEST:
                this.tileTexture = TextureStore.getTileTextureByName("westwall");
                break;
            case WALL_SOUTH:
                this.tileTexture = TextureStore.getTileTextureByName("southwall");
                break;
            case WALL_NORTH:
                this.tileTexture = TextureStore.getTileTextureByName("northwall");
                break;
            case WALL_NORTHEAST:
                this.tileTexture = TextureStore.getTileTextureByName("northeastwall");
                break;
            case WALL_NORTHWEST:
                this.tileTexture = TextureStore.getTileTextureByName("northwestwall");
                break;
            case WALL_SOUTHEAST:
                this.tileTexture = TextureStore.getTileTextureByName("southeastwall");
                break;
            case WALL_SOUTHWEST:
                this.tileTexture = TextureStore.getTileTextureByName("southwestwall");
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


}
