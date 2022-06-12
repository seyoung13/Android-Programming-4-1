package kr.ac.kpu.sgp02.termproject.game.player;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.helper.Metrics;
import kr.ac.kpu.sgp02.termproject.framework.interfaces.GameObject;
import kr.ac.kpu.sgp02.termproject.framework.objects.Button;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.system.NumberDisplay;
import kr.ac.kpu.sgp02.termproject.game.tower.Tower;

public class TowerSelector implements GameObject {
    private final float btnSize;
    private Button upgrader;
    private Button uninstaller;
    private NumberDisplay upgradeCostDisplay;
    private NumberDisplay sellCostDisplay;

    private Tower selectedTower;

    public TowerSelector() {
        btnSize = Metrics.size(R.dimen.upgrade_button_size);

        upgradeCostDisplay = new NumberDisplay(100, 3, -9999, -9999, btnSize/6, Color.YELLOW, false);
        sellCostDisplay = new NumberDisplay(100, 3, -9999, -9999, btnSize/6, Color.YELLOW, false);

        upgrader = new Button(-9999, -9999, btnSize, btnSize, R.mipmap.upgrade_pressed, R.mipmap.upgrade_pressed,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.ButtonAction action) {
                        if (action == Button.ButtonAction.released)
                            return false;

                        if(selectedTower != null) {
                            if(!selectedTower.isMaxLevel()) {
                                if(DefenseGame.getInstance().useMineral(selectedTower.getUpgradeCost()))
                                    selectedTower.upgrade();
                            }

                            return true;
                        }

                        return false;
                    }
                });
        DefenseGame.getInstance().add(upgrader, DefenseGame.Layer.ui);

        uninstaller = new Button(-9999, -9999, btnSize, btnSize, R.mipmap.uninstall_pressed, R.mipmap.uninstall_pressed,
                new Button.Callback() {
                    @Override
                    public boolean onTouch(Button.ButtonAction action) {
                        if (action == Button.ButtonAction.released)
                            return false;

                        if(selectedTower != null) {
                            DefenseGame.getInstance().storeMineral(selectedTower.getSellCost());
                            selectedTower.uninstall();
                            release();
                            return true;
                        }

                        return false;
                    }
                });
        DefenseGame.getInstance().add(uninstaller, DefenseGame.Layer.ui);
    }

    public Tower getSelected() {
        return selectedTower;
    }

    public void select(Tower tower){
        selectedTower = tower;
        selectedTower.showRange(true);
        upgradeCostDisplay.setNumber(selectedTower.getUpgradeCost());
        sellCostDisplay.setNumber(selectedTower.getSellCost());

        if(selectedTower.isMaxLevel()) {
            upgrader.setActivated(false);
            upgradeCostDisplay.setActivated(false);
        }
        else {
            upgrader.setActivated(true);
            upgradeCostDisplay.setActivated(true);
        }
    }

    public void release() {
        if(selectedTower == null)
            return;

        selectedTower.showRange(false);
        selectedTower = null;
        hide();
    }

    public void setPosition(float x, float y){
        float cellSize = Metrics.size(R.dimen.cell_size);

        Point tileIndex = Metrics.positionToTileIndex(x, y);
        PointF center = Metrics.tileIndexToPosition(tileIndex.x, tileIndex.y);

        PointF upgraderOffset = new PointF(-cellSize, -cellSize/2);
        PointF uninstallerOffset = new PointF(cellSize, -cellSize/2);
        PointF costOffset = new PointF();

        if(center.x - cellSize <= 0) {
            upgraderOffset.x = 0;

            if(center.y - cellSize < 0)
                upgraderOffset.y = cellSize/2;
        }

        if(center.x + cellSize >= Metrics.width) {
            uninstallerOffset.x = 0;

            if(center.y - cellSize < 0)
                uninstallerOffset.y = cellSize/2;
        }


        upgrader.setPosition(center.x + upgraderOffset.x, center.y + upgraderOffset.y);
        uninstaller.setPosition(center.x + uninstallerOffset.x, center.y + uninstallerOffset.y);
        upgradeCostDisplay.setPosition(center.x + upgraderOffset.x, center.y + upgraderOffset.y - btnSize/2);
        sellCostDisplay.setPosition(center.x + uninstallerOffset.x, center.y + uninstallerOffset.y - btnSize/2);
    }

    protected void hide() {
        float x = -9999;
        float y = -9999;

        upgrader.setPosition(x, y);
        uninstaller.setPosition(x, y);
        upgradeCostDisplay.setPosition(x , y);
        sellCostDisplay.setPosition(x, y);
    }

    @Override
    public void update(float deltaSecond) {
    }

    @Override
    public void draw(Canvas canvas) {
        upgradeCostDisplay.draw(canvas);
        sellCostDisplay.draw(canvas);
    }
}
