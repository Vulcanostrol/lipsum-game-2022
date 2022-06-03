package gamejam.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import gamejam.event.EventConsumer;
import gamejam.event.EventQueue;
import gamejam.event.EventType;
import gamejam.event.events.KeyEvent;
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
        EventConsumer<MenuChangeEvent> consumer = this::handleMenuChangeEvent;
        EventQueue.getInstance().registerConsumer(consumer, EventType.MENU_CHANGE);

        // Pause menu
        EventConsumer<KeyEvent> keyConsumer = this::onKeyEvent;
        EventQueue.getInstance().registerConsumer(keyConsumer, EventType.KEY_EVENT);
    }

    public void handleMenuChangeEvent(MenuChangeEvent event) {
        switchMenu(event.getMenuId());
    }

    private void onKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == Input.Keys.ESCAPE && event.isKeyDown()) {
            EventQueue.getInstance().invoke(new MenuChangeEvent(2));
        }
    }

    public void registerMenu(Menu menuToRegister) {
        registeredMenus.add(menuToRegister);
    }

    public void switchMenu(int id) {
        if (id == currentMenuId) return;
        if (currentMenuId >= 0) {
            registeredMenus.get(currentMenuId).dispose();
        }
        currentMenuId = id;
        if (id < 0) {
            Gdx.input.setInputProcessor(InputHandler.getInstance());
            return;
        }
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
