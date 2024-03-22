package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.messages.MessageBus;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MissionImpossible implements SceneListener {
    private Ripley player;
    public static class Factory implements ActorFactory{

        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            switch(name) {
                case "access card":
                    return new AccessCard();
                case "door":
                    return new LockedDoor("locked door", Door.Orientation.VERTICAL);
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "locker":
                    return new Hammer();//Locker(1, new Hammer())
                case "ventilator":
                    return new Ventilator();
                default:
                    return null;
            }
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);
        player = scene.getFirstActorByType(Ripley.class);
        if(player == null) return;
        MovableController movableController = new MovableController(player);
        KeeperController keeperController = new KeeperController(player);
        @NotNull Disposable mController = scene.getInput().registerListener(movableController);
        @NotNull Disposable kController = scene.getInput().registerListener(keeperController);
        scene.follow(player);
        scene.getGame().pushActorContainer(player.getBackpack());
        final @NotNull Disposable[] hController = new Disposable[1];
        MessageBus messageBus = scene.getMessageBus();
        messageBus.subscribe(Door.DOOR_OPENED, (door) -> {
            hController[0] = new Loop<>(
                new ActionSequence<>(
                    new Wait<>(0.1f),
                    new Invoke<>(() -> player.getHealth().drain(1))
                )
            ).scheduleFor(player);
        });
        messageBus.subscribe(Ripley.ALIVE_ACTOR_DIED, (ripley -> {
            mController.dispose();
            kController.dispose();
            hController[0].dispose();
        }));
        messageBus.subscribe(Ventilator.VENTILATOR_REPAIRED, (ventilator -> hController[0].dispose()));

    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        if(player == null) return;
        player.showRipleyState(scene);
    }
}
