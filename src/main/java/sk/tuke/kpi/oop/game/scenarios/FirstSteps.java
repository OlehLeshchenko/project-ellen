package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener {
    private Ripley player;
    @Override
    public void sceneInitialized(@NotNull Scene scene){
        SceneListener.super.sceneInitialized(scene);
        player = new Ripley();
        scene.addActor(player, 0, 0);

        Backpack backpack = player.getBackpack();
        backpack.add(new Hammer());
        backpack.add( new FireExtinguisher());
        backpack.add(new Wrench());
        backpack.shift();
        scene.getGame().pushActorContainer(backpack);

        MovableController movableController = new MovableController(player);
        KeeperController keeperController = new KeeperController(player);
        scene.getInput().registerListener(movableController);
        scene.getInput().registerListener(keeperController);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        player.showRipleyState(scene);
    }
}
