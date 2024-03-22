package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor>{
    public AccessCard(){
        Animation animation = new Animation("sprites/key.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(LockedDoor actor) {
        if(actor == null || !actor.getName().equals("locked door")) return;
        if(actor.isLocked()) actor.unlock();
        else actor.lock();
    }

    @Override
    public Class<LockedDoor> getUsingActorClass() {
        return LockedDoor.class;
    }
}
