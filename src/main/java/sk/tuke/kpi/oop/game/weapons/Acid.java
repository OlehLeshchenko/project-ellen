package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Crewmate;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Acid extends AbstractActor implements Fireable {
    private final Animation animation;
    private final int speed;
    public Acid(){
        speed = 4;
        animation = new Animation("sprites/energy_bullet.png", 16 ,16, 0.1f, Animation.PlayMode.ONCE, Color.GREEN, 2.4f);
        setAnimation(animation);
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        animation.setRotation(direction.getAngle());
    }
    @Override
    public void stoppedMoving() {
        animation.stop();
        Scene scene = getScene();
        if(scene == null) return;
        scene.removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(() ->{
                Ripley player = scene.getFirstActorByType(Ripley.class);

                scene.getActors().stream().
                    filter(actor -> actor instanceof Crewmate && actor.intersects(this)).
                    forEach(
                        actor -> {
                            ((Crewmate) actor).getHealth().drain(25);
                            collidedWithWall();
                        }
                    );

                if(player != null && player.intersects(this)) {
                    player.getHealth().drain(25);
                    collidedWithWall();
                }
            })
        ).scheduleFor(this);
    }

    @Override
    public void collidedWithWall() {
        Scene scene = getScene();
        if(scene != null) scene.removeActor(this);
    }
}
