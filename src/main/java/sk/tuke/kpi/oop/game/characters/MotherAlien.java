package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class MotherAlien extends Alien{
    public MotherAlien(Behaviour<? super Alien> behaviour){
        super(500, behaviour);
        Animation animation = new Animation("sprites/mother.png", 112, 162, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
    }




}
