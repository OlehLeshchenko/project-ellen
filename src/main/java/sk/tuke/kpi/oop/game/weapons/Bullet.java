package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Bullet extends AbstractActor implements Fireable {
    private final Animation animation;
    private final int speed;
    public Bullet(){
        speed = 4;
        animation = new Animation("sprites/bullet.png");
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
                Alive alive = (Alive) scene.getActors().stream().
                    filter(actor -> (actor instanceof Alive && this.intersects(actor))).
                    findFirst().
                    orElse(null);
                if(alive != null && !(alive instanceof Ripley)) {
                    alive.getHealth().drain(15);
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
