package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

public interface Alive extends Actor {
    /**
     *  Creating a topic named "Alive actor died" with a specified class type (Alive.class)
     */
    Topic<Alive> ALIVE_ACTOR_DIED = Topic.create("Alive actor died", Alive.class);
    /**
     *  @return a reference to the Health class object associated with the given actor.
     */
    Health getHealth();
}
