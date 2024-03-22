package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Ripley> {
    private int uses;
    private final Collectible item;

    public Locker(int uses, Collectible item){
        this.uses = uses;
        this.item = item;
        Animation animation = new Animation("sprites/locker.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(Ripley actor) {
        Scene scene = actor.getScene();
        if(scene == null || uses == 0 || item == null) return;
        scene.addActor(item, getPosX() , getPosY());
        uses--;
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}
