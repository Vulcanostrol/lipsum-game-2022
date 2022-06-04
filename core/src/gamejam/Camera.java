package gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import gamejam.rooms.RoomConfiguration;

public class Camera {
    SpriteBatch spriteBatch;
    float factorX = 1f;
    float factorY = 1f;
    float offsetX = 0;
    float offsetY = 0;
    float windowFactorX = 1f;
    float windowFactorY = 1f;
    float START_WIDTH = RoomConfiguration.ROOM_TILE_WIDTH*RoomConfiguration.TILE_PIXEL_WIDTH;
    float START_HEIGHT = RoomConfiguration.ROOM_TILE_HEIGHT*RoomConfiguration.TILE_PIXEL_HEIGHT;


    public Camera(){
        spriteBatch = new SpriteBatch();
    }

    public void draw(Texture sprite, float x, float y, float width, float height){
        float newX = (x+offsetX)*windowFactorX;
        float newY = (y+offsetY)*windowFactorY;
        float newWidth = width*factorX*windowFactorX;
        float newHeight = height*factorY*windowFactorY;
        spriteBatch.draw(sprite, newX, newY, newWidth, newHeight);
    }
    public void draw(Texture sprite, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY){
        float newX = (x+offsetX)*windowFactorX;
        float newY = (y+offsetY)*windowFactorY;
        float newWidth = width*factorX*windowFactorX;
        float newHeight = height*factorY*windowFactorY;
        spriteBatch.draw(sprite, newX, newY, newWidth, newHeight, srcX, srcY, srcWidth, srcHeight, flipX, flipY);

    }

    public void begin(){
        System.out.println(Gdx.graphics.getWidth());
        System.out.println(Gdx.graphics.getHeight());
        windowFactorX = Gdx.graphics.getWidth()/START_WIDTH;
        windowFactorY = Gdx.graphics.getHeight()/START_HEIGHT;
        spriteBatch.begin();
    }
    public void end(){
        spriteBatch.end();
    }
}
