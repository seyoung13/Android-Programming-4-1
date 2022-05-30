package kr.ac.kpu.sgp02.termproject.framework.helper;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.TypedValue;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.view.GameView;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();

        return res.getDimension(dimenResId);
    }

    public static float floatValue(int dimenResId) {
        Resources res = GameView.view.getResources();
        // 최신 버전에서만 지원함
        // res.getFloat(dimenResId);

        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId, outValue, true);
        return outValue.getFloat();
    }

    /**
     * 타일 인덱스에 해당하는 타일의 중앙 좌표를 반환하는 함수.
     */
    public static PointF tileIndexToPosition(int x, int y) {
        float cellSize = Metrics.size(R.dimen.cell_size);

        return new PointF(x * cellSize + cellSize/2, y * cellSize + cellSize/2);
    }

    /**
     * 주어진 좌표에 해당하는 타일의 인덱스를 반환하는 함수.
     */
    public static Point positionToTileIndex(float x, float y) {
        float cellSize = Metrics.size(R.dimen.cell_size);

        return new Point((int)Math.floor(x/cellSize), (int)Math.floor(y/cellSize));
    }

    /**
     * 네자리 문자열에서 x, y 인덱스를 추출하여 반환하는 함수.
     * @param xy 네자리 숫자의 문자열.
     * @throws Point 네자리가 아닌 경우 Point(-9999, -9999).
     */
    public static Point stringToTileIndex(String xy) {
        if (xy.length() != 4) {
            return new Point(-9999, -9999);
        }
        int xTens = Character.getNumericValue(xy.charAt(0));
        int xUnits = Character.getNumericValue(xy.charAt(1));
        int yTens = Character.getNumericValue(xy.charAt(2));
        int yUnits = Character.getNumericValue(xy.charAt(3));

        return new Point(xTens * 10 + xUnits, yTens * 10 + yUnits);
    }
}
