package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer{

    public Mjolnir(){
        super(4);
        Animation animation = new Animation("sprites/hammer.png");
        setAnimation(animation);
    }

}
