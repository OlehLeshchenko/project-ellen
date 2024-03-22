package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{

    private boolean state;
    private boolean electricityFlow;
    private final Animation turnOn;
    private final Animation turnOff  ;

    public Light(){
        state = false;
        electricityFlow = false;
        turnOn = new Animation("sprites/light_on.png");
        turnOff = new Animation("sprites/light_off.png");
        setAnimation(turnOff);
    }
    @Override
    public void turnOn(){
        state = true;
        updateAnimation();
    }
    @Override
    public void turnOff(){
        state = false;
        updateAnimation();
    }
    @Override
    public boolean isOn(){
        return this.state;
    }
    public void toggle(){
        state = !state;
        updateAnimation();
    }
    @Override
    public void setPowered(boolean electricity){
        this.electricityFlow = electricity;
        updateAnimation();
    }
    @Override
    public void updateAnimation(){
        if(state && electricityFlow) setAnimation(turnOn);
        else setAnimation(turnOff);
    }

}
