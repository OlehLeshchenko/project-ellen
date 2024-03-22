package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.items.Backpack;

public class Crewmate extends AbstractActor implements Movable, Keeper, Alive {
    private final int speed;
    private final Health health;
    public static final Topic<Crewmate> CREWMATE_DIED = Topic.create("Crewmate died", Crewmate.class);
    private final RandomlyMoving behaviour;
    public Crewmate(RandomlyMoving behaviour){
        super("Crewmate");
        this.speed = 1;
        this.health = new Health(100);
        this.behaviour = behaviour;
        startedMoving(Direction.NORTH);
        stoppedMoving();
        health.onFatigued(this::die);
    }
    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction){
        Movable.super.startedMoving(direction);
        Animation animation = new Animation("sprites/crewmate.png", 32, 32, 0.1f/speed, Animation.PlayMode.LOOP_PINGPONG, Color.WHITE, 1, (float) direction.getAngle());
        setAnimation(animation);
    }

    @Override
    public void stoppedMoving() {
        Movable.super.stoppedMoving();
        this.getAnimation().stop();
    }

    public void die(){
        Scene scene = getScene();
        if(scene != null){
            scene.removeActor(this);
            scene.getMessageBus().publish(CREWMATE_DIED, this);
        }
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Backpack getBackpack() {
        return null;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        behaviour.setUp(this);
    }
}
