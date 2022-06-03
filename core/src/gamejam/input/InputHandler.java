package gamejam.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import gamejam.event.EventQueue;
import gamejam.event.events.KeyEvent;
import gamejam.event.events.KeyTypedEvent;
import gamejam.factories.EntityFactory;

import java.util.function.Function;

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
        this.eventQueue.add(new KeyEvent(true, keycode));
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.eventQueue.add(new KeyEvent(false, keycode));
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        this.eventQueue.add(new KeyTypedEvent(character));
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
        } else if (button == Input.Buttons.RIGHT) {
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return this.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // handle camera zooming input
        return false;
    }
}
