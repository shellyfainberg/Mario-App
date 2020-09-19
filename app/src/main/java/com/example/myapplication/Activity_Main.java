package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Activity_Main extends AppCompatActivity implements LocationListener{
    private static final int HP_LIFE_BAR=100;
    private static final int LIMIT_LIFE_BAR=50;
    private static final int ATTACK1=10;
    private static final int ATTACK2=20;
    private static final int ATTACK3=30;
    private static final int DELAY_TIME=1000;
    private static final int TOP_TEN_SIZE=10;

    private MySp mySp;
    Gson gson = new Gson();
    MediaPlayer mysound;
   final Handler handler = new Handler();


    // Location
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude, longitude;
    private Location location;

    private ProgressBar main_PRB_life_player1;
    private ProgressBar main_PRB_life_player2;

    private Button main_BTN_player1_attack1;
    private Button main_BTN_player1_attack2;
    private Button main_BTN_player1_attack3;

    private Button main_BTN_player2_attack1;
    private Button main_BTN_player2_attack2;
    private Button main_BTN_player2_attack3;

    private ImageView main_IMG_cube_player1;
    private ImageView main_IMG_cube_player2;
    private ImageView main_IMG_clickHere;


    private int player1_dice;
    private int player2_dice;

    private Random randomNumber= new Random();

    private ArrayList<Button> btnsPlayer1 = new ArrayList<>();
    private ArrayList<Button> btnsPlayer2 = new ArrayList<>();

    // if Player = 1 -> player1 turn, player=2 -> player2 turn
    private int player;

    private int player1_moves_counter = 1;
    private int player2_moves_counter = 1;


    private ImageView Play_IMGBTN_Dice;

    // Define Top10 array list to be loaded from SP
    private ArrayList<Winner> scores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySp = new MySp(this);
        player = 0;

        findViews();
        setLocation();
        setDice();
        loadTopTenArrayList();
    }


    private void findViews() {
        main_BTN_player1_attack1 = findViewById(R.id.main_BTN_player1_attack1);
        main_BTN_player1_attack2 = findViewById(R.id.main_BTN_player1_attack2);
        main_BTN_player1_attack3 = findViewById(R.id.main_BTN_player1_attack3);

        main_BTN_player2_attack1 = findViewById(R.id.main_BTN_player2_attack1);
        main_BTN_player2_attack2 = findViewById(R.id.main_BTN_player2_attack2);
        main_BTN_player2_attack3 = findViewById(R.id.main_BTN_player2_attack3);

        main_PRB_life_player1 = findViewById(R.id.main_PRB_life_player1);
        main_PRB_life_player2 = findViewById(R.id.main_PRB_life_player2);

        main_PRB_life_player1.setProgress(HP_LIFE_BAR);
        main_PRB_life_player2.setProgress(HP_LIFE_BAR);

        main_IMG_cube_player1 = findViewById(R.id.main_IMG_cube_player1);
        main_IMG_cube_player2 = findViewById(R.id.main_IMG_cube_player2);
        main_IMG_clickHere = findViewById(R.id.main_IMG_clickHere);
    }


    private void setDice() {
        Play_IMGBTN_Dice = findViewById(R.id.main_IMG_clickHere);
        Play_IMGBTN_Dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
                start();
                loadTopTenArrayList();
            }
        });
    }


    private void rollDice() {
        player1_dice = (randomNumber.nextInt(6)+1);
        showDice(player1_dice,main_IMG_cube_player1);
        player2_dice = (randomNumber.nextInt(6)+1);
        showDice(player2_dice,main_IMG_cube_player2);

        if (player1_dice == player2_dice){
          //  player=0;
            Toast.makeText(this, "Please roll dice again", Toast.LENGTH_SHORT).show();
            main_IMG_clickHere.setEnabled(true);
            rollDice();
        }
        whoStartGame(player1_dice,player2_dice);
    }

    private void showDice(int diceResult, ImageView imgPlayer) {
        switch (diceResult) {
            case 1:
                imgPlayer.setImageResource(R.drawable.ic_cube1);
                break;
            case 2:
                imgPlayer.setImageResource(R.drawable.ic_cube2);
                break;
            case 3:
                imgPlayer.setImageResource(R.drawable.ic_cube3);
                break;
            case 4:
                imgPlayer.setImageResource(R.drawable.ic_cube4);
                break;
            case 5:
                imgPlayer.setImageResource(R.drawable.ic_cube5);
                break;
            case 6:
                imgPlayer.setImageResource(R.drawable.ic_cube6);
                break;
        }
    }


    private void whoStartGame(int playerDice1,int playerDice2) {

        if (playerDice1 > playerDice2) {
            player=1;
            Toast.makeText(this, "luigi start", Toast.LENGTH_SHORT).show();
            main_IMG_clickHere.setEnabled(false);

            main_BTN_player2_attack1.setEnabled(false);
            main_BTN_player2_attack2.setEnabled(false);
            main_BTN_player2_attack3.setEnabled(false);

            main_BTN_player1_attack1.setEnabled(true);
            main_BTN_player1_attack2.setEnabled(true);
            main_BTN_player1_attack3.setEnabled(true);


        } else if (playerDice1 < playerDice2) {
            player=2;
            Toast.makeText(this, "mario start", Toast.LENGTH_SHORT).show();
            main_IMG_clickHere.setEnabled(false);

            main_BTN_player1_attack1.setEnabled(false);
            main_BTN_player1_attack2.setEnabled(false);
            main_BTN_player1_attack3.setEnabled(false);

            main_BTN_player2_attack1.setEnabled(true);
            main_BTN_player2_attack2.setEnabled(true);
            main_BTN_player2_attack3.setEnabled(true);
        }
    }

    private void start() {
        final Handler handler = new Handler();
        final int delay = DELAY_TIME;
        handler.postDelayed(new Runnable() {
            public void run() {
                play();
                if (main_PRB_life_player1.getProgress() != 0 && main_PRB_life_player2.getProgress() != 0) {
                    handler.postDelayed(this, delay);
                }
            }
        }, delay);

    }

    private void play() {
//        if (player == 1) { //player's 1 turn
//            int randomNumber = new Random().nextInt(3);
//            if (randomNumber == 0) {
//                main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - ATTACK1);
//                mysound = MediaPlayer.create(this,R.raw.smb_fireball);
//                mysound.start();
//                float currentRotation = this.main_BTN_player1_attack1.getRotation();
//                this.main_BTN_player1_attack1.animate().rotation(currentRotation+(-360)).setDuration(500);
//                player1_moves_counter++;
//            } else if (randomNumber == 1) {
//
//                main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - ATTACK2);
//                mysound = MediaPlayer.create(this,R.raw.smb_stomp);
//                mysound.start();
//
//                float currentRotation = this.main_BTN_player1_attack1.getRotation();
//                this.main_BTN_player1_attack2.animate().rotation(currentRotation+(-360)).setDuration(500);
//                player1_moves_counter++;
//            } else if (randomNumber == 2) {
//                main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - ATTACK3);
//                mysound = MediaPlayer.create(this,R.raw.smb_kick);
//                mysound.start();
//                float currentRotation = this.main_BTN_player1_attack1.getRotation();
//                this.main_BTN_player1_attack3.animate().rotation(currentRotation+(-360)).setDuration(500);
//                player1_moves_counter++;
//            }
//            updateLifeBar();
//            if (main_PRB_life_player1.getProgress() <= 0 || main_PRB_life_player2.getProgress() <= 0) {
//                mysound = MediaPlayer.create(this,R.raw.smb_gameover);
//                mysound.start();
//                howsWin();
//            }
//            player = 2;
            if (player == 1) {
                int rnd = (randomNumber.nextInt(3));
                float currentRotation;
                switch (rnd) {
                    case 0:
                        currentRotation = this.main_BTN_player1_attack1.getRotation();
                        this.main_BTN_player1_attack1.animate().rotation(currentRotation + (-360)).setDuration(500);

                        main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - ATTACK1);

                        mysound = MediaPlayer.create(this, R.raw.smb_fireball);
                        mysound.start();

                        player1_moves_counter++;
                        break;
                    case 1:
                        currentRotation = this.main_BTN_player1_attack2.getRotation();
                        this.main_BTN_player1_attack2.animate().rotation(currentRotation + (-360)).setDuration(500);

                        main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - ATTACK2);

                        mysound = MediaPlayer.create(this, R.raw.smb_stomp);
                        mysound.start();

                        player1_moves_counter++;
                        break;
                    case 2:
                        currentRotation = this.main_BTN_player1_attack3.getRotation();
                        this.main_BTN_player1_attack3.animate().rotation(currentRotation + (-360)).setDuration(500);

                        main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - ATTACK3);

                        mysound = MediaPlayer.create(this, R.raw.smb_kick);
                        mysound.start();
                        player1_moves_counter++;
                        break;
                }
                updateLifeBar();
                player = 2;
                if (main_PRB_life_player2.getProgress() <= 0 || main_PRB_life_player1.getProgress() <= 0) {
                    mysound = MediaPlayer.create(this, R.raw.smb_gameover);
                    mysound.start();
                    whoWinner();
                }
            } else { //player's 2 turn
                int rnd = (randomNumber.nextInt(3));
                float currentRotation;
                switch (rnd) {
                    case 0:
                        main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - ATTACK1);
                        currentRotation = this.main_BTN_player2_attack1.getRotation();

                        this.main_BTN_player2_attack1.animate().rotation(currentRotation + (-360)).setDuration(500);
                        mysound = MediaPlayer.create(this, R.raw.smb_fireball);
                        mysound.start();
                        player2_moves_counter++;
                        break;
                    case 1:
                        main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - ATTACK2);

                        currentRotation = this.main_BTN_player2_attack2.getRotation();
                        this.main_BTN_player2_attack2.animate().rotation(currentRotation + (-360)).setDuration(500);

                        mysound = MediaPlayer.create(this, R.raw.smb_stomp);
                        mysound.start();

                        mysound = MediaPlayer.create(this, R.raw.smb_stomp);
                        mysound.start();
                        player2_moves_counter++;
                        break;
                    case 2:
                        main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - ATTACK3);

                        currentRotation = this.main_BTN_player2_attack3.getRotation();
                        this.main_BTN_player2_attack3.animate().rotation(currentRotation + (-360)).setDuration(500);

                        mysound = MediaPlayer.create(this, R.raw.smb_kick);
                        mysound.start();
                        player2_moves_counter++;
                        break;
                }
                updateLifeBar();
                player = 1;
                if (main_PRB_life_player2.getProgress() <= 0 || main_PRB_life_player1.getProgress() <= 0) {
                    mysound = MediaPlayer.create(this, R.raw.smb_gameover);
                    mysound.start();
                    whoWinner();
                }
            }
