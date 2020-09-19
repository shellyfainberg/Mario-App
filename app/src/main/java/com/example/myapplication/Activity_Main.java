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
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Activity_Main extends AppCompatActivity {

    private MySp mySp;
    Gson gson = new Gson();
    final Handler handler = new Handler();
    MediaPlayer sound;
    // Location
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude, longitude;
    private Location location;

    private ProgressBar main_PRB_life_player1;
    private ProgressBar main_PRB_life_player2;


    private Button main_BTN_player1_button1;
    private Button main_BTN_player1_button2;
    private Button main_BTN_player1_button3;

    private Button main_BTN_player2_button1;
    private Button main_BTN_player2_button2;
    private Button main_BTN_player2_button3;


    private ProgressBar player1_progressBar;
    private ProgressBar player2_progressBar;
    private ImageView main_IMG_cube_player1;
    private ImageView main_IMG_cube_player2;
    private ImageView main_IMG_clickHere;

    private int value_cub1;
    private int value_cub2;

    // if Player is even - player1 turn, else player2 turn
    private int player;
    private boolean end = false;

    // Set number of moves for each player - start with 1
    private int player1_moves_counter = 1;
    private int player2_moves_counter = 1;


    // Define Dice parameters
    private ImageView Play_IMGBTN_Dice;
    private TextView Play_TXT_PlayerTurn;
    private Random rand = new Random();

    // Define dice scores for each player  and initial it with 1
    int player1_dice_score = 1;
    int player2_dice_score = 1;

    // Boolean variable that define if we can start the game - initial with false
    boolean is_game_can_start = false;

    // Define Top10 array list to be loaded from SP
    private ArrayList<Winner> tops;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set SP
        mySp = new MySp(this);

        // Set player
        player = 0;

        // Set Who is  rolling the dice
        Play_TXT_PlayerTurn = findViewById(R.id.Play_TXT_PlayerTurn);
        Play_TXT_PlayerTurn.setText("Donald Duck");

        // Set location
        //setLocation();

        // Set dice
        setDice();

        // Load top10ArrayList from SP
        // loadTopTenArrayList();

        // Set Variables as attack buttons and progress bars
        SetPlayersVars();

    }


    private void SetPlayersVars() {

        main_BTN_player1_button1 = findViewById(R.id.main_BTN_player1_button1);
        main_BTN_player1_button2 = findViewById(R.id.main_BTN_player1_button2);
        main_BTN_player1_button3 = findViewById(R.id.main_BTN_player1_button3);

        main_BTN_player2_button1 = findViewById(R.id.main_BTN_player2_button1);
        main_BTN_player2_button2 = findViewById(R.id.main_BTN_player2_button2);
        main_BTN_player2_button3 = findViewById(R.id.main_BTN_player2_button3);

        main_PRB_life_player1 = findViewById(R.id.main_PRB_life_player1);
        main_PRB_life_player2 = findViewById(R.id.main_PRB_life_player2);

        main_PRB_life_player1.setProgress(100);
        main_PRB_life_player2.setProgress(100);

        main_IMG_cube_player1 = findViewById(R.id.main_IMG_cube_player1);
        main_IMG_cube_player2 = findViewById(R.id.main_IMG_cube_player2);
        main_IMG_clickHere = findViewById(R.id.main_IMG_clickHere);

        // Set initial value to progress bar

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

        int randomNumber = rand.nextInt(6) + 1;
        switch (randomNumber) {
            case 1:
                main_IMG_cube_player1.setImageResource(R.drawable.ic_cube1);
                value_cub1 = 1;
                break;
            case 2:
                main_IMG_cube_player1.setImageResource(R.drawable.ic_cube2);
                value_cub1 = 2;
                break;
            case 3:
                main_IMG_cube_player1.setImageResource(R.drawable.ic_cube3);
                value_cub1 = 3;
                break;
            case 4:
                main_IMG_cube_player1.setImageResource(R.drawable.ic_cube4);
                value_cub1 = 4;
                break;
            case 5:
                main_IMG_cube_player1.setImageResource(R.drawable.ic_cube5);
                value_cub1 = 5;
                break;
            case 6:
                main_IMG_cube_player1.setImageResource(R.drawable.ic_cube6);
                value_cub1 = 6;
                break;
        }
        randomNumber = rand.nextInt(6) + 1;
        switch (randomNumber) {
            case 1:
                main_IMG_cube_player2.setImageResource(R.drawable.ic_cube1);
                value_cub2 = 1;
                break;
            case 2:
                main_IMG_cube_player2.setImageResource(R.drawable.ic_cube2);
                value_cub2 = 2;
                break;
            case 3:
                main_IMG_cube_player2.setImageResource(R.drawable.ic_cube3);
                value_cub2 = 3;
                break;
            case 4:
                main_IMG_cube_player2.setImageResource(R.drawable.ic_cube4);
                value_cub2 = 4;
                break;
            case 5:
                main_IMG_cube_player2.setImageResource(R.drawable.ic_cube5);
                value_cub2 = 5;
                break;
            case 6:
                main_IMG_cube_player2.setImageResource(R.drawable.ic_cube6);
                value_cub2 = 6;
                break;
        }
        howStartGame();

    }


    private void howStartGame() {

        if (value_cub1 > value_cub2) {
            Toast.makeText(this, "luigi start", Toast.LENGTH_SHORT).show();
            player = 1;

            main_BTN_player2_button1.setEnabled(false);
            main_BTN_player2_button2.setEnabled(false);
            main_BTN_player2_button3.setEnabled(false);

            main_BTN_player1_button1.setEnabled(true);
            main_BTN_player1_button2.setEnabled(true);
            main_BTN_player1_button3.setEnabled(true);

        } else if (value_cub1 < value_cub2) {
            Toast.makeText(this, "mario start", Toast.LENGTH_SHORT).show();
            player = 2;


            main_IMG_clickHere.setEnabled(false);

            main_BTN_player1_button1.setEnabled(false);
            main_BTN_player1_button2.setEnabled(false);
            main_BTN_player1_button3.setEnabled(false);

            main_BTN_player2_button1.setEnabled(true);
            main_BTN_player2_button2.setEnabled(true);
            main_BTN_player2_button3.setEnabled(true);

        } else  {
            main_IMG_clickHere.setEnabled(true);
            Toast.makeText(this, "Please roll dice again", Toast.LENGTH_SHORT).show();
            rollDice();
            howStartGame();
        }

    }

    private void start() {
        final Handler handler = new Handler();
        final int delay = 1000;
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
        if (player == 1) { //player's 1 turn
            int randomNumber = new Random().nextInt(3);
            if (randomNumber == 0) {
                main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - 10);
                player1_moves_counter++;
            } else if (randomNumber == 1) {
                main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - 20);
                player1_moves_counter++;
            } else if (randomNumber == 2) {
                main_PRB_life_player2.setProgress(main_PRB_life_player2.getProgress() - 30);
                player1_moves_counter++;
            }
            updateLifeBar();
            if (main_PRB_life_player1.getProgress() <= 0 || main_PRB_life_player2.getProgress() <= 0) {
                howsWin();

            }
            player = 2;

        } else { //player's 2 turn
            int randomNumber = new Random().nextInt(3);
            if (randomNumber == 0) {
                main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - 10);
                player2_moves_counter++;
            } else if (randomNumber == 1) {
                main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - 20);
                player2_moves_counter++;
            } else if (randomNumber == 2) {
                main_PRB_life_player1.setProgress(main_PRB_life_player1.getProgress() - 30);
                player2_moves_counter++;
            }
            updateLifeBar();
            if (main_PRB_life_player2.getProgress() <= 0 || main_PRB_life_player1.getProgress() <= 0) {
                // main_IMG_clickHere.setImageResource(R.drawable.ic_mario);
                howsWin();


            }

            player = 1;


        }

    }

    private void howsWin() {
        Winner winner = new Winner();
        if (main_PRB_life_player1.getProgress() <= 0) {
            setWinner("mario ", player1_moves_counter, winner);
            //clickHere.setEnabled(false);

        } else {

            setWinner("lugi", player2_moves_counter, winner);
            //clickHere.setEnabled(false);
        }

        setWinnerOnSP(winner);

        checkAndAddWinnerToScoresArray(winner);

        // Save tops list with sp in a json format
        String json = gson.toJson(tops);
        mySp.putString(MySp.KEYS.TOP_TEN, json);

        // End the Game and open Activity_End_Game to see the winner
        openEndGame();
    }


    private void checkAndAddWinnerToScoresArray(Winner winner) {
        boolean is_inserted  = false;
        // Go over each winner in the winners array list and insert current one in the correct place
        for (Winner t : tops) {
            if (winner.getNumOfMoves() <= t.getNumOfMoves()) {
                tops.add(tops.indexOf(t), winner);
                is_inserted = true;
                break;
            }
        }
        //If list size > 0 and winner didn't insert to the array list, add it in the end of the list
        if(tops.size() < 10 && !is_inserted) {
            tops.add(winner);
        }
    }


    private void updateLifeBar() {
        if (main_PRB_life_player1.getProgress() < 50) {
            main_PRB_life_player1.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (main_PRB_life_player2.getProgress() < 50) {
            main_PRB_life_player2.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }

    }


    private void openEndGame() {
        Intent intent = new Intent(Activity_Main.this, Activity_EndGame.class);
        startActivity(intent);
    }


    private void setWinner(String name, int player1_moves_counter, Winner winner) {
        winner.setName(name);
        winner.setNumOfMoves(player1_moves_counter);
        winner.setPlayer_number(player);

        // Set Location
//        winner.setLat(latitude);
//        winner.setLon(longitude);
    }

    // Set winner in current sp in order to display it in Activity_End_Game page
    private void setWinnerOnSP(Winner winner) {
        String json = gson.toJson(winner);
        mySp.putString(MySp.KEYS.CURRENT_WINNER, json);
    }

    private void loadTopTenArrayList(){
        String json = mySp.getString(MySp.KEYS.TOP_TEN, null);
        Type type = new TypeToken<ArrayList<Winner>>() {}.getType();
        tops = gson.fromJson(json, type);

        if (tops == null){
            tops = new ArrayList<>();
        }
    }




}



