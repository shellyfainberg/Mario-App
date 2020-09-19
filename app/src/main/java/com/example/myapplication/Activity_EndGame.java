package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activity_Main;
import com.example.myapplication.Activity_Topten;
import com.example.myapplication.MySp;
import com.example.myapplication.R;
import com.google.gson.Gson;

public class Activity_EndGame extends AppCompatActivity {
    private MySp mySp;
    private TextView ENDGAME_TXT_CURRENT_WINNER;

    // Define buttons
    private Button ENDGAME_BTN_TOP10;
    private Button ENDGAME_BTN_NEWGMAE;
    private Button ENDGAME_BTN_Main_Page;

    // Define winner image
    private ImageView ENDGAME_IMG_WINNER;

    // Define Gson object
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        mySp = new MySp(this);
        ENDGAME_TXT_CURRENT_WINNER = findViewById(R.id.ENDGAME_TXT_CURRENT_WINNER);

        // Find match buttons
        ENDGAME_BTN_TOP10 = findViewById(R.id.ENDGAME_BTN_TOP10);
        ENDGAME_BTN_NEWGMAE = findViewById(R.id.ENDGAME_BTN_NEWGMAE);
        ENDGAME_BTN_Main_Page = findViewById(R.id.ENDGAME_BTN_Main_Page);

        // Find winner image
        ENDGAME_IMG_WINNER = findViewById(R.id.ENDGAME_IMG_WINNER);

        //Display winner name and image
        displayWinner();

        // Go to top10 page when clicking ENDGAME_BTN_TOP10 button
        ENDGAME_BTN_TOP10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTpp10();
            }
        });
        // Start ne game when clicking ENDGAME_BTN_NEWGMAE button
        ENDGAME_BTN_NEWGMAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });
        // Go to main page when clicking ENDGAME_BTN_Main_Page button
        ENDGAME_BTN_Main_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPge();
            }
        });


    }


    private void displayWinner() {
        String json_winner = mySp.getString(MySp.KEYS.CURRENT_WINNER, "No Winner");
        Winner winner = gson.fromJson(json_winner, Winner.class);

        ENDGAME_TXT_CURRENT_WINNER.setText("" + winner.getName() + " is the winner with " + winner.getNumOfMoves() + " moves!");
        // setWinnerImge(winner.getPlayer_number());
    }

    // Set image with winner image
    private void setWinnerImge(int player_number) {
        if (player_number % 2 == 0)
            ENDGAME_IMG_WINNER.setImageResource(R.drawable.ic_button1);
        else
            ENDGAME_IMG_WINNER.setImageResource(R.drawable.ic_button2);
    }


    private void openTpp10() {
        Intent intent = new Intent(Activity_EndGame.this, Activity_Topten.class);
        startActivity(intent);
        finish();
    }

    private void startNewGame() {
        Intent intent = new Intent(Activity_EndGame.this, Activity_Main.class);
        startActivity(intent);
        finish();
    }

    private void openMainPge() {
        finish();
    }
}