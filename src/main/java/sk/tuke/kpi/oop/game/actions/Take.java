package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Bomb;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Take<T extends Keeper> extends AbstractAction<T> {

    public Take() {}

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

            Collectible item = (Collectible) scene.getActors().stream().
                filter(actor -> actor instanceof Collectible &&
                    actor.intersects(player)).
                filter(actor -> !(actor instanceof Bomb) || (!((Bomb)actor).isActivated() && !((Bomb)actor).isExploded())).
                findFirst().orElse(null);

            if(item != null){
                player.getBackpack().add(item);
                scene.removeActor(item);
            }
        } catch (Exception exception) {
            scene.getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
        }
    }
}
