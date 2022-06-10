package kr.ac.kpu.sgp02.termproject.framework.pool;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import kr.ac.kpu.sgp02.termproject.framework.view.GameView;

public class BitmapPool {
    private static HashMap<Integer, Bitmap> bitmapPool = new HashMap<>();

    public static Bitmap getBitmap(int bitmapId) {
        Bitmap bitmap = bitmapPool.get(bitmapId);

        if(bitmap == null) {
            Resources resources = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(resources, bitmapId);
            bitmapPool.put(bitmapId, bitmap);
        }

        return bitmap;
    }
}
