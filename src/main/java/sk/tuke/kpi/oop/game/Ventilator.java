package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {
    private final Animation animation;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("Ventilator repaired", Ventilator.class);

    public Ventilator(){
        animation = new Animation("sprites/ventilator.png", 32, 32, 0.1f, Animation.PlayMode.LOOP);
        setAnimation(animation);
        animation.stop();
    }
    @Override
    public boolean repair() {
        Scene scene = getScene();
        if(scene != null){
            scene.getMessageBus().publish(VENTILATOR_REPAIRED, this);
        }
        animation.play();
        return true;
    }
}
