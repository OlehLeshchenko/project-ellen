package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;

public class Hammer extends BreakableTool<Repairable> implements Collectible{

    public Hammer(){
        super(1);
        Animation animation = new Animation("sprites/hammer.png");
        setAnimation(animation);
    }

    public Hammer(int remainingUses) {
        super(remainingUses);
    }

    public void useWith(Repairable repairable ) {
        if(repairable == null) return;
        if(repairable.repair()) super.useWith(repairable);
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}
