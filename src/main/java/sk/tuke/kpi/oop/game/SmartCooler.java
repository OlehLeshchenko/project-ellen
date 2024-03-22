package sk.tuke.kpi.oop.game;

public class SmartCooler extends Cooler{

    private final Reactor reactor;

    public SmartCooler(Reactor reactor) {
        super(reactor);
        this.reactor = reactor;
    }

    @Override
    public void coolReactor(){
        if(reactor == null) return;
        if(reactor.getTemperature() >= 2500) this.turnOn();
        else if(reactor.getTemperature() <= 1500) this.turnOff();
        if(this.isOn()){
            this.reactor.decreaseTemperature(1);
        }
    }
}
