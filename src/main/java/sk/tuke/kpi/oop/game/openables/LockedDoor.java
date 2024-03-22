package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public class LockedDoor extends Door {
    private boolean locked;
    public LockedDoor(String name, Orientation orientation){
        super(name, orientation);
        locked = true;
    }

    public boolean isLocked(){
        return locked;
    }

    public void lock(){
        locked = true;
    };

    public void unlock(){
        locked = false;
    }

    @Override
    public void useWith(Actor actor) {
        if(!super.isOpen() && locked) return;
        else if(super.isOpen() || locked) close();
        else open();
    }
}
