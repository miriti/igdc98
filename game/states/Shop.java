package game.states;

import engine.core.TextureManager;
import engine.core.types.Color;
import engine.devices.input.GamePadInput;
import engine.devices.input.PCInput;
import engine.display.DisplayObject;
import engine.display.Image;
import engine.display.Quad;
import game.GameCore;
import game.types.mobs.ControlledMob;
import game.types.mobs.player.AutoTank;
import game.types.mobs.player.Helicopter;
import game.types.mobs.player.MegaTank;
import game.types.mobs.player.RocketLauncher;
import game.types.mobs.player.Tank;

/**
 *
 * @author Michael Miriti <michael@miriti.ru>
 */
public class Shop extends DisplayObject {

    public static final int SWITCH_TIMEOUT = 250;
    private ShopItem[] shopItems;
    private int selectedItem = 0;
    private int switchTimeout = SWITCH_TIMEOUT;

    public Shop() {

        Image shopTitle = new Image(TextureManager.getTexture("data/hud/shop/title.png"));
        addChildAt(shopTitle, (1920 - shopTitle.getWidth()) / 2, 0);

        shopItems = new ShopItem[]{
            new ShopItem() {
                @Override
                protected ControlledMob mobFactory() {
                    return new Tank();
                }

                @Override
                public float getCost() {
                    return 100;
                }

                @Override
                protected Image priceImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/price/price100.png"));
                }

                @Override
                protected Image iconImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/icons/tank.png"));
                }
            },
            new ShopItem() {
                @Override
                protected ControlledMob mobFactory() {
                    return new AutoTank();
                }

                @Override
                public float getCost() {
                    return 250;
                }

                @Override
                protected Image priceImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/price/price250.png"));
                }

                @Override
                protected Image iconImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/icons/auto-tank.png"));
                }
            },
            new ShopItem() {
                @Override
                protected ControlledMob mobFactory() {
                    return new RocketLauncher();
                }

                @Override
                public float getCost() {
                    return 500;
                }

                @Override
                protected Image priceImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/price/price500.png"));
                }

                @Override
                protected Image iconImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/icons/rocket-launcher.png"));
                }
            },
            new ShopItem() {
                @Override
                protected ControlledMob mobFactory() {
                    return new Helicopter();
                }

                @Override
                public float getCost() {
                    return 1000;
                }

                @Override
                protected Image priceImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/price/price1000.png"));
                }

                @Override
                protected Image iconImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/icons/helicopter.png"));
                }
            },
            new ShopItem() {
                @Override
                protected ControlledMob mobFactory() {
                    return new MegaTank();
                }

                @Override
                public float getCost() {
                    return 2500;
                }

                @Override
                protected Image priceImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/price/price2500.png"));
                }

                @Override
                protected Image iconImageFactory() {
                    return new Image(TextureManager.getTexture("data/hud/shop/icons/mega-tank.png"));
                }
            }
        };

        for (int i = 0; i < shopItems.length; i++) {
            if (shopItems[i].getCost() <= GameRound.getMoney()) {
                shopItems[i].setAvailable(true);
            }
            addChildAt(shopItems[i], 300 + i * ((1920 - 300) / shopItems.length), 1080 / 2);
        }

        selectItem(0);
    }

    public final void selectItem(int item) {
        for (int i = 0; i < shopItems.length; i++) {
            shopItems[i].setSelected(false);
        }

        if (item >= shopItems.length) {
            item = 0;
        }

        if (item < 0) {
            item = shopItems.length - 1;
        }

        if (!shopItems[item].isAvailable()) {
            selectItem(item + 1);
        } else {
            shopItems[item].setSelected(true);
            selectedItem = item;
        }
    }

    @Override
    public void update(long deltaTime) {
        super.update(deltaTime);

        if (getInput().isRight()) {
            if (switchTimeout >= SWITCH_TIMEOUT) {
                selectItem(selectedItem + 1);
                switchTimeout = 0;
            } else {
                switchTimeout += deltaTime;
            }
        } else {
            if (getInput().isLeft()) {
                if (switchTimeout >= SWITCH_TIMEOUT) {
                    selectItem(selectedItem - 1);
                    switchTimeout = 0;
                } else {
                    switchTimeout += deltaTime;
                }
            } else {
                switchTimeout = SWITCH_TIMEOUT;
            }
        }

        if (getInput().getType().equals("pc")) {
            if (((PCInput) getInput()).getBoolData("return") || ((PCInput) getInput()).getBoolData("space")) {
                shopItems[selectedItem].execute();
            }
        } else {
            if (((GamePadInput) getInput()).getFloatData("0") == 1f) {
                shopItems[selectedItem].execute();
            }
        }
    }
}

abstract class ShopItem extends DisplayObject {

    private boolean selected;
    private boolean available;
    private Image frameGrey;
    private Image frameRed;
    private final Image naImage;

    public ShopItem() {
        frameGrey = new Image(TextureManager.getTexture("data/hud/shop/frame_grey.png"));
        frameRed = new Image(TextureManager.getTexture("data/hud/shop/frame_red.png"));
        naImage = new Image(TextureManager.getTexture("data/hud/shop/na.png"));

        addChildAt(frameGrey, -frameGrey.getWidth() / 2, -frameGrey.getHeight() / 2);
        addChildAt(frameRed, -frameRed.getWidth() / 2, -frameRed.getHeight() / 2);

        Image iconImage = iconImageFactory();
        addChildAt(iconImage, -iconImage.getWidth() / 2, -iconImage.getHeight() / 2);

        addChildAt(naImage, -naImage.getWidth() / 2, -naImage.getHeight() / 2);

        Image priceImage = priceImageFactory();
        addChildAt(priceImage, -priceImage.getWidth() / 2, 200f);
    }

    abstract protected ControlledMob mobFactory();

    abstract public float getCost();

    abstract protected Image priceImageFactory();

    abstract protected Image iconImageFactory();

    public void execute() {
        GameRound.addMoney(-getCost());
        GameCore.getInstance().setState(new GameRound(mobFactory()));
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        naImage.setVisible(!available);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;

        frameRed.setVisible(selected);
        frameGrey.setVisible(!selected);
    }
}
