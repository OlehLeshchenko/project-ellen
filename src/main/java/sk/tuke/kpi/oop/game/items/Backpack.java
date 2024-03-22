package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {

    private final String name;
    private final int capacity;
    private final List<Collectible> list;

    public Backpack(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.list = new ArrayList<>(capacity);
    }
    @Override
    public @NotNull List<Collectible> getContent() {
        return List.copyOf(list);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(getSize() == getCapacity()) throw new IllegalStateException(String.format("%s is full!", name));
        else list.add(actor);
//        try{
//            list.add(actor);
//        }catch (Exception ex){
//            throw new IllegalStateException(String.format("%s is full!", name));
//        }
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        list.remove(actor);
    }

    @Override
    public @Nullable Collectible peek() {
        if(getSize() == 0) return null;
        return list.get(getSize() - 1);
    }

    @Override
    public void shift() {
        Collections.rotate(list, 1);
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return list.iterator();
    }
}
