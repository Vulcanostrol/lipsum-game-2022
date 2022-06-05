package gamejam.event.events;

public abstract class MouseButtonEvent extends MouseEvent {
    private final int button;

    public MouseButtonEvent(int xScreen, int yScreen, int button) {
        super(xScreen, yScreen);
        this.button = button;
    }

    public int getButton() {
        return button;
    }
}
