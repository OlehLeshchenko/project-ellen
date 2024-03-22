package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Health;

public class Energy extends AbstractActor implements Usable<Alive>{

    public Energy(){
        Animation animation = new Animation("sprites/energy.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(Alive actor) {
        if(actor ==  null) return;
        Health health = actor.getHealth();
        health.restore();
        Scene scene = getScene();
        if(scene != null) scene.removeActor(this);
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }


}
