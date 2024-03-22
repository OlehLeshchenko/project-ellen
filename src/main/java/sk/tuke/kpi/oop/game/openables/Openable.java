package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;

public interface Openable extends Actor {

    /**
     * open the object (actor)
     */
    void open();
    /**
     * close the object (actor)
     */
    void close();
    /**
     * @return true if the object (actor) is open, otherwise returns false
     */
    boolean isOpen();
}
