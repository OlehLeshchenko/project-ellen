package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.Map;

public class KeeperController implements KeyboardListener{

    private final Keeper player;
    private final Map<Input.Key, Integer> keeperMap = Map.ofEntries(
        Map.entry(Input.Key.ENTER, 0),
        Map.entry(Input.Key.BACKSPACE, 1),
        Map.entry(Input.Key.S, 2),
        Map.entry(Input.Key.U, 3),
        Map.entry(Input.Key.B, 4)
    );

    public KeeperController(Keeper player) {
        this.player = player;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if(keeperMap.containsKey(key)){
            switch(keeperMap.get(key)) {
                case 0:
                    Take<Keeper> take = new Take<>();
                    take.scheduleFor(player);
                    break;
                case 1:
                    Drop<Keeper> drop = new Drop<>();
                    drop.scheduleFor(player);
                    break;
                case 2:
                    Shift<Keeper> shift = new Shift<>();
                    shift.scheduleFor(player);
                    break;
                case 3:
                    Usable<?> mediatingActor = (Usable<?>) player.getScene().getActors().stream()
                        .filter(actor -> actor instanceof Usable<?> && player.intersects(actor))
                        .findFirst()
                        .orElse(null);
                    if(mediatingActor == null) break;
                    Use<?> use = new Use<>(mediatingActor);
                    use.scheduleForIntersectingWith(player);
                    break;
                case 4:
                    Collectible item = player.getBackpack().peek();
                    if(!(item instanceof Usable)) return;
                    Use<?> use1 = new Use<>((Usable<?>) item);
                    use1.scheduleForIntersectingWith(player);
                    break;
                default:
                    break;
            }
        }

    }
}
