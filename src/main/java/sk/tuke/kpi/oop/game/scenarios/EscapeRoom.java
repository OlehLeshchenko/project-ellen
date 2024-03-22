package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.messages.MessageBus;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.MotherAlien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class EscapeRoom implements SceneListener {
    private Ripley player;
    public static class Factory implements ActorFactory{
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            switch(name) {
                case "alien":
                    return new Alien(100,alienBehaviour(type));
                case "alien mother":
                    return new MotherAlien(alienBehaviour(type));
                case "ammo":
                    return new Ammo();
                case "back door":
                    return new Door("back door", Door.Orientation.HORIZONTAL);
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "exit door":
                    return new Door("exit door", Door.Orientation.VERTICAL);
                case "front door":
                    return new Door("front door", Door.Orientation.VERTICAL);
//                case "spawn point":
//                    return new SpawnPoint(3);
                default:
                    return null;
            }
        }
    }

    public static Behaviour<Movable> alienBehaviour(String type){
        if(type.equals("waiting1")){
            return new Observing<>(
                Door.DOOR_OPENED,
                door -> door.getName().equals("front door"),
                new RandomlyMoving()
            );
        }
        else if(type.equals("waiting2")){
            return new Observing<>(
                Door.DOOR_OPENED,
                door -> door.getName().equals("back door"),
                new RandomlyMoving()
            );
        }
        else return new RandomlyMoving();
    }

    @Override
    public void sceneCreated(@NotNull Scene scene) {
        SceneListener.super.sceneCreated(scene);
        scene.getMessageBus().subscribe(World.ACTOR_ADDED_TOPIC, actor -> {});
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);

        player = scene.getFirstActorByType(Ripley.class);
        if(player == null) return;
        MovableController movableController = new MovableController(player);
        KeeperController keeperController = new KeeperController(player);
        ShooterController shooterController = new ShooterController(player);
        @NotNull Disposable mController = scene.getInput().registerListener(movableController);
        @NotNull Disposable kController = scene.getInput().registerListener(keeperController);
        @NotNull Disposable sController = scene.getInput().registerListener(shooterController);
        scene.follow(player);
        scene.getGame().pushActorContainer(player.getBackpack());
        MessageBus messageBus = scene.getMessageBus();
        messageBus.subscribe(Ripley.RIPLEY_DIED, (actor -> {
            mController.dispose();
            kController.dispose();
            sController.dispose();
        }));

        new Observing<>(
            Door.DOOR_OPENED,
            door -> door.getName().equals("exit door"),
            scene::removeActor
        ).setUp(player);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        if(player == null) return;
        player.showRipleyState(scene);
    }
}
