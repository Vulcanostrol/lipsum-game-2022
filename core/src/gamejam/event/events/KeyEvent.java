package gamejam.event.events;

import gamejam.event.Event;

public class KeyEvent implements Event {
    private final boolean isKeyDown;
    private final int code;

    public KeyEvent(boolean isKeyDown, int code) {
        this.isKeyDown = isKeyDown;
        this.code = code;
    }

    public int getCode(){
        return this.code;
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
