package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

import java.util.ArrayList;
import java.util.List;

public class Health extends AbstractActor implements Alive{
    private int currentHealth;
    private final int maxHealth;
    private final List<FatigueEffect> effects = new ArrayList<>();
    public Health(int currentHealth){
        this.currentHealth = currentHealth;
        this.maxHealth = currentHealth;
    }
    public Health(int currentHealth, int maxHealth){
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

    public int getValue(){
        return currentHealth;
    }

    public void refill(int amount){
        currentHealth += amount;
        if(currentHealth > maxHealth) currentHealth = maxHealth;
    }

    public void restore(){currentHealth = maxHealth;}

    public void drain(int amount){
        currentHealth -= amount;
        if(currentHealth <= 0) exhaust();
    }

    public void exhaust(){
        currentHealth = 0;
        if(!effects.isEmpty()) effects.forEach(FatigueEffect::apply);
        effects.clear();
    }

    @Override
    public Health getHealth() {
        return this;
    }
    public void onFatigued(Health.FatigueEffect effect){
        if(effect != null) effects.add(effect);
    };
    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }
}
