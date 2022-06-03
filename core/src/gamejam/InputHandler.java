package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;

public class InputHandler implements InputProcessor {
    CameraController cameraController;
    BuildingController buildingController;

    public InputController(CameraController cameraController, BuildingController buildingController) {
        this.cameraController = cameraController;
        Gdx.input.setInputProcessor(this);
        this.buildingController = buildingController;
    }

    @Override
    public boolean keyDown(int keycode) {
        this.cameraController.setKeyActive(keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        this.cameraController.setKeyInactive(keycode);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            buildingController.onClick(screenX, screenY);
        } else if (button == Input.Buttons.RIGHT) {
            this.cameraController.setKeyActive(button);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.RIGHT) {
            this.cameraController.setKeyInactive(button);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (this.cameraController.isKeyActive(Input.Buttons.RIGHT)) {
            this.cameraController.pan(screenX, screenY);
        } else {
            return this.mouseMoved(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.cameraController.updateCursor(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // handle camera zooming input
        this.cameraController.zoomedAmount = amountY;
        return false;
    }
}
