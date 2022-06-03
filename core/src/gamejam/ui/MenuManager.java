package gamejam.ui;

import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.events.MenuChangeEvent;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    private final List<Menu> registeredMenus;
    private int currentMenuId;

    public MenuManager() {
        registeredMenus = new ArrayList<>();
        currentMenuId = -1;

        // Menu change listeners
        EventConsumer<MenuChangeEvent> consumer = this::handleMenuChangeEvent;
        EventQueue.getInstance().registerConsumer(consumer, "MenuChange");
    }

    public void handleMenuChangeEvent(MenuChangeEvent event) {
        switchMenu(event.getMenuId());
    }

    public void registerMenu(Menu menuToRegister) {
        registeredMenus.add(menuToRegister);
    }

    public void switchMenu(int id) {
        if (id == currentMenuId) return;
        if (currentMenuId >= 0) {
            registeredMenus.get(currentMenuId).dispose();
        }
        if (id < 0) return;
        currentMenuId = id;
        registeredMenus.get(currentMenuId).create();
    }

    public void draw() {
        if (currentMenuId < 0) return;
        registeredMenus.get(currentMenuId).draw();
    }

    public void onResize(int width, int height) {
        if (currentMenuId < 0) return;
        registeredMenus.get(currentMenuId).onResize(width, height);
    }
}
