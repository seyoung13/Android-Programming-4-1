package kr.ac.kpu.sgp02.termproject.framework.collision;

import android.graphics.Canvas;

import java.net.CacheRequest;

import kr.ac.kpu.sgp02.termproject.framework.interfaces.Collidable;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.monster.Monster;
import kr.ac.kpu.sgp02.termproject.game.projectile.Projectile;
import kr.ac.kpu.sgp02.termproject.game.projectile.SiegeSplash;
import kr.ac.kpu.sgp02.termproject.game.tower.Tower;

public class CollisionChecker implements GameObject {

    private boolean collides(Collider a, Collider b) {
        return a.intersects(b);
    }

    private void checkCollision(Collidable object1, Collider object1Collider, Collidable object2, Collider object2Collider) {
        if (collides(object1Collider, object2Collider)) {
            if (object1Collider.overlappedColliders.contains(object2Collider)) {
                object1.onStayOverlap((GameObject) object2);
            }
            else {
                object1Collider.overlappedColliders.add(object2Collider);
                object1.onBeginOverlap((GameObject) object2);
            }

            if (object2Collider.overlappedColliders.contains(object1Collider)) {
                object2.onStayOverlap((GameObject) object1);
            }
            else {
                object2Collider.overlappedColliders.add(object1Collider);
                object2.onBeginOverlap((GameObject) object1);
            }
        }
        else {
            if (object1Collider.overlappedColliders.contains(object2Collider)) {
                object1Collider.overlappedColliders.remove(object2Collider);
                object1.onEndOverlap((GameObject) object2);
            }

            if (object2Collider.overlappedColliders.contains(object1Collider)) {
                object2Collider.overlappedColliders.remove(object1Collider);
                object2.onEndOverlap((GameObject) object1);
            }
        }
    }

    @Override
    public void update(float deltaSecond) {
        DefenseGame game = DefenseGame.getInstance();

        for (GameObject o1 : game.getObjectsAt(DefenseGame.Layer.monster)) {
            if(!(o1 instanceof Monster))
                continue;

            Monster monster = (Monster) o1;
            BoxCollider monsterCollider = monster.getCollider(BoxCollider.class);

            // 대미지를 줄 수 있는 객체와 몬스터의 충돌을 확인한다.
            for(GameObject o2 : game.getObjectsAt(DefenseGame.Layer.damageCauser)) {
                if(o2 instanceof Projectile){
                    Projectile projectile = (Projectile) o2;

                    checkCollision(monster, monsterCollider, projectile, projectile.getCollider(CircleCollider.class));
                }

                if(o2 instanceof SiegeSplash) {
                    SiegeSplash splash = (SiegeSplash) o2;

                    checkCollision(monster, monsterCollider, splash, splash.getCollider(CircleCollider.class));
                }
            }

            // 타워 사거리 내 몬스터가 있는지 확인한다.
            for(GameObject o2 : game.getObjectsAt(DefenseGame.Layer.tower)) {
                if(!(o2 instanceof Tower))
                    continue;

                Tower tower = (Tower) o2;

                checkCollision(monster, monsterCollider, tower, tower.getCollider(CircleCollider.class));
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
