package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.alientools.AlienEgg;

import java.util.Random;

public class LayingEggs extends RandomlyMoving{
    public LayingEggs(){
        super();
    }
    @Override
    public void setUp(Movable actor) {
        super.setUp(actor);

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(()->{
                    Scene scene = actor.getScene();
                    if(scene != null){
                        scene.addActor(new AlienEgg(), actor.getPosX() + actor.getWidth()/2, actor.getPosY() + actor.getHeight()/2);
                    }
                }),
                new Wait<>(new Random().nextInt(4) + 10)
            )
        ).scheduleFor(actor);
    }
}
