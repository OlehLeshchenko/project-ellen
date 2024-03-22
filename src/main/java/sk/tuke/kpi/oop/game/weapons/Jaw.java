package sk.tuke.kpi.oop.game.weapons;

public class Jaw extends Firearm {


    public Jaw(int bulletCount) {
        super(bulletCount);
    }

    public Jaw(int bulletCount, int maxBulletCount) {
        super(bulletCount, maxBulletCount);
    }

    @Override
    protected Fireable createBullet() {
        return new Acid();
    }
}
