package gamejam.ui;

import com.badlogic.gdx.Gdx;
import gamejam.event.Event;
import gamejam.event.EventListener;
import gamejam.event.EventQueue;
import gamejam.event.events.MenuChangeEvent;
import gamejam.input.InputHandler;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    private final List<Menu> registeredMenus;
    private int currentMenuId;

    public MenuManager() {
        registeredMenus = new ArrayList<>();
        currentMenuId = -1;

        // Menu change listeners
        EventListener listener = new EventListener(this::handleMenuChangeEvent, "MenuChange");
        EventQueue.getInstance().registerListener(listener);
    }

    public void handleMenuChangeEvent(Event event) {
        MenuChangeEvent menuChangeEvent = (MenuChangeEvent) event;
        switchMenu(menuChangeEvent.getMenuId());
    }

    public void registerMenu(Menu menuToRegister) {
        registeredMenus.add(menuToRegister);
    }

    public void switchMenu(int id) {
        if (id == currentMenuId) return;
        if (currentMenuId >= 0) {
            registeredMenus.get(currentMenuId).dispose();
        }
        if (id < 0) {
            Gdx.input.setInputProcessor(InputHandler.getInstance());
            return;
        }
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
