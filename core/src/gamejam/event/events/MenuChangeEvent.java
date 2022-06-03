package gamejam.event.events;

import gamejam.event.Event;

public class MenuChangeEvent implements Event {

    private final int menuId;

    public MenuChangeEvent(int menuIdToChangeTo) {
        menuId = menuIdToChangeTo;
    }

    @Override
    public String getType() {
        return "MenuChange";
    }

    public int getMenuId() {
        return menuId;
    }
}
