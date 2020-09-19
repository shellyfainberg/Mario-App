package com.example.myapplication;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment_List extends Fragment {

    private View view;
    private TableLayout fragmentList_TBL_ToptenList;

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

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, container, false);
        }

        findViews(view);

        if (callBack_topTen != null) {
            callBack_topTen.GetTopsFromSP();
        }

        return view;
    }

    private void findViews(View view) {
        fragmentList_TBL_ToptenList = view.findViewById(R.id.fragmentList_TBL_ToptenList);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void setToptenTable(ArrayList<Winner> scores) {
        int counter = 1;
        // 10 first results
        for (Winner current : scores) {
            if (counter <= 10) {
                setRow(current.getName(), current.getNumOfMoves(), current.getTime(), counter);
                counter++;
            } else return;
        }
    }

    //set row with winner
    private void setRow(String winner_name, int numberOfMoves, String time, int counter) {

        TableRow row = new TableRow(getActivity());
        setRecord("" + counter, row);
        setRecord(winner_name, row);
        setRecord(time, row);
        setRecord("" + numberOfMoves, row);
        fragmentList_TBL_ToptenList.addView(row);
    }


    private void setRecord(String str, TableRow row) {
        TextView txt = new TextView(getActivity().getApplication());
        txt.setText(str);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        txt.setGravity(1);
        row.addView(txt);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
        }
    }
}
