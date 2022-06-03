package gamejam.rooms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RoomTile {

    protected RoomTileType roomTileType;
    protected boolean impassible;
    protected String texturePath;
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

    public void draw(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        // TODO: Unmagic these numbers
        batch.draw(tileTexture, minX, minY, 80, 80);
    }

}
