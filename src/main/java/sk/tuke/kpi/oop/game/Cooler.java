package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable{

    private final Reactor reactor;
    private boolean state;
    private Animation animation;

    public Cooler(Reactor reactor){
        this.reactor = reactor;
        this.state = false;
        animation = new Animation("sprites/fan.png", 32, 32, 0.0f);
        setAnimation(animation);
    }

    public void coolReactor(){
        if(state && reactor != null) this.reactor.decreaseTemperature(1);
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
    @Override
    public void updateAnimation(){
        if(state) animation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        else animation = new Animation("sprites/fan.png", 32, 32, 0f);
        setAnimation(animation);
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
