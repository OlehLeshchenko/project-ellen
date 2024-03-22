package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {

    public Helicopter(){
        Animation animation = new Animation("sprites/heli.png", 64, 64, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
    }

    private void activateHelicopter(){
        Player player = getPlayer();
        if(player == null) return;

        if(this.intersects(player)){
            player.setEnergy(player.getEnergy() - 1);
            return;
        }

        int x, y;

        if(this.getPosX() > player.getPosX()) x = this.getPosX() - 1;
        else x = this.getPosX() + 1;

        if(this.getPosY() > player.getPosY()) y = this.getPosY() - 1;
        else y = this.getPosY() + 1;

        this.setPosition(x, y);

    }

    private Player getPlayer(){
        Scene scene = this.getScene();
        if(scene == null) return null;
        return (Player)scene.getFirstActorByName("Player");
    }
    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::activateHelicopter)).scheduleFor(this);
    }

}
