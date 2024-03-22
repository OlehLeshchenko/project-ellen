package sk.tuke.kpi.oop.game.alientools;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Health;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class AlienWall extends AbstractActor implements Alive, Enemy {
    private final List<MapTile> tiles = new ArrayList<>();
    private final Health health;

    private boolean destroyedByBomb = false;

    public AlienWall(String type){
        this.health = new Health(250);
        Animation animation = new Animation(resoursesPath(type));
        setAnimation(animation);
        health.onFatigued(() -> {
            System.out.println("sdfdsDSfqeqw1213213");
            die();
        });
    }

    private String resoursesPath(String type){
        String result;
        switch (type){
            case "vertical":
                result = "v_alien_wall.png";
                break;
            case "horizontal":
                result = "h_alien_wall.png";
                break;
            case "right top":
                result = "rt_alien_wall.png";
                break;
            case "right bottom":
                result = "rb_alien_wall.png";
                break;
            case "left top":
                result = "lt_alien_wall.png";
                break;
            case "left bottom":
                result = "lb_alien_wall.png";
                break;
            default:
                result = "alien.png";
                break;
        }

        return "sprites/" + result;
    }

    public void die(){
        Scene scene = getScene();
        if(scene != null){
            scene.removeActor(this);
            tiles.forEach(mapTile -> mapTile.setType(MapTile.Type.CLEAR));
        }
    }

    public void dieByBomb(){
        destroyedByBomb = true;
        Scene scene = getScene();
        if(scene == null) return;

        Animation animation = new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE, Color.WHITE, 2);
        setAnimation(animation);

        new When<>(
            () -> animation.getCurrentFrameIndex() == animation.getFrameCount() - 6,
            new Invoke<>(() -> {
                scene.getActors().stream().
                    filter(actor -> {
                        Rectangle2D.Float area = new Rectangle2D.Float(getPosX() + 16 -20, getPosY() + 16 - 20,  40, 40);
                        return actor instanceof AlienWall && area.intersects(actor.getPosX(), actor.getPosY(), 32, 32);
                    }).
                    forEach(actor -> {
                        if(!((AlienWall) actor).isDestroyedByBomb()) ((AlienWall) actor).dieByBomb();
                    });
            })
        ).scheduleFor(this);

        new When<>(
            () -> animation.getCurrentFrameIndex() == animation.getFrameCount() - 1,
            new Invoke<>(this::die)
        ).scheduleFor(this);

    }

    public boolean isDestroyedByBomb() {
        return destroyedByBomb;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        tiles.add(scene.getMap().getTile(getPosX()/16, getPosY()/16));
        tiles.add(scene.getMap().getTile(getPosX()/16, getPosY()/16 + 1));
        tiles.add(scene.getMap().getTile(getPosX()/16 + 1, getPosY()/16));
        tiles.add(scene.getMap().getTile(getPosX()/16 + 1, getPosY()/16 + 1));

        tiles.forEach(mapTile -> mapTile.setType(MapTile.Type.WALL));
    }
}
