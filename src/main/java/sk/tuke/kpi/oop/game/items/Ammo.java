package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;

public class Ammo extends AbstractActor implements Usable<Armed>{

    public Ammo(){
        Animation animation = new Animation("sprites/ammo.png");
        setAnimation(animation);
    }

    @Override
    public void useWith(Armed actor) {
        if(actor ==  null || actor.getFirearm() == null) return;
        actor.getFirearm().reload(50);

        Scene scene = getScene();
        if(scene != null) scene.removeActor(this);
    }

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }
}
