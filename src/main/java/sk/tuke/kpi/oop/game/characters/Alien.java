package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {
    private int speed;
    private Health health;
    private Behaviour<? super Alien> behaviour;

    public Alien(int health, Behaviour<? super Alien> behaviour){
        this.behaviour = behaviour;
        speed = 1;
        this.health = new Health(health);
        Animation animation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        animation.stop();
        this.health.onFatigued(this::die);
    }
    public Alien(){
        speed = 1;
        this.health = new Health(100);
        Animation animation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        animation.stop();
        this.health.onFatigued(this::die);
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if(this instanceof LurkerAlien) return;
        else if(behaviour != null) behaviour.setUp(this);

        new Loop<>(
            new ActionSequence<>(
                new Wait<>(0.3f),
                new Invoke<>(this::attack)
            )
        ).scheduleFor(this);

    }
    public void attack(){
        Scene scene = getScene();
        if(scene == null ) return;
        Alive actor = (Alive) scene.getActors().stream().
            filter(actor1 -> !(actor1 instanceof Enemy) &&
                (actor1 instanceof Alive) &&
                (actor1.intersects(this))).
            findFirst().
            orElse(null);
        if(actor == null) return;
        actor.getHealth().drain(5);
    }

    public void die(){
        getAnimation().stop();
        Scene scene = getScene();
        if(scene != null){
            scene.removeActor(this);
            scene.getMessageBus().publish(ALIVE_ACTOR_DIED, this);
        }
    }

    @Override
    public void startedMoving(Direction direction) {
        Movable.super.startedMoving(direction);
        getAnimation().setRotation(direction.getAngle());
    }

    @Override
    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }
}
