package com.example.myapplication;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

// This fragment represent the top10 ist in a table
public class Fragment_List extends Fragment {

    private View view;
    private TableLayout List_TBL_TopTen;

    private CallBack_TopTen callBack_topTen;

    public void setListCallBack(CallBack_TopTen callBack_topTen) {
        this.callBack_topTen = callBack_topTen;
    }

    public static Fragment_List newInstance() {

        Fragment_List fragment = new Fragment_List();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_list, container, false);
        }

        findViews(view);

        if (callBack_topTen != null) {
            callBack_topTen.GetTopsFromSP();
        }

        return view;
    }

    private void findViews(View view) {
        List_TBL_TopTen = view.findViewById(R.id.List_TBL_TopTen);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    // Set table and display it
    protected void setTable(ArrayList<Winner> tops){
        int counter = 1;
        // Display only 10 first results
        for(Winner current: tops) {
            if(counter <= 10) {
                setRowWithWinner(current.getName(), current.getNumOfMoves(), current.getTimestamp(), counter);
                //increase counter
                counter++;
            }
            else return;
        }
    }

    // Set 1 row in top10 table
    private void setRowWithWinner(String winner_name, int numberOfMoves, String timestamp, int counter) {
        // Define new row
        TableRow row = new TableRow(getActivity());

        // Set columns text texts
        setCell("" + counter, row);
        setCell(winner_name, row);
        setCell(timestamp, row);
        setCell("" + numberOfMoves, row);

        // Add new row to table
        List_TBL_TopTen.addView(row);
    }

    // Set 1 cell in a row
    private void setCell(String str, TableRow row){
        // Define cell
        TextView txt = new TextView(getActivity().getApplication());
        // Set columns text texts
        txt.setText(str);
        //Style
        txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP,10);
        txt.setGravity(1);
        // Add cell to new row
        row.addView(txt);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }
}
