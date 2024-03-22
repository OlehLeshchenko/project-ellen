package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    private boolean electricityFlow;
    private Animation animationOn;
    private Animation animationOff;


    public Computer(){
        electricityFlow = false;
        animationOn  = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        animationOff = new Animation("sprites/computer.png", 80, 48, 0f);
        setAnimation(animationOff);
    }

    public int add(int a, int b){
        if(!electricityFlow) return 0;
        return a + b;
    }
    public float add(float a, float b){
        if(!electricityFlow) return 0;
        return a + b;
    }

    public int sub(int a, int b){
        if(!electricityFlow) return 0;
        return a - b;
    }
    public float sub(float a, float b){
        if(!electricityFlow) return 0;
        return a - b;
    }

    @Override
    public void setPowered(boolean power) {
        this.electricityFlow = power;
        if(power) setAnimation(animationOn);
        else setAnimation(animationOff);
    }
}
