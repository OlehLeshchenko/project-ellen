package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private final int speed;
    private final Health health;
    private final Backpack backpack;
    private Firearm weapon;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("Ripley died", Ripley.class);

    public Ripley(){
        super("Ellen");
        this.speed = 2;
        this.health = new Health(100);
        this.backpack = new Backpack("Ripley's backpack", 10);
        this.weapon = new Gun(200, 500);
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
        Animation animation = new Animation("sprites/player.png", 32, 32, 0.1f/speed, Animation.PlayMode.LOOP_PINGPONG, Color.WHITE, 1, (float) direction.getAngle());
        setAnimation(animation);
    }

    @Override
    public void stoppedMoving() {
        Movable.super.stoppedMoving();
        this.getAnimation().stop();
    }

    @Override
    public Backpack getBackpack() {
        return backpack;
    }

    public void die(){
        Animation animation = new Animation("sprites/player_die.png", 32, 32, 0.1f/speed, Animation.PlayMode.ONCE);
        setAnimation(animation);
        Scene scene = getScene();
        if(scene != null){
            scene.getMessageBus().publish(RIPLEY_DIED, this);
        }
    }

    public void showRipleyState(@NotNull Scene scene){
        int windowHeight = scene.getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        Overlay overlay = scene.getGame().getOverlay();
        overlay.drawText("| Energy: " + health.getValue() + "| Ammo: " + getFirearm().getAmmo(), 110, yTextPos);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Firearm getFirearm() {
        return weapon;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.weapon = weapon;
    }
}
