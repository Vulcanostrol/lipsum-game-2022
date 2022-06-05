package gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import gamejam.config.RoomConfiguration;
import gamejam.event.events.MouseEvent;

import java.util.Random;

public class Camera {
    private SpriteBatch spriteBatch;

    private float windowFactorX = 1f;

    private float windowFactorY = 1f;
    private float START_WIDTH = RoomConfiguration.ROOM_TILE_WIDTH*RoomConfiguration.TILE_PIXEL_WIDTH;
    private float START_HEIGHT = RoomConfiguration.ROOM_TILE_HEIGHT*RoomConfiguration.TILE_PIXEL_HEIGHT;
    private long timingCounter = 0;

    private long updateMillis = 50;

    //debuffs

    public static final int SHAKE_START = 10;

    private Random random;

    private int currentShake = 0;
    private int shake = SHAKE_START;
    private int shakeX = 0;
    private int shakeY = 0;

    private float factorX = 1f;
    private float factorY = 1f;
    private float updateFactorY = 1f;
    private float updateFactorX = 1f;

    private int movementSpeed = 0;
    private int movementX = movementSpeed;
    private int movementY = 0;
    private int movementXDirection = 1;
    private int movementYDirection = 1;
    private float movementOffsetX = 0;
    private float movementOffsetY = 0;

    private boolean flipX = false;

    public Camera() {
        spriteBatch = new SpriteBatch();
        random = new Random();
        windowFactorX = Gdx.graphics.getWidth()/START_WIDTH;
        windowFactorY = Gdx.graphics.getHeight()/START_HEIGHT;
    }

    public void draw(Texture sprite, float x, float y, float width, float height){
        TextureRegion tr = new TextureRegion(sprite);
        draw(tr, x, y, width, height, false, false);
    }

    public void draw(TextureRegion region, float x, float y, float width, float height, boolean flipX, boolean flipY){
        float newWidth = width * factorX * windowFactorX;
        float newHeight = height * factorY * windowFactorY;

        float newX = (x + movementOffsetX + shakeX) * factorX * windowFactorX;
        if(this.flipX){
            newX = ((START_WIDTH-x-newWidth/windowFactorX) + movementOffsetX + shakeX) * factorX * windowFactorX;
        }
        float newY = (y + movementOffsetY + shakeY) * factorY * windowFactorY;
        float newOriginX = newWidth / 2f; // Origin in middle.
        float newOriginY = newHeight; // Origin on the bottom.
        float xScale = (this.flipX ^ flipX) ? -1f : 1f;
        float yScale = flipY ? -1f : 1f;
        spriteBatch.draw(region, newX, newY, newOriginX, 0, newWidth, newHeight, xScale, yScale, 0f);
    }

    public void updateShake() {
        if (currentShake <= 0) return;
        shakeY = random.nextInt(2 * currentShake) - currentShake;
        shakeX = random.nextInt(2 * currentShake) - currentShake;
        currentShake *= 0.9;
    }

    public float getXfromEvent(MouseEvent event){
        if(this.flipX){
            return -((event.getScreenX() / (factorX*windowFactorX)) - movementOffsetX - START_WIDTH);
        } else {
            return (event.getScreenX() / (factorX*windowFactorX)) - movementOffsetX;
        }
    }

    public float getYfromEvent(MouseEvent event){
        return ((Gdx.graphics.getHeight() - event.getScreenY()) / (factorY*windowFactorY)) - movementOffsetY;
    }

    private void updateHeight(){
        factorY *= updateFactorY;
    }

    private void updateWidth(){
        factorX *= updateFactorX;
    }

    private void newMovementSpeeds() {
        if (movementSpeed == 0) return;
        movementX = random.nextInt(movementSpeed);
        movementY = movementSpeed - movementX;
    }

    private void updateMovement(long deltaTimeMillis) {
        if (movementSpeed <= 0) return;
        if (movementX == 0 && movementY == 0) {
            newMovementSpeeds();
            return;
        }
        movementOffsetX += movementX * movementXDirection * deltaTimeMillis / 1000f;
        movementOffsetY += movementY * movementYDirection * deltaTimeMillis / 1000f;

        if(movementOffsetX < 0) {
            movementOffsetX = 0;
            movementXDirection = 1;
            newMovementSpeeds();
        } else if((START_WIDTH + movementOffsetX) * factorX >= START_WIDTH) { // (a + b) * c > d  ===== (d / c) - a
            movementOffsetX = START_WIDTH / factorX - START_WIDTH;
            movementXDirection = -1;
            newMovementSpeeds();
        }
        if(movementOffsetY < 0) {
            movementOffsetY = 0;
            movementYDirection = 1;
            newMovementSpeeds();
        } else if((START_HEIGHT + movementOffsetY) * factorY >= START_HEIGHT) {
            movementOffsetY = START_HEIGHT / factorY - START_HEIGHT;
            movementYDirection = -1;
            newMovementSpeeds();
        }
    }

    private void updateLoop(){
        while(timingCounter >= updateMillis) {
            updateShake();
            updateHeight();
            updateWidth();
            timingCounter -= updateMillis;
        }
    }

    public void begin(long deltaTimeMillis){
        timingCounter += deltaTimeMillis;
        updateMovement(deltaTimeMillis);
        updateLoop();

        spriteBatch.begin();
    }

    public void end(){
        spriteBatch.end();
    }

    public void startShake() {
        currentShake = shake;
    }

    public void multiplyShake(float multiplier) {
        shake = (int) (shake * multiplier);
    }

    public void resetShake() {
        shake = SHAKE_START;
    }

    public void scale(float xScale, float yScale) {
        factorX *= xScale;
        factorY *= yScale;
    }

    public void setScale(float xScale, float yScale) {
        factorX = xScale;
        factorY = yScale;
    }

    public void setScaleX(float scale) {
        factorX = scale;
    }

    public void setScaleY(float scale) {
        factorY = scale;
    }

    public float getWindowFactorX() {
        return windowFactorX;
    }

    public float getWindowFactorY() {
        return windowFactorY;
    }

    public float getWidth() {
        return START_WIDTH * factorX;
    }

    public float getHeight() {
        return START_HEIGHT * factorY;
    }

    public void resetMovementOffset() {
        movementOffsetX = 0;
        movementOffsetY = 0;
    }

    public void addMovementOffset(float xAmount, float yAmount) {
        movementOffsetX += xAmount;
        movementOffsetY += yAmount;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int speed) {
        movementSpeed = speed;
        newMovementSpeeds();
    }

    public void multiplyMovementSpeed(float multiplier) {
        movementSpeed = (int) (movementSpeed * multiplier);
        newMovementSpeeds();
    }

    public void setFlipX(boolean flip) {
        flipX = flip;
    }

    public void inverseFlipX() {
        flipX = !flipX;
    }

    public void resetFlips() {
        flipX = false;
    }

    public boolean flipX() {
        return flipX;
    }
}
