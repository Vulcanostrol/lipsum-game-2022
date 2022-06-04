package gamejam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import gamejam.GameManager;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.MenuChangeEvent;
import gamejam.event.events.RoomChangeEvent;
import gamejam.levels.Direction;

public class LevelFadeMenu extends Menu {

    public static final float ANIMATION_TIME = 0.5f;
    private static Direction roomChangeDirection;

    static {
        EventConsumer<RoomChangeEvent> consumer = LevelFadeMenu::onRoomChangeEvent;
        EventQueue.getInstance().registerConsumer(consumer, EventType.ROOM_CHANGED);
    }

    private static void onRoomChangeEvent(RoomChangeEvent event) {
        roomChangeDirection = event.getDirection();
        if (!event.isUpgrade()) {
            EventQueue.getInstance().invoke(new MenuChangeEvent(3));
        }
    }

    // Menu itself, no static stuff anymore.

    private Image overlay;
    private float currentAlpha;
    private float deltaMultiplier;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        currentAlpha = 0f;
        deltaMultiplier = 1f;

        overlay = new Image(new Texture("overlay.png"));
        overlay.setFillParent(true);
        overlay.setColor(new Color(0f, 0f, 0f, 0f));
        stage.addActor(overlay);
    }

    public void draw() {
        float delta = Gdx.graphics.getDeltaTime();
        currentAlpha += delta * deltaMultiplier / ANIMATION_TIME;
        overlay.setColor(new Color(0f, 0f, 0f, currentAlpha));
        if (currentAlpha >= 1) {
            onOverlayComplete();
        }
        if (currentAlpha <= 0) {
            onComplete();
        }
        super.draw();
    }

    private void onOverlayComplete() {
        deltaMultiplier = -1;
        // Load level
        GameManager.getInstance().moveToRoomByDirection(roomChangeDirection);
    }

    private void onComplete() {
        EventQueue.getInstance().invoke(new MenuChangeEvent(-1));
    }
}
