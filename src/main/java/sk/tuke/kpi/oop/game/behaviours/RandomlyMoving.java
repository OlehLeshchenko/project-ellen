package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable>{
    private Move<Movable> move = null;

    public RandomlyMoving(){}

    @Override
    public void setUp(Movable actor) {
        if (actor == null) return;
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(() ->{
                    moving(actor);
                }),
                new Wait<>(new Random().nextInt(3) + new Random().nextFloat() * 3)
            )
        ).scheduleFor(actor);
    }

    public Move<Movable> randomMove(){
        int random = new Random().nextInt(7) + 1;
        Direction direction = Direction.values()[random];
        return new Move<>(direction, Float.MAX_VALUE);
    }

    public void moving(Movable actor){
        if(move != null) move.stop();
        move = randomMove();
        move.scheduleFor(actor);
    }

}
