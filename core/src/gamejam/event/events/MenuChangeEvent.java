package gamejam.event.events;

import gamejam.event.Event;
import gamejam.event.EventType;

public class MenuChangeEvent implements Event {

    private final int menuId;

    public MenuChangeEvent(int menuIdToChangeTo) {
        menuId = menuIdToChangeTo;
    }

    @Override
    public String getType() {
        return EventType.MENU_CHANGE;
    }

    public int getMenuId() {
        return menuId;
    }
}
