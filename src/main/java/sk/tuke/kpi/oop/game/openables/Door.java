package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    public enum Orientation{
        HORIZONTAL(true),
        VERTICAL (false);

        Orientation(boolean state) {
        }
    }

    private boolean opened;
    private final Animation animation;
    private MapTile tile1;
    private MapTile tile2;
    private Scene scene;
    private final Orientation orientation;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);

    public Door(String name, Orientation orientation){
        super(name);
        this.orientation = orientation;
        opened = false;
        scene = null;
        if(orientation == Orientation.VERTICAL){
            animation = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        } else{
            animation = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);
        }
        setAnimation(animation);
        animation.stop();
    }

     @Override
    public void open() {
        opened = true;
        animation.setPlayMode(Animation.PlayMode.ONCE);
        animation.play();
        tile1.setType(MapTile.Type.CLEAR);
        tile2.setType(MapTile.Type.CLEAR);
        Scene scene = getScene();
        if(scene != null){
            scene.getMessageBus().publish(DOOR_OPENED, this);
        }
    }

    @Override
    public void close() {
        opened = false;
        animation.setPlayMode(Animation.PlayMode.ONCE_REVERSED);
        animation.play();
        tile1.setType(MapTile.Type.WALL);
        tile2.setType(MapTile.Type.WALL);
        if(scene != null){
            scene.getMessageBus().publish(DOOR_CLOSED, this);
        }
    }

    @Override
    public boolean isOpen() {
        return opened;
    }

    @Override
    public void useWith(Actor actor) {
        if(opened) close();
        else open();
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.scene = scene;
        tile1 = scene.getMap().getTile(getPosX()/16, getPosY()/16);

        if(orientation == Orientation.VERTICAL){
            tile2 = scene.getMap().getTile(getPosX()/16, getPosY()/16 + 1);
        }
        else{
            tile2 = scene.getMap().getTile(getPosX()/16 + 1, getPosY()/16);
        }

        tile1.setType(MapTile.Type.WALL);
        tile2.setType(MapTile.Type.WALL);
    }
}
