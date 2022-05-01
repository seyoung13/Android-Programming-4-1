package kr.ac.kpu.sgp02.termproject.framework;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class ObjectPool {
    private static final String LOG_TAG = "ObjectPool : ";
    private static HashMap<Class, ArrayList<GameObject>> objectPool = new HashMap<>();

    public static void clear() {
        objectPool.clear();
    }

    // 사용이 끝난 오브젝트를 오브젝트 풀에 집어넣는다.
    public static void add(GameObject object) {
        Class clazz = object.getClass();

        ArrayList<GameObject> objects = objectPool.get(clazz);

        // 오브젝트 풀에 처음 들어온 클래스라면 해쉬맵에 추가한다.
        if(object == null) {
            objects = new ArrayList<>();
            objectPool.put(clazz, objects);
        }

        // 오브젝트가 중복되어서 들어가는 일이 없도록 한다.
        // indexOf는 오브젝트가 리스트에 있으면 인덱스를, 없으면 -1을 반환한다.
        if (objects.indexOf(object) >= 0) {
            return;
        }

        objects.add(object);
    }

    // 오브젝트 풀에서 오브젝트를 재활용한다.
    public static GameObject get(Class clazz) {
        ArrayList<GameObject> objects = objectPool.get(clazz);

        if(objects == null || objects.size() > 0){
            Log.d(LOG_TAG, clazz.getSimpleName()+" list is null.");
            return null;
        }

        // 첫번째 오브젝트를 리스트에서 제거하고 반환한다.
        return objects.remove(0);
    }
}
