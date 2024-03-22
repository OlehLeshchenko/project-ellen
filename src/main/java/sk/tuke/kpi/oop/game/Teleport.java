package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;


import java.util.List;

public class Teleport extends AbstractActor {

    private Teleport Destination;
    private boolean teleportedArea;

    public Teleport(Teleport Destination){
        this.Destination = null;
        this.teleportedArea = false;
        setDestination(Destination);
        Animation animation = new Animation("sprites/lift.png");
        setAnimation(animation);
    }

    public void setDestination(Teleport Destination) {
        if (this != Destination && Destination != null) {
            this.Destination = Destination;
        }
    }

    public Teleport getDestination(){
        return this.Destination;
    }

    public boolean isTeleportedArea(){
        return teleportedArea;
    }

    public void setTeleportedArea(boolean state){
        this.teleportedArea = state;
    }

    private boolean intersectsCenter(Player player){
        int centerX = player.getPosX() + 16;
        int centerY = player.getPosY() + 16;

        return this.getPosX() <= centerX && (this.getPosX() + 24) >= centerX &&
            this.getPosY() <= centerY && (this.getPosY() + 24) >= centerY;
    }

    public void teleport(Player player, Scene scene){
        List<Actor> actors = scene.getActors();
        Teleport teleportThere = null;

        for(Actor actor : actors){
            if(actor.getClass() == Teleport.class && ((Teleport) actor).getDestination() == this){
                teleportThere = (Teleport) actor;
                break;
            }
        }

        if(teleportThere == null) return;

        if(teleportThere.intersectsCenter(player) && !teleportThere.isTeleportedArea()){
            teleportPlayer(player);
        }
        else if(!teleportThere.intersects(player) && teleportThere.isTeleportedArea()){
            teleportThere.setTeleportedArea(false);
        }
    }

    public void teleportPlayer(Player player){
        this.setTeleportedArea(true);
        player.setPosition(this.getPosX() + 8, this.getPosY() + 8);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(()->teleport(scene.getFirstActorByType(Player.class), scene))).scheduleFor(this);
    }

}
