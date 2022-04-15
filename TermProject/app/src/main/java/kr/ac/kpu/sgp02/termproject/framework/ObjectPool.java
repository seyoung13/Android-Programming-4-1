package kr.ac.kpu.sgp02.termproject.framework;

import java.util.ArrayList;

public class ObjectPool<GameObject> {
    ArrayList<GameObject> pool;

    public ObjectPool(ArrayList<GameObject> objects) {
        pool = objects;
    }
}
