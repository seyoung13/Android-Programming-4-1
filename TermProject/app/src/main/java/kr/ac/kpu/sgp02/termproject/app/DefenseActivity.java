package kr.ac.kpu.sgp02.termproject.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.sgp02.termproject.R;
import kr.ac.kpu.sgp02.termproject.framework.view.GameView;
import kr.ac.kpu.sgp02.termproject.game.DefenseGame;
import kr.ac.kpu.sgp02.termproject.game.player.PlayerLogger;
import kr.ac.kpu.sgp02.termproject.game.player.TowerDeployer;

public class DefenseActivity extends AppCompatActivity {
    protected HashMap<Integer, ImageButton> towerButtons = new HashMap<>(4);
    protected FrameLayout resultWindow;
    protected int levelIndex;

    // Build - Generate Signed Bundle / APK
    // APK- Create New... - Key store password - Key pasword - Remember - Release - Finish
    // Event Log - Locate - .apk

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        levelIndex = intent.getExtras().getInt(DefenseGame.LEVEL_INDEX);
        DefenseGame.getInstance().setMapLevel(levelIndex);
        setContentView(R.layout.activity_defense);

        setTowerButtons();
        setTowerCostTexts();
        setResultWindow();
    }

    private void setResultWindow() {
        resultWindow = findViewById(R.id.resultWindow);
        resultWindow.setVisibility(View.INVISIBLE);
    }

    private void setTowerButtons() {
        towerButtons.put(R.id.btnCannon ,findViewById(R.id.btnCannon));
        towerButtons.put(R.id.btnLaser, findViewById(R.id.btnLaser));
        towerButtons.put(R.id.btnMissile, findViewById(R.id.btnMissile));
        towerButtons.put(R.id.btnPlasma, findViewById(R.id.btnPlasma));
    }

    private void setTowerCostTexts() {
        setTowerCostText(findViewById(R.id.txtCannonCost), R.dimen.cannon_cost);
        setTowerCostText(findViewById(R.id.txtLaserCost), R.dimen.laser_cost);
        setTowerCostText(findViewById(R.id.txtMissileCost), R.dimen.missile_cost);
        setTowerCostText(findViewById(R.id.txtPlasmaCost), R.dimen.plasma_cost);
    }

    private void setTowerCostText(View view, int costResId) {
        TextView textView = (TextView) view;

        textView.setText(costResId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        if(GameView.view != null){
            GameView.view.pauseGame();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        GameView.view = null;
        DefenseGame.clear();

        super.onDestroy();
    }

    private void selectTowerToDeploy(int btnId) {
        TowerDeployer.TowerType towerType = null;
        switch (btnId){
            case R.id.btnCannon:
                towerType = TowerDeployer.TowerType.cannon;
                break;
            case R.id.btnLaser:
                towerType = TowerDeployer.TowerType.laser;
                break;
            case R.id.btnMissile:
                towerType = TowerDeployer.TowerType.missile;
                break;
            case R.id.btnPlasma:
                towerType = TowerDeployer.TowerType.plasma;
                break;
            default:
                return;
        }

        resetButtonsSelected();
        towerButtons.get(btnId).setSelected(true);

        DefenseGame.getInstance().activateDeployer(towerType);
    }

    public void resetButtonsSelected() {
        for(Integer id : towerButtons.keySet()) {
            towerButtons.get(id).setSelected(false);
        }
    }

    public void onBtnCannon(View view) {
        selectTowerToDeploy(R.id.btnCannon);
    }

    public void onBtnLaser(View view) {
        selectTowerToDeploy(R.id.btnLaser);
    }

    public void onBtnMissile(View view) {
        selectTowerToDeploy(R.id.btnMissile);
    }

    public void onBtnPlasma(View view) {
        selectTowerToDeploy(R.id.btnPlasma);
    }

    public void onStageEnd(boolean isVictory) {
        resultWindow.setVisibility(View.VISIBLE);

        ArrayList<Integer> log = DefenseGame.getInstance().getPlayerLog();

        TextView resultMessage = findViewById(R.id.txtResultMessage);
        if(isVictory)
            resultMessage.setText("CLEAR!!");
        else
            resultMessage.setText("FAILED!");

        TextView killScore = findViewById(R.id.txtKill);
        killScore.setText(getString(R.string.kill) + log.get(PlayerLogger.PlayerLog.kill.ordinal()));

        TextView loseScore = findViewById(R.id.txtMiss);
        loseScore.setText(getString(R.string.miss) + log.get(PlayerLogger.PlayerLog.miss.ordinal()));

        TextView usedMinerals = findViewById(R.id.txtUsedMinerals);
        usedMinerals.setText(getString(R.string.used_minerals) + log.get(PlayerLogger.PlayerLog.usedMinerals.ordinal()));

        GameView.view.pauseGame();
    }

    public void onBtnToStageSelect(View view){
        startActivity(new Intent(this, StageSelectionActivity.class));
    }

    public void onBtnToTitle(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}