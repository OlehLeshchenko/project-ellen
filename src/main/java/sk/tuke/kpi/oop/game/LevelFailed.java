package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class LevelFailed extends AbstractActor {
    public LevelFailed(){
        Animation animation = new Animation("sprites/popup_level_failed.png");
        setAnimation(animation);
    }
}
