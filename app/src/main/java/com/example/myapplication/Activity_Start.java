package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Start extends AppCompatActivity {

    private Button start_BTN_startGame;
    private Button start_BTN_topten;
    MediaPlayer mysound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mysound = MediaPlayer.create(this, R.raw.smb_mariodie);
        mysound.start();

        findViews();
        CheckPremission();
    }

    private void findViews() {
        start_BTN_topten = findViewById(R.id.start_BTN_topten);
        start_BTN_topten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTop10_FromMenu = new Intent(Activity_Start.this, Activity_Topten.class);
                startActivity(intentTop10_FromMenu);
            }
        });

        start_BTN_startGame = findViewById(R.id.start_BTN_startGame);
        start_BTN_startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //      MySignalV2.getInstance().vibrate(300);
                Intent intentBattle = new Intent(Activity_Start.this, Activity_Main.class);
                startActivity(intentBattle);
            }
        });
    }


    private void CheckPremission() {
        if (!(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(this, R.string.error_permission_map, Toast.LENGTH_LONG).show();
        }
    }


}
