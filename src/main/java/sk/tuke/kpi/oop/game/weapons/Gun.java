package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm {


    public Gun(int bulletCount) {
        super(bulletCount);
    }

    public Gun(int bulletCount, int maxBulletCount) {
        super(bulletCount, maxBulletCount);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
