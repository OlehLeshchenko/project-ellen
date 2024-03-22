package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Shift<T extends Keeper> extends AbstractAction<T> {

    public Shift() {}

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
            backpack.shift();
        } catch (Exception exception) {
            scene.getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
        }
    }
}
