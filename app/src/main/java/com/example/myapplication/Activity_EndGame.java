package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class Activity_EndGame extends AppCompatActivity {


    private Button acitivityEndGame_BTN_topTen;
    private Button acitivityEndGame_BTN_playAgain;
    private Button acitivityEndGame_BTN_backToMenu;


    Gson gson = new Gson();
    private MySp mySp;

    private TextView activityEndGame_TXT_nameOfWinner;
    private ImageView activityEndGame_IMG_winnerImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        mySp = new MySp(this);

        activityEndGame_TXT_nameOfWinner = findViewById(R.id.activityEndGame_TXT_nameOfWinner);

        acitivityEndGame_BTN_topTen = findViewById(R.id.acitivityEndGame_BTN_topTen);
        acitivityEndGame_BTN_playAgain = findViewById(R.id.acitivityEndGame_BTN_playAgain);
        acitivityEndGame_BTN_backToMenu = findViewById(R.id.acitivityEndGame_BTN_backToMenu);

        activityEndGame_IMG_winnerImg = findViewById(R.id.activityEndGame_IMG_winnerImg);


        showWinner();


        acitivityEndGame_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_EndGame.this, Activity_Topten.class);
                startActivity(intent);
                finish();
            }
        });

        acitivityEndGame_BTN_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_EndGame.this, Activity_Main.class);
                startActivity(intent);
                finish();
            }
        });

        acitivityEndGame_BTN_backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_EndGame.this, Activity_Start.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void showWinner() {

        String json_winner = mySp.getString(MySp.KEYS.CURRENT_WINNER, "No Winner");
        Winner winner = gson.fromJson(json_winner, Winner.class);
        activityEndGame_TXT_nameOfWinner.setText("" + winner.getName() + " is the winner\n " + "moves:" + winner.getNumOfMoves());
         setWinnerImge(winner.getPlayer_number());
    }

    private void setWinnerImge(int player_number) {
        if (player_number == 1) {
            activityEndGame_IMG_winnerImg.setImageResource(R.drawable.ic_mario);
        } else {
            activityEndGame_IMG_winnerImg.setImageResource(R.drawable.ic_luigi);
        }
    }
}