package gamejam.rooms;

import com.badlogic.gdx.graphics.Texture;
import gamejam.TextureStore;

public class Floor extends RoomTile {

    public Floor(int minX, int maxX, int minY, int maxY) {
        super(RoomTileType.FLOOR, minX, maxX, minY, maxY);
        this.impassible = false;
        this.tileTexture = TextureStore.getTileTextureByName("floor");
    }

}
