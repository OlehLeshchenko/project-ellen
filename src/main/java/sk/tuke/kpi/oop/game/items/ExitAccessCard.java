package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class ExitAccessCard extends AccessCard{
    public ExitAccessCard(){
        super();
        getAnimation().setTint(Color.BLUE);
    }
    @Override
    public void useWith(LockedDoor actor) {
        if(actor == null || !actor.getName().equals("exit door")) return;
        if(actor.isLocked()) actor.unlock();
        else actor.lock();
    }
}
