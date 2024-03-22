package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.Movable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {

    private final Movable actor;
    private Move<Movable> move = null;
    private Set<Direction> list = new HashSet<>();
    private static final Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );

    public MovableController(Movable actor) {
        this.actor = actor;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if(keyDirectionMap.containsKey(key)){
            if(move != null) move.stop();
            list.add(keyDirectionMap.get(key));
            move = new Move<>(updateDirection(), Float.MAX_VALUE);
            move.scheduleFor(actor);
        }
    }
    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if(keyDirectionMap.containsKey(key)){
            if(move != null) move.stop();
            list.remove(keyDirectionMap.get(key));
            if(list.isEmpty()) return;
            move = new Move<>(updateDirection(), Float.MAX_VALUE);
            move.scheduleFor(actor);
        }
    }

    private Direction updateDirection(){
        Direction newDirection = Direction.NONE;
        for(Direction direction: list){
            newDirection = newDirection.combine(direction);
        }
        return newDirection;
    }
}
