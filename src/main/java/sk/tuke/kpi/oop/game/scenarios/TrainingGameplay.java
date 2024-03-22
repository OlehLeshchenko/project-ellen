package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.Teleport;

public class TrainingGameplay extends Scenario {
    @Override
    public void setupPlay(@NotNull Scene scene) {
//        Reactor reactor = new Reactor();
//        scene.addActor(reactor, 147, 97);
//        reactor.turnOn();
//        Cooler cooler = new Cooler(reactor);
//        scene.addActor(cooler, 249, 139);
//        new ActionSequence<>(
//            new Wait<>(5),
//            new Invoke<>(cooler::turnOn)
//        ).scheduleFor(cooler);
        Teleport Tel3 = new Teleport(null);
        scene.addActor(Tel3);
        Tel3.setPosition(65, 113);

        Teleport Tel2 = new Teleport(Tel3);
        scene.addActor(Tel2);
        Tel2.setPosition(260, 269);

        Teleport Tel1 = new Teleport(Tel2);
        scene.addActor(Tel1);
        Tel1.setPosition(65, 269);

    }

}
