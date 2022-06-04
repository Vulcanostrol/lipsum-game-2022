package gamejam.input;

import com.badlogic.gdx.InputProcessor;
import gamejam.event.EventQueue;
import gamejam.event.events.*;

public class InputHandler implements InputProcessor {

    EventQueue eventQueue = EventQueue.getInstance();

    private static InputHandler instance = null;

    public static InputHandler getInstance() {
        if (instance == null) {
            instance = new InputHandler();
        }
        return instance;
    }

    @Override
    public boolean keyDown(int keycode) {
        this.eventQueue.invoke(new KeyEvent(true, keycode));
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.eventQueue.invoke(new KeyEvent(false, keycode));
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        this.eventQueue.invoke(new KeyTypedEvent(character));
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.eventQueue.invoke(new MousePressEvent(screenX, screenY, button));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.eventQueue.invoke(new MouseReleaseEvent(screenX, screenY, button));
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return this.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.eventQueue.invoke(new MouseMoveEvent(screenX, screenY));
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // handle camera zooming input
        return false;
    }
}
