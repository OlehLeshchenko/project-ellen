package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

public interface Movable extends Actor {
    /**
     * @return the actor's movement speed
     */
    int getSpeed();
    /**
     * start of movement in direction
     */
    default void startedMoving(Direction direction) {}
    /**
     * end of movement in direction
     */
    default void stoppedMoving() {}
    /**
     * action when an actor is facing with the wall
     */
    default void collidedWithWall() {}
}
