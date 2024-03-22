package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;

public interface Usable<T extends Actor> {
    /**
     *  Use the object by the actor
     */
    void useWith(T actor);

    /**
     *  @return the class of the actor that performs the action
     */
    Class<T> getUsingActorClass();
}
