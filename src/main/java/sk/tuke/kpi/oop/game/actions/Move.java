package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.weapons.Bullet;

public class Move<T extends Movable> implements Action<T> {
    private T actor;
    private final Direction direction;
    private boolean state;
    private float timeOfAction;
    private float duration;
    private int prevX, prevY;

    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        state = false;
        timeOfAction = 0.0f;
    }

    @Override
    public @Nullable T getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable T actor) {
        this.actor = actor;
    }

    @Override
    public boolean isDone() {
        return state;
    }

    @Override
    public void execute(float deltaTime) {
        if(actor == null || state) return;

        if(timeOfAction == 0.0f && direction != Direction.NONE) actor.startedMoving(direction);
        timeOfAction +=deltaTime;

        prevX = actor.getPosX();
        prevY = actor.getPosY();

        setPositionByDirection();

        intersection();

        if(Math.abs(duration - timeOfAction) <= 1e-5 || timeOfAction > duration){
            stop();
        }
    }

    private void intersection() {
        if (actor == null || actor.getScene() == null) return;

        if (actor.getScene().getMap().intersectsWithWall(actor)) {
            if(!(actor instanceof Bullet)) actor.setPosition(prevX, prevY);
            actor.collidedWithWall();
        }

    }

    @Override
    public void reset() {
        if(actor == null) return;
        state = false;
        timeOfAction = 0.0f;
        duration = 0.0f;
        actor.stoppedMoving();
    }

    public void stop(){
        if(actor == null) return;
        state = true;
        actor.stoppedMoving();
    }
    private void setPositionByDirection(){
        int dx = prevX + actor.getSpeed()* direction.getDx(),
            dy = prevY + actor.getSpeed()* direction.getDy();
        actor.setPosition(dx, dy);
    }
}
