package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.awt.geom.Rectangle2D;
//import sk.tuke.kpi.oop.game.characters.Ripley;
//
//import java.awt.geom.Rectangle2D;

public class SpawnPoint extends AbstractActor {
    private int alienCount;
    private Disposable loop;
    private Disposable loop2;
    public SpawnPoint(int alienCount){
        Animation animation = new Animation("sprites/spawn.png");
        setAnimation(animation);
        this.alienCount = alienCount;
    }

    public void spawningAliens(@NotNull Scene scene){
        loop = new Loop<>(
            new ActionSequence<>(
                new Invoke<>(()->{
                    if(alienCount > 0){
                        Alien alien = new Alien(100, new RandomlyMoving());
                        scene.addActor(alien, getPosX(), getPosY());
                        alienCount--;
                    }
                    else loop.dispose();
                }),
                new Wait<>(2.9f)
            )
        ).scheduleFor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        Rectangle2D.Float spawnPointArea = new Rectangle2D.Float(getPosX() + 16 - 50, getPosY() + 16 - 50, 100, 100);

        Ripley player = scene.getFirstActorByType(Ripley.class);
        loop2 = new Loop<>(
            new Invoke<>(()->{
                boolean check = player != null && spawnPointArea.intersects(player.getPosX(), player.getPosY(), 32, 32);
                if(check){
                    spawningAliens(scene);
                    loop2.dispose();
                }
                spawningAliens(scene);
                loop2.dispose();
            })
        ).scheduleFor(this);
    }
}
