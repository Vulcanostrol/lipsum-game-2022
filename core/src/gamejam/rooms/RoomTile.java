package gamejam.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.Camera;
import org.w3c.dom.Text;

public class RoomTile {

    protected RoomTileType roomTileType;
    protected boolean impassible;
    protected Texture tileTexture;

    protected int minX;
    protected int maxX;
    protected int minY;
    protected int maxY;

    public RoomTile(RoomTileType roomTileType, int minX, int maxX, int minY, int maxY) {
        this.roomTileType = roomTileType;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void draw(Camera camera) {
        camera.draw(tileTexture, minX, minY, RoomConfiguration.TILE_PIXEL_WIDTH, RoomConfiguration.TILE_PIXEL_HEIGHT);
    }

}
