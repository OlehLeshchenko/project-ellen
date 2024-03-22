package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChainBomb extends TimeBomb{
    public ChainBomb(float seconds) {
        super(seconds);
    }
    @Override
    public void makeBoom(){
        Scene scene = getScene();
        if(scene == null) return;

        Animation explosion  = new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(explosion);

        Ellipse2D.Float coverageArea = new Ellipse2D.Float(this.getPosX()-42, this.getPosY()-42, 100, 100);

        Set<ChainBomb> bombs = new HashSet<>();
        List<Actor> actors = scene.getActors();
        for(Actor actor : actors) {
            Rectangle2D.Float newBomb = new Rectangle2D.Float(actor.getPosX(), actor.getPosY(), 16, 16);

            if(actor.getClass() == ChainBomb.class && this != actor && coverageArea.intersects(newBomb)){
                bombs.add((ChainBomb)actor);
            }
        }

        for(ChainBomb bomb : bombs){
            bomb.activate();
        }

    }
}
