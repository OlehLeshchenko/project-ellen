package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

public interface Repairable extends Actor {
    /**
     * @return a value will reflect the success or failure of the repair
     */
    boolean repair();
}
