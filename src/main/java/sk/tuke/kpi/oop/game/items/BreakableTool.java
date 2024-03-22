package sk.tuke.kpi.oop.game.items;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.Keeper;

public abstract class BreakableTool<T extends Actor> extends AbstractActor implements Usable<T> {

    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }
    public int getRemainingUses (){return this.remainingUses;}

    @Override
    public void useWith(T actor){
        this.remainingUses --;

        if(this.remainingUses  < 1){
            Scene scene = getScene();
            if(scene == null) return;
            scene.removeActor(this);
            Keeper player = scene.getFirstActorByType(Keeper.class);
            if(player == null || !(this instanceof Collectible)) return;
            player.getBackpack().remove((Collectible) this);
        }
    }

}
