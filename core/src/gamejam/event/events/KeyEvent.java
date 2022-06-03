package gamejam.event.events;

import gamejam.event.Event;

public class KeyEvent implements Event {
    private final boolean isKeyDown;
    private final int keyCode;

    public KeyEvent(boolean isKeyDown, int keyCode) {
        this.isKeyDown = isKeyDown;
        this.keyCode = keyCode;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isKeyDown() {
        return this.isKeyDown;
    }

    public boolean isKeyUp() {
        return !this.isKeyDown;
    }

    @Override
    public String getType() {
        return "KeyEvent";
    }
}
