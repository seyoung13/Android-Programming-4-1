package kr.ac.kpu.sgp02.termproject.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

public class BitmapPool {
    private static HashMap<Integer, Bitmap> bitmapPool = new HashMap<>();

    public static Bitmap getBitmap(int mipmapResId) {
        Bitmap bitmap = bitmapPool.get(mipmapResId);

        if(bitmap == null) {
            Resources resources = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(resources, mipmapResId);
            bitmapPool.put(mipmapResId, bitmap);
        }

        return bitmap;
    }
}
