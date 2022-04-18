package kr.ac.kpu.sgp02.termproject.framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;

import kr.ac.kpu.sgp02.termproject.GameView;

public class BitmapPool {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<>();

    public static Bitmap getBitmap(int mipmapResId) {
        Bitmap bitmap = bitmaps.get(mipmapResId);

        if(bitmap == null) {
            Resources resources = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(resources, mipmapResId);
            bitmaps.put(mipmapResId, bitmap);
        }

        return bitmap;
    }
}
