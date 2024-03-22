package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Jaw;

public class Praetorian extends Alien implements Armed{
    private Firearm jaw;
    public Praetorian(Behaviour<? super Alien> behaviour){
        super(200, behaviour);
        Animation animation = new Animation("sprites/praetorian.png", 72, 128, 0.2f, Animation.PlayMode.LOOP_PINGPONG, Color.WHITE, 0.8f);
        setAnimation(animation);
        this.jaw = new Jaw(500, 500);
    }

    @Override
    public Firearm getFirearm() {
        return jaw;
    }

    @Override
    public void setFirearm(Firearm jaw) {this.jaw = jaw;}

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene); //
        new Loop<>(
            new ActionSequence<>(
                new Wait<>(3),
                new Invoke<>(()->{
                    new Fire<>().scheduleFor(this);
                    jaw.reload(1);
                })
            )
        ).scheduleFor(this);


    }
}
