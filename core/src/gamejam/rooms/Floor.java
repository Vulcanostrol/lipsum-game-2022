package gamejam.rooms;

import com.badlogic.gdx.graphics.Texture;

public class Floor extends RoomTile {

    public Floor(int minX, int maxX, int minY, int maxY) {
        super(RoomTileType.FLOOR, minX, maxX, minY, maxY);
        this.impassible = false;
        this.texturePath = "assets/terrain/floor.png";
        this.tileTexture = new Texture(this.texturePath);
    }

}
