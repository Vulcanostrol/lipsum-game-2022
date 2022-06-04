package gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.rooms.RoomConfiguration;

import java.util.Random;

public class Camera {
    private SpriteBatch spriteBatch;
    private float factorX = 1f;
    private float factorY = 1f;
    private float windowFactorX = 1f;
    private float windowFactorY = 1f;
    private float START_WIDTH = RoomConfiguration.ROOM_TILE_WIDTH*RoomConfiguration.TILE_PIXEL_WIDTH;
    private float START_HEIGHT = RoomConfiguration.ROOM_TILE_HEIGHT*RoomConfiguration.TILE_PIXEL_HEIGHT;

    private long timingCounter = 0;
    private long updateMillis = 50;

    //debuffs
    private Random random;
    private int shake = 10;
    private int shakeX = 0;
    private int shakeY = 0;
    private float updateFactorY = 0.998f;
    private float updateFactorX = 0.999f;

    private int movementSpeed = 500;
    private int movementX = movementSpeed;
    private int movementY = 0;
    private int movementXDirection = 1;
    private int movementYDirection = 1;
    private float movementOffsetX = 0;
    private float movementOffsetY = 0;


    public Camera(){
        spriteBatch = new SpriteBatch();
        random = new Random();
        windowFactorX = Gdx.graphics.getWidth()/START_WIDTH;
        windowFactorY = Gdx.graphics.getHeight()/START_HEIGHT;
    }

    public void draw(Texture sprite, float x, float y, float width, float height){
        float newX = (x+ movementOffsetX +shakeX)*factorX*windowFactorX;
        float newY = (y+ movementOffsetY +shakeY)*factorY*windowFactorY;
        float newWidth = width*factorX*windowFactorX;
        float newHeight = height*factorY*windowFactorY;
        spriteBatch.draw(sprite, newX, newY, newWidth, newHeight);
    }

    public void draw(Texture sprite, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY){
        float newX = (x+ movementOffsetX)*factorX*windowFactorX;
        float newY = (y+ movementOffsetY)*factorY*windowFactorY;
        float newWidth = width*factorX*windowFactorX;
        float newHeight = height*factorY*windowFactorY;
        spriteBatch.draw(sprite, newX, newY, newWidth, newHeight, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
    }

    public void draw(TextureRegion region, float x, float y, float width, float height, boolean flipX, boolean flipY){
        float newX = (x + movementOffsetX) * factorX * windowFactorX;
        float newY = (y + movementOffsetY) * factorY * windowFactorY;
        float newWidth = width * factorX * windowFactorX;
        float newHeight = height * factorY * windowFactorY;
        float newOriginX = newWidth / 2f; // Origin in middle.
        float newOriginY = newHeight; // Origin on the bottom.
        float xScale = flipX ? -1f : 1f;
        float yScale = flipY ? -1f : 1f;
        spriteBatch.draw(region, newX, newY, newOriginX, newOriginY, newWidth, newHeight, xScale, yScale, 0f);
    }

    private void updateShake(){
        shakeY = random.nextInt(2*shake)-shake;
        shakeX = random.nextInt(2*shake)-shake;
    }

    private void updateHeight(){
        factorY *= updateFactorY;
    }
    private void updateWidth(){
        factorX *= updateFactorX;
    }

    private void newMovementSpeeds(){
        movementX = random.nextInt(movementSpeed);
        movementY = movementSpeed-movementX;
    }

    private void updateMovement(long deltaTimeMillis){
        movementOffsetX += movementX*movementXDirection*deltaTimeMillis/1000;
        movementOffsetY += movementY*movementYDirection*deltaTimeMillis/1000;

        if(movementOffsetX < 0){
            movementXDirection = 1;
            newMovementSpeeds();
        } else if((START_WIDTH+ movementOffsetX)*factorX >= START_WIDTH){
            movementXDirection = -1;
            newMovementSpeeds();
        }
        if(movementOffsetY < 0){
            movementYDirection = 1;
            newMovementSpeeds();
        } else if((START_HEIGHT+ movementOffsetY)*factorY >= START_HEIGHT){
            movementYDirection = -1;
            newMovementSpeeds();
        }
    }

    private void updateLoop(){
        while(timingCounter >= updateMillis) {
//            updateShake();
//            updateHeight();
//            updateWidth();
            timingCounter -= updateMillis;
        }
    }

    public void begin(long deltaTimeMillis){
        timingCounter += deltaTimeMillis;
//        updateMovement(deltaTimeMillis);
        updateLoop();
        spriteBatch.begin();
    }
    public void end(){
        spriteBatch.end();
    }
}
