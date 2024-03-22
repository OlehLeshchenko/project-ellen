package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.messages.MessageBus;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.alientools.AlienWall;
import sk.tuke.kpi.oop.game.behaviours.LayingEggs;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.*;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class MyLevel implements SceneListener {
    private Ripley player;
    private @NotNull Disposable mController;
    private @NotNull Disposable kController;
    private @NotNull Disposable sController;
    public static class Factory implements ActorFactory{
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            switch(name) {
                case "alien":
                    return new Alien(100, new RandomlyMoving());
                case "alien mother":
                    return new MotherAlien(new LayingEggs());
                case "alien wall":
                    return new AlienWall(type);
                case "locker":
                    return new Locker(1, new Bomb(4));
                case "locked door":
                    return new LockedDoor("locked door", Door.Orientation.VERTICAL);
                case "exit locked door":
                    return new LockedDoor("exit door", Door.Orientation.HORIZONTAL);
                case "ellen":
                    return new Ripley();
                case "crewmate":
                    return new Crewmate(new RandomlyMoving());
                default:
                    return help(name);
            }
        }

        public @Nullable Actor help(@Nullable String name){
            switch(name) {
                case "ventilator":
                    Ventilator ventilator = new Ventilator();
                    ventilator.repair();
                    return ventilator;
                case "ammo":
                    return new Ammo();
                case "energy":
                    return new Energy();
                case "computer":
                    return new Computer();
                case "access card":
                    return new AccessCard();
                case "exit access card":
                    return new ExitAccessCard();
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
        ShooterController shooterController = new ShooterController(player);
        mController = scene.getInput().registerListener(movableController);
        kController = scene.getInput().registerListener(keeperController);
        sController = scene.getInput().registerListener(shooterController);
        scene.follow(player);
        scene.getGame().pushActorContainer(player.getBackpack());
        MessageBus messageBus = scene.getMessageBus();
        player.getBackpack().add(new Bomb(5));
        messageBus.subscribe(Ripley.RIPLEY_DIED, (actor -> {
            gameOver(scene);
        }));

        scene.getMessageBus().subscribe(Alive.ALIVE_ACTOR_DIED, actor -> {
            int alienCounter = (int)scene.getActors().stream().
                filter(actor1 -> actor1 instanceof Alien).
                count();
            if(alienCounter == 0) gameOk(scene);
        });

        scene.getMessageBus().subscribe(Door.DOOR_OPENED, actor -> {
            if(actor.getName().equals("exit door")) gameOk(scene);
        });

        scene.getMessageBus().subscribe(Crewmate.CREWMATE_DIED, actor -> {
            int memberCounter = (int)scene.getActors().stream().
                filter(actor1 -> actor1 instanceof Crewmate).
                count();
            if(memberCounter == 0) gameOver(scene);
        });
    }

    public void gameOver(@NotNull Scene scene){
        disposeControllers();
        new ActionSequence<>(
            new Wait<>(3),
            new Invoke<>(()->{
                scene.addActor(new LevelFailed(), player.getPosX() + 16 - 144, player.getPosY() + 16 - 64);
            }),
            new Wait<>(4),
            new Invoke<>(()->{
                scene.getGame().stop();
            })
        ).scheduleFor(player);
    }

    public void gameOk(@NotNull Scene scene){
        disposeControllers();
        new ActionSequence<>(
            new Wait<>(2),
            new Invoke<>(()->{
                scene.addActor(new LevelComplete(), player.getPosX() + 16 - 144, player.getPosY() + 16 - 64);
            }),
            new Wait<>(4),
            new Invoke<>(()->{
                scene.getGame().stop();
            })
        ).scheduleFor(player);
    }


    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        if(player == null) return;
        player.showRipleyState(scene);
    }

    public void disposeControllers(){
        mController.dispose();
        kController.dispose();
        sController.dispose();
    }
}
