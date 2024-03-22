package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;

import java.util.Random;

public class LurkerAlien extends Alien {
    private Animation animation;
    private final Behaviour<? super Alien> behaviour;
    private final int random;
    private Color color = Color.WHITE;
    private int praetorianCounter = 0;
    public LurkerAlien(Behaviour<? super Alien> behaviour){
        super(45, behaviour);
        this.behaviour = behaviour;
        this.random = new Random().nextInt(8) + 1;
        Animation animation = new Animation("sprites/lurker_born.png", 32, 32, 0.4f, Animation.PlayMode.ONCE);
        setAnimation(animation);
        setSpeed(3);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        praetorianCounter = (int)scene.getActors().stream().
            filter(actor -> actor instanceof Praetorian || (actor instanceof LurkerAlien && ((LurkerAlien) actor).isFuturePraetorian())).
            count();
        color = (isFuturePraetorian() ? Color.RED : Color.WHITE);
        getAnimation().setTint(color);

        new When<>(
            () -> getAnimation().getCurrentFrameIndex() == 3,
            new Invoke<>(() -> {
                animation = new Animation("sprites/lurker_alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG, color);
                setAnimation(animation);
                if(behaviour != null) behaviour.setUp(this);
                alienTransformation(scene);
            })
        ).scheduleFor(this);

    }

    public void alienTransformation(@NotNull Scene scene){
        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(()->{
                if(isFuturePraetorian()){
                    scene.addActor(new Praetorian(new RandomlyMoving()), getPosX(), getPosY());
                }
                else scene.addActor(new Alien(100, new RandomlyMoving()), getPosX() + 16 - getWidth()/2, getPosY() + 16 - getHeight()/2);
                scene.removeActor(this);
            })
        ).scheduleFor(this);
    }

    public boolean isFuturePraetorian(){
        return random < 6 && praetorianCounter < 2;
    }

}
