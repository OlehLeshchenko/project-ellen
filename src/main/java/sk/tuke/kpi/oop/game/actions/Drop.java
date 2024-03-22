package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<T extends Keeper> extends AbstractAction<T> {

    public Drop() {}

    @Override
    public void execute(float deltaTime) {
        if(getActor() == null || getActor().getScene() == null || isDone()){
            setDone(true);
            return;
        }
        setDone(true);
        Scene scene = getActor().getScene();

        try {
            Keeper player = getActor();
            if(player == null) return;

            Backpack backpack = player.getBackpack();
            Collectible item = backpack.peek();
            if(item != null){
                backpack.remove(item);
                int itemX = player.getPosX() + player.getWidth()/2 - item.getWidth()/2,
                    itemY = player.getPosY() + player.getHeight()/2 - item.getHeight()/2;
                scene.addActor(item, itemX, itemY);
            }
        } catch (Exception exception) {
            scene.getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
        }
    }
}
