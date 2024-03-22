package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

public interface EnergyConsumer extends Actor {
    /**
     *  the energy producer will notify the equipment that energy is or is not being supplied.
     */
    void setPowered(boolean power);
}
