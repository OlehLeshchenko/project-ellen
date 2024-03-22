package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

import java.util.Random;

public class DefectiveLight extends Light implements Repairable{
    private Disposable cycle;
    private boolean currentUse;

    public DefectiveLight(){
        super();
        currentUse = false;
    }

   private void randomLight(){
        int random = new Random().nextInt(21);
        if(random == 1) {
            if(this.isOn()) turnOff();
            else turnOn();
        }

   }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        cycle = new Loop<>(new Invoke<>(this::randomLight)).scheduleFor(this);
    }
    public boolean repair() {
        if(currentUse) return false;
        Scene scene = this.getScene();
        if(scene == null) return false;
        cycle.dispose();
        turnOn();
        new ActionSequence<>(
            new Invoke<>(()->currentUse = true),
            new Wait<>(10),
            new Invoke<>(()->cycle = new Loop<>(new Invoke<>(this::randomLight)).scheduleFor(this)),
            new Invoke<>(()->currentUse = false)
        ).scheduleFor(this);

        return true;
    }
}
