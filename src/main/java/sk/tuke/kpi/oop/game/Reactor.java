package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{

    private int temperature;
    private int damage;
    private float frequency;
    private boolean extinguisher;

    private final Animation offAnimation;
    private final Animation brokenAnimation;
    private final Animation extinguishedAnimation  ;
    private boolean state;
    private final Set<EnergyConsumer> devices;

    public Reactor() {
        temperature = 0;
        damage = 0;
        state = false;
        extinguisher = false;
        frequency = 0.1f;
        devices = new HashSet<>();
        offAnimation = new Animation("sprites/reactor.png");
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        extinguishedAnimation = new Animation("sprites/reactor_extinguished.png");
        setAnimation(offAnimation);
    }

    public int getTemperature(){
        return this.temperature;
    }

    public int getDamage(){ return this.damage;}

    public void setTemperature(int temperature){
        this.temperature = temperature;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    @Override
    public void updateAnimation() {
        Animation newAnimation;
        Animation current = this.getAnimation();
        if (isReactorOn()) { //normalAnimation
            newAnimation = new Animation("sprites/reactor_on.png", 80, 80, this.frequency, Animation.PlayMode.LOOP_PINGPONG);
        }
        else if (isReactorOverheated()) { //overheatedAnimation
            newAnimation = new Animation("sprites/reactor_hot.png", 80, 80, this.frequency, Animation.PlayMode.LOOP_PINGPONG);
        }
        else if (isReactorExtinguished()) {
            newAnimation = extinguishedAnimation;
        }
        else if(isReactorOff()){
            newAnimation = offAnimation;
        }
        else {
            newAnimation = brokenAnimation;
        }
        if(!current.getResource().equals(newAnimation.getResource()) || current.getFrameDuration() != newAnimation.getFrameDuration()){
            setAnimation(newAnimation);
        }
    }

    private boolean isReactorOff() {
        return damage < 100 && !state;
    }
    private boolean isReactorOn() {
        return temperature < 4000 && damage < 100 && state;
    }
    private boolean isReactorOverheated() {
        return temperature < 6000 && damage < 100 && state;
    }
    private boolean isReactorExtinguished() {
        return damage == 100 && extinguisher;
    }


    public void increaseTemperature(int increment){
        if(increment <= 0 || !isOn()) return;

        if(damage < 33) temperature += increment;
        else if(damage <=66) temperature += (int)Math.round(increment * 1.5);
        else temperature += increment * 2;

        if(temperature > 2000 && temperature < 6000) damage = (temperature - 2000) / 40;
        else if(temperature >= 6000){
            damage = 100;
            turnOff();
        }
        this.frequency = (0.1f - 0.0005f * (float)damage);

        updateAnimation();
    }

    public void decreaseTemperature(int decrement){
        if(damage == 100 || temperature < 0 || decrement <= 0 || !this.isOn()) return;

        if(damage > 50) temperature -= decrement / 2;
        else temperature -= decrement;

        if(temperature < 0) temperature = 0;

        updateAnimation();
    }

    public boolean repair(){
        boolean result = false;
        if(this.damage > 0 && this.damage < 100){
            if(damage < 50) damage = 0;
            else damage -= 50;
            int temp = damage * 40;
            temperature = temp + 2000;
            this.frequency = (0.1f - 0.0005f * (float)damage);
            updateAnimation();
            result = true;
        }

        return result;
    }

    public boolean extinguish(){

        boolean result = false;
        if(damage == 100){
            setTemperature(4000);
            this.extinguisher = true;
            updateAnimation();
            result = true;
        }
        return result;
    }
    @Override
    public void turnOn(){
        if(damage == 100) return;
        state = true;
        for (EnergyConsumer device : devices) {
            device.setPowered(true);
        }
        updateAnimation();
    }
    @Override
    public void turnOff(){
        state = false;
        for (EnergyConsumer device : devices) {
            device.setPowered(false);
        }
        updateAnimation();
    }
    @Override
    public boolean isOn(){
        return this.state;
    }

    public void addDevice(EnergyConsumer device){
        Scene scene = getScene();
        if(scene != null && device != null){
            devices.add(device);
            scene.addActor(device);
            device.setPowered(state);
        }
    }

    public void removeDevice(EnergyConsumer device){
        Scene scene = getScene();
        if(scene != null && devices.contains(device)){
            device.setPowered(false);
            scene.removeActor(device);
            devices.remove(device);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}

