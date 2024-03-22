package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm{
    private int bulletCount;
    private final int maxBulletCount;
    public Firearm(int bulletCount){
        this.bulletCount = bulletCount;
        this.maxBulletCount = bulletCount;
    }
    public Firearm(int bulletCount, int maxBulletCount){
        this.bulletCount = bulletCount;
        this.maxBulletCount = maxBulletCount;
    }

    public int getAmmo(){
        return bulletCount;
    }

    public void reload(int newAmmo){
        bulletCount = Math.min(bulletCount + newAmmo, maxBulletCount);
    }

    protected abstract Fireable createBullet();

    public Fireable fire() {
        if(bulletCount == 0) return null;
        bulletCount--;
        return createBullet();
    }

}
