package kr.ac.kpu.sgp02.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout frameTitle, frameSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameTitle = findViewById(R.id.frameTitle);
        frameSettings = findViewById(R.id.frameSettings);

        frameTitle.setVisibility(View.VISIBLE);
        frameSettings.setVisibility(View.INVISIBLE);
    }

    public void onBtnStart(View view){
        startActivity(new Intent(MainActivity.this, StageSelectionActivity.class));
    }

    public void onBtnSettings(View view){
        frameTitle.setVisibility(View.INVISIBLE);
        frameSettings.setVisibility(View.VISIBLE);
    }

    public void onBtnCloseSettings(View view){
        frameTitle.setVisibility(View.VISIBLE);
        frameSettings.setVisibility(View.INVISIBLE);
    }

}