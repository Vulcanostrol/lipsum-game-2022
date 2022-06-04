package gamejam.rooms;

import gamejam.TextureStore;

public class FloorTile extends RoomTile {

    public FloorTile(int minX, int maxX, int minY, int maxY) {
        super(RoomTileType.FLOOR, minX, maxX, minY, maxY);
        this.impassible = false;
        this.tileTexture = TextureStore.getTileTextureByName("floor");
    }

}
