package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class LevelComplete extends AbstractActor {
    public LevelComplete(){
        Animation animation = new Animation("sprites/popup_level_done.png");
        setAnimation(animation);
    }
}
