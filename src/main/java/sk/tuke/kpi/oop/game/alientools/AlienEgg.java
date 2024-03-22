package sk.tuke.kpi.oop.game.alientools;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.*;

public class AlienEgg extends AbstractActor implements Alive, Enemy {
    private Animation animation;
    private Health health;
    public AlienEgg(){
        animation = new Animation("sprites/alien_egg.png", 32 ,32, 0.1f, Animation.PlayMode.ONCE);
        animation.pause();
        setAnimation(animation);
        this.health = new Health(30);
        this.health.onFatigued(this::die);
    }

    public void die(){
        Scene scene = getScene();
        if(scene != null){
            scene.removeActor(this);
            scene.getMessageBus().publish(ALIVE_ACTOR_DIED, this);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new ActionSequence<>(
            new Wait<>(15),
            new Invoke<>(()->{
                animation.play();
            }),
            new Wait<>(3),
            new Invoke<>(()->{
                scene.addActor(new LurkerAlien(new RandomlyMoving()), getPosX(), getPosY());
                scene.removeActor(this);

            })
        ).scheduleFor(this);

    }

    @Override
    public Health getHealth() {
        return health;
    }
}
