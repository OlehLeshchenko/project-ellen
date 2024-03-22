package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.List;

public class Fire<T extends Armed> extends AbstractAction<T> {

    public Fire() {}

    @Override
    public void execute(float deltaTime) {
        if(startChecking()){
            setDone(true);
            return;
        }
        setDone(true);
        Scene scene = getActor().getScene();

        try {
            Armed player = getActor();
            if(player == null || getActor().getFirearm() == null) return;

            Fireable bullet = getActor().getFirearm().fire();

            if(bullet != null){
                if(player instanceof Ripley){
                    float angle = player.getAnimation().getRotation();
                    Direction direction = Direction.fromAngle(angle);

                    scene.addActor(bullet, player.getPosX() + 8 + direction.getDx()*10, player.getPosY() + 8 + direction.getDy()*10);

                    Move<Fireable> move = new Move<>(direction, Float.MAX_VALUE);
                    move.scheduleFor(bullet);
                }
                else alienFire(scene, player);
            }


        } catch (Exception exception) {
            scene.getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
        }
    }
    public boolean startChecking(){return getActor() == null || getActor().getScene() == null || isDone();}

    public void alienFire(@NotNull Scene scene, Armed player){
        int index = 1;
        List<Direction> directionValues = List.of(Direction.values());
        Fireable bullet = getActor().getFirearm().fire();
        while (bullet != null && index < 8){
            Direction currentDirection = directionValues.get(index);
            index++;

            int indentationX = (currentDirection != Direction.SOUTH && currentDirection != Direction.NORTH &&
                currentDirection != Direction.NONE) ?
                (player.getWidth()/2 + 5) * currentDirection.getDx() : 0;

            int indentationY = (currentDirection == Direction.SOUTH || currentDirection == Direction.NORTH) ?
                (player.getHeight()/2 + 5) * currentDirection.getDy() : 0;

            scene.addActor(bullet, player.getPosX() + player.getWidth()/2 + indentationX, player.getPosY() + player.getHeight()/2 + indentationY);

            Move<Fireable> move = new Move<>(currentDirection, Float.MAX_VALUE);
            move.scheduleFor(bullet);
        }
    }
}
