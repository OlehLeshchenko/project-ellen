package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor {

    private final Switchable device;
    public PowerSwitch(Switchable device){
        this.device = device;
        Animation animation = new Animation("sprites/switch.png");
        setAnimation(animation);
    }

    public void toggle(){
        if(device == null) return;
        if(device.isOn()) device.turnOff();
        else device.turnOn();
    }

    public void switchOn(){
        if(device == null) return;
        device.turnOn();
        this.getAnimation().setTint(Color.WHITE);
    }

    public void switchOff(){
        if(device == null) return;
        device.turnOff();
        this.getAnimation().setTint(Color.GRAY);
    }

    public Switchable getDevice(){
        return this.device;
    }
}
