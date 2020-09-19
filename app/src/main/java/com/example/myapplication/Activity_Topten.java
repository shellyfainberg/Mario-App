package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_Topten extends AppCompatActivity implements CallBack_TopTen{

    private MySp mySPV;
    Gson gson = new Gson();
    final Handler handler = new Handler();

    //Define fragments
    private Fragment_List fragment_list;
    private Fragment_Map fragment_maps;

    //Buttons
    Button TopTen_BTN_Main_Page;
    Button TopTen_BTN_NEWGMAE;

    // Define Top10 array list to be loaded from SP
    private ArrayList<Winner> tops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topten);

        // Set Buttons
        findViews();

        // Set SP
        mySPV = new MySp(this);

        //Set google map

        // Start s game when clicking ENDGAME_BTN_NEWGMAE button
        TopTen_BTN_NEWGMAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
            }
        });
        // Go to main page when clicking ENDGAME_BTN_Main_Page button
        TopTen_BTN_Main_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainPge();
            }
        });

        initFragments();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Set fragments
        fragment_list.setTable(tops);
        fragment_maps.setTopTen(tops);
    }

    private void findViews() {
        TopTen_BTN_Main_Page = findViewById(R.id.TopTen_BTN_Main_Page);
        TopTen_BTN_NEWGMAE = findViewById(R.id.TopTen_BTN_NEWGMAE);
    }


    private void initFragments() {
        // Init list
        fragment_list = Fragment_List.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.TOPTEN_TBL_TopTen,fragment_list);
        transaction.commit();
        fragment_list.setListCallBack(this);

        // Init map
        fragment_maps = Fragment_Map.newInstance();
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.TOPTEN_Map_TopTen,fragment_maps);
        transaction2.commit();
        fragment_maps.setListCallBack(this);
    }



    // Load top10 array list from sp and convert json format to array list
    @Override
    public void GetTopsFromSP() {
        String json = mySPV.getString(MySp.KEYS.TOP_TEN, null);
        Type type = new TypeToken<ArrayList<Winner>>() {}.getType();
        tops = gson.fromJson(json, type);

        if (tops == null){
            tops = new ArrayList<>();
        }
    }

    private void startNewGame() {
        Intent intent = new Intent(Activity_Topten.this, Activity_Main.class);
        startActivity(intent);
        finish();
    }

    private void openMainPge() {
        finish();
    }
}