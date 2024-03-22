package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;

public interface Behaviour<T extends Actor> {
    /**
     *  will define the behaviour of the actor in question
     */
    void setUp(T actor);
}
