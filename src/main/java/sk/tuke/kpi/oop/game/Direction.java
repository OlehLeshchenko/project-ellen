package sk.tuke.kpi.oop.game;

import java.util.List;
import java.util.Map;

public enum Direction {
    NONE(0, 0),
    NORTH(0, 1),
    SOUTH(0, -1),
    EAST(1, 0),
    WEST(-1, 0),
    NORTHEAST(1, 1),
    NORTHWEST(-1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1);
    private final int dx;
    private final int dy;

    private static final Map<List<Integer>, Float> angleMap = Map.ofEntries(
        Map.entry(List.of(0, 1), 0f),
        Map.entry(List.of(1, 1), 315f),
        Map.entry(List.of(1, 0), 270f),
        Map.entry(List.of(1, -1), 225f),
        Map.entry(List.of(0, -1), 180f),
        Map.entry(List.of(-1, -1), 135f),
        Map.entry(List.of(-1, 0), 90f),
        Map.entry(List.of(-1, 1), 45f)
    );

    Direction(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public float getAngle(){
        return getAngleFromCoordinates(dx, dy);
    }

    public float getAngleFromCoordinates(int dx, int dy){
        List<Integer> coordinates = List.of(dx,dy);
        if(!angleMap.containsKey(coordinates)) return -1;

        return angleMap.get(coordinates);
    }

    public static Direction fromAngle(float angle){
        switch ((int) angle){
            case 0:
                return NORTH;
            case 315:
                return NORTHEAST;
            case 270:
                return EAST;
            case 225:
                return SOUTHEAST;
            case 180:
                return SOUTH;
            case 135:
                return SOUTHWEST;
            case 90:
                return WEST;
            case 45:
                return NORTHWEST;
            default:
                return NONE;
        }
    }

    public Direction combine(Direction other){
        int newDx = (this.dx == other.getDx()) ? this.dx : this.dx + other.getDx(),
            newDy = (this.dy == other.getDy()) ? this.dy : this.dy + other.getDy();
        float angle = getAngleFromCoordinates(newDx, newDy);
        return fromAngle(angle);
    }
}
