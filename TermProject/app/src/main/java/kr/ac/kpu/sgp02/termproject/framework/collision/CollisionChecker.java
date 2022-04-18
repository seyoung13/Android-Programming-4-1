package kr.ac.kpu.sgp02.termproject.framework.collision;

public class CollisionChecker {
//cannot resolve T
//    public static<T extends Collider> boolean collides(Collider a, T b){
//        return a.intersects(b);
//    };

    public static boolean collides(Collider a, BoxCollider b){
        return a.intersects(b);
    };

    public static boolean collides(Collider a, CircleCollider b){
        return a.intersects(b);
    };
}
