package com.example.myapplication;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class Activity_Topten extends AppCompatActivity implements CallBack_TopTen {

    private Button topten_BTN_backToMenu;
    private Button topten_BTN_playAgain;

    private Fragment_List fragment_list;
    private Fragment_Map fragment_maps;

    private MySp mySp;
    Gson gson = new Gson();

    //ArrayList to be loaded from SP
    private ArrayList<Winner> scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);

        findViews();

        mySp = new MySp(this);

        topten_BTN_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Topten.this, Activity_Main.class);
                startActivity(intent);
                finish();
            }
        });
        topten_BTN_backToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Topten.this, Activity_Start.class);
                startActivity(intent);
                finish();
            }
        });

        setFragments();
    }


    @Override
    protected void onStart() {

        super.onStart();
        fragment_list.setToptenTable(scores);
        fragment_maps.setScores(scores);
    }

    private void findViews() {
        topten_BTN_backToMenu = findViewById(R.id.topten_BTN_backToMenu);
        topten_BTN_playAgain = findViewById(R.id.topten_BTN_playAgain);
    }


    private void setFragments() {

        //list
        fragment_list = Fragment_List.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.topten_FLAY_listFrame, fragment_list);
        transaction.commit();
        fragment_list.setListCallBack(this);

        // map
        fragment_maps = Fragment_Map.newInstance();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.topten_FLAY_MapFrame, fragment_maps);
        transaction2.commit();
        fragment_maps.setListCallBack(this);
    }


    // Load top10 from sp
    @Override
    public void GetTopsFromSP() {
        String json = mySp.getString(MySp.KEYS.TOP_TEN, null);
        Type type = new TypeToken<ArrayList<Winner>>() {
        }.getType();
        scores = gson.fromJson(json, type);

        if (scores == null) {
            scores = new ArrayList<>();
        }
    }

}