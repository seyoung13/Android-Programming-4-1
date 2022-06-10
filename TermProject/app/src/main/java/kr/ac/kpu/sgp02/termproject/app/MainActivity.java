package kr.ac.kpu.sgp02.termproject.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import kr.ac.kpu.sgp02.termproject.R;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameTitle, frameSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameTitle = findViewById(R.id.frameTitle);
        frameSettings = findViewById(R.id.frameOption);

        hideOptions();

        //빠른 실행
        //startActivity(new Intent(this, StageSelectionActivity.class));
    }

    public void onBtnStart(View view){
        startActivity(new Intent(MainActivity.this, StageSelectionActivity.class));
    }

    private void showOptions() {
        frameTitle.setVisibility(View.INVISIBLE);
        frameSettings.setVisibility(View.VISIBLE);
    }

    private void hideOptions() {
        frameTitle.setVisibility(View.VISIBLE);
        frameSettings.setVisibility(View.INVISIBLE);
    }

    public void onBtnOption(View view){
        showOptions();
    }

    public void onBtnCloseSettings(View view){
        hideOptions();
    }
}