//            if (randomNumber == 0) {
//                main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - ATTACK1);
//                mysound = MediaPlayer.create(this,R.raw.smb_fireball);
//                mysound.start();
//
//                float currentRotation = this.main_BTN_player1_attack1.getRotation();
//                this.main_BTN_player2_attack1.animate().rotation(currentRotation+(-360)).setDuration(500);
//                player2_moves_counter++;
//            } else if (randomNumber == 1) {
//                main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - ATTACK2);
//                mysound = MediaPlayer.create(this,R.raw.smb_stomp);
//                mysound.start();
//                float currentRotation = this.main_BTN_player1_attack1.getRotation();
//                this.main_BTN_player2_attack2.animate().rotation(currentRotation+(-360)).setDuration(500);
//                player2_moves_counter++;
//            } else if (randomNumber == 2) {
//                main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - ATTACK3);
//                mysound = MediaPlayer.create(this,R.raw.smb_kick);
//                mysound.start();
//                float currentRotation = this.main_BTN_player1_attack1.getRotation();
//                this.main_BTN_player2_attack3.animate().rotation(currentRotation+(-360)).setDuration(500);
//                player2_moves_counter++;
//            }
//            updateLifeBar();
//            if (main_PRB_life_player2.getProgress() <= 0 || main_PRB_life_player1.getProgress() <= 0) {
//                mysound = MediaPlayer.create(this,R.raw.smb_gameover);
//                mysound.start();
//                howsWin();
//
//            }
//
//            player = 1;
//

        }



    private void whoWinner() {
        Winner winner = new Winner();
        if (main_PRB_life_player1.getProgress() <= 0) {
            setWinner("mario ", player1_moves_counter, winner);
        } else {

            setWinner("luigi", player2_moves_counter, winner);
        }
        putWinnerOnSP(winner);
        addWinnerToArr(winner);

        String json = gson.toJson(scores);
        mySp.putString(MySp.KEYS.TOP_TEN, json);
        Intent intent = new Intent(Activity_Main.this, Activity_EndGame.class);
        startActivity(intent);
    }


    private void addWinnerToArr(Winner winner) {
        boolean isAdd = false;
        for (Winner t : scores) {
            if (winner.getNumOfMoves() <= t.getNumOfMoves()) {
                scores.add(scores.indexOf(t), winner);
                isAdd = true;
                break;
            }
        }
        if (scores.size() < TOP_TEN_SIZE && !isAdd) {
            scores.add(winner);
        }
    }


    private void updateLifeBar() {
        if (main_PRB_life_player1.getProgress() < LIMIT_LIFE_BAR) {
            main_PRB_life_player1.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (main_PRB_life_player2.getProgress() < LIMIT_LIFE_BAR) {
            main_PRB_life_player2.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }

    }


    private void setWinner(String name, int player1_moves, Winner winner) {

        winner.setName(name);
        winner.setNumOfMoves(player1_moves);
        winner.setPlayer_number(player);

        // Set Location
        winner.setLat(latitude);
        winner.setLon(longitude);
    }

    private void putWinnerOnSP(Winner winner) {
        String json = gson.toJson(winner);
        mySp.putString(MySp.KEYS.CURRENT_WINNER, json);
    }

    private void setLocation() {
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        // Check map permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, R.string.error_permission_map, Toast.LENGTH_LONG).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    private void loadTopTenArrayList() {
        String json = mySp.getString(MySp.KEYS.TOP_TEN, null);
        Type type = new TypeToken<ArrayList<Winner>>() {
        }.getType();
        scores = gson.fromJson(json, type);

        if (scores == null) {
            scores = new ArrayList<>();
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }


}



