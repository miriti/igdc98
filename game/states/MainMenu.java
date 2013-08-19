package game.states;

import engine.Engine;
import engine.core.TextureManager;
import engine.core.types.Color;
import engine.devices.input.GamePadInput;
import engine.devices.input.PCInput;
import engine.display.Button;
import engine.display.DisplayObject;
import engine.display.Image;
import game.GameCore;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class MainMenu extends DisplayObject {

    private MenuItem[] buttons;
    private int selectedButton;
    private int changeDelay;

    public MainMenu() {
        Image logoImage = new Image(TextureManager.getTexture("data/hud/menu/logo.png"));
        addChildAt(logoImage, (1920 - logoImage.getWidth()) / 2, 0);

        Image creditsImage = new Image(TextureManager.getTexture("data/hud/menu/credits.png"), 1066, 32);
        addChildAt(creditsImage, (1920 - creditsImage.getWidth()) / 2, 1080 - 32);

        buttons = new MenuItem[]{
            new MenuItem("data/hud/menu/play-button") {
                @Override
                public void execute() {
                    DisplayObject state = GameRound.getInstance() == null ? new Shop() : GameRound.getInstance();
                    GameCore.getInstance().setState(state);
                }
            },
            new MenuItem("data/hud/menu/quit-button") {
                @Override
                public void execute() {
                    Engine.getInstance().getDevice().terminate();
                }
            }
        };

        for (int i = 0; i < buttons.length; i++) {
            addChildAt(buttons[i], 1920 / 2, 500 + i * 250);
        }

        selectButton(0);
    }

    private void selectButton(int index) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setSelected(false);
        }

        if (index >= buttons.length) {
            index = 0;
        }

        if (index < 0) {
            index = buttons.length - 1;
        }

        buttons[index].setSelected(true);

        selectedButton = index;
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if (getInput().isDown()) {
            if (changeDelay <= 0) {
                selectButton(selectedButton + 1);
                changeDelay = 200;
            } else {
                changeDelay -= deltaTime;
            }
        } else {
            if (getInput().isUp()) {
                if (changeDelay <= 0) {
                    selectButton(selectedButton - 1);
                    changeDelay = 200;
                } else {
                    changeDelay -= deltaTime;
                }
            } else {
                changeDelay = 0;
            }
        }

        if (getInput().getType().equals("pc")) {
            if (((PCInput) getInput()).getBoolData("return")) {
                buttons[selectedButton].execute();
            }
        } else {
            if (((GamePadInput) getInput()).getFloatData("0") == 1.0f) {
                buttons[selectedButton].execute();
            }
        }
    }
}

abstract class MenuItem extends DisplayObject {

    private Image buttonImage;
    private boolean selected;

    public MenuItem(String imagePath) {
        buttonImage = new Image(TextureManager.getTexture(imagePath + ".png"));

        buttonImage.setColor(new Color(1, 1, 1, 1));
        addChildAt(buttonImage, -buttonImage.getWidth() / 2, -buttonImage.getHeight() / 2);
    }

    abstract public void execute();

    public boolean isSelected() {
        return selected;
    }

    final public void setSelected(boolean selected) {
        this.selected = selected;
        if (selected) {
            buttonImage.getColor().set(0x33 / 256f, 0x33 / 256f, 0x33 / 256f, 1);
        } else {
            buttonImage.getColor().set(0xcc / 256f, 0xcc / 256f, 0xcc / 256f, 1);
        }
    }
}
