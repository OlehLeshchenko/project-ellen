package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;


public class PerpetualReactorHeating  extends AbstractAction<Reactor> {

    private final int temp;

    public PerpetualReactorHeating(int temp){
        this.temp = temp;
    }


    @Override
    public void execute(float deltaTime) {
        Reactor reactor = this.getActor();
        if(reactor != null) reactor.increaseTemperature(temp);
    }
}
