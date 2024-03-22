package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.TimeBomb;
import sk.tuke.kpi.oop.game.alientools.AlienWall;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Bomb extends TimeBomb implements Usable<Alive>, Collectible {
    private final float seconds;
    public Bomb(float seconds){
        super(seconds);
        this.seconds = seconds;
    }

    @Override
    public void makeBoom(){
        Scene scene = getScene();
        if(scene == null) return;
        super.setExplosionAnimation(new Animation("sprites/large_explosion.png", 100, 100, 0.05f, Animation.PlayMode.ONCE));
        new ActionSequence<>(
            new Wait<>(seconds),
            new Invoke<>(()->{
                setAnimation(getExplosionAnimation());
                setPosition(getPosX()  + 8 - 50, getPosY() + 8 - 50);
                setExplodedState(true);

                scene.getActors().stream()
                    .filter(alive -> alive instanceof Alive && alive.intersects(this))
                    .forEach(alive -> {
                        if(alive instanceof AlienWall) ((AlienWall) alive).dieByBomb();
                        else ((Alive) alive).getHealth().drain(250);
                    });
            })
        ).scheduleFor(this);
    }

    @Override
    public void useWith(Alive actor) {
        if(actor == null) return;
        activate();
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
