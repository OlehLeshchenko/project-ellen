package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private final float seconds;
    private boolean isActivated;
    private boolean isExploded;
    private Animation activatedBomb;
    private Animation explosion;

    public TimeBomb(float seconds){
        this.seconds = seconds;
        this.isActivated = false;
        this.isExploded = false;
        Animation bomb = new Animation("sprites/bomb.png");
        float frameDuration = seconds / 6;
        activatedBomb = new Animation("sprites/bomb_activated.png", 16, 16, frameDuration, Animation.PlayMode.ONCE);
        setAnimation(bomb);
    }
    public boolean isActivated(){return isActivated;}
    public boolean isExploded(){return isExploded;}
    public void setExplodedState(boolean isExploded){
        this.isExploded = isExploded;
    }

    public void makeBoom(){
        setExplosionAnimation(new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE));
        new ActionSequence<>(
            new Wait<>(seconds),
            new Invoke<>(()->{
                setAnimation(explosion);
                this.isExploded = true;
            })
        ).scheduleFor(this);
    }

    public void activate(){
        if(this.isActivated() || this.isExploded()) return;

        Scene scene = this.getScene();
        if(scene == null) return;

        this.isActivated = true;
        setAnimation(activatedBomb);

        makeBoom();

        new When<>(
            () -> this.getAnimation().getCurrentFrameIndex() == explosion.getFrameCount() - 1,
            new Invoke<>(() -> scene.removeActor(this))
        ).scheduleFor(this);
    }

    public void setExplosionAnimation(Animation animation){
        explosion = animation;
    }

    public Animation getExplosionAnimation(){
        return explosion;
    }


}
