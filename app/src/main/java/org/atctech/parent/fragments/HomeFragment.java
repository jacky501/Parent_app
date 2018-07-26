package org.atctech.parent.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.atctech.parent.MainActivity;
import org.atctech.parent.R;
import org.atctech.parent.utils.GPSTracker;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    FloatingActionButton messageBtn;
    GPSTracker gpsTracker;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");


        messageBtn = view.findViewById(R.id.messageBtn);

        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessegesFragment fragment = new MessegesFragment();
                getFragmentManager().beginTransaction().replace(R.id.main_fragment,fragment).commitAllowingStateLoss();
            }
        });



//        session = Session.getInstance(getContext().getSharedPreferences("prefs", Context.MODE_PRIVATE));
//
//
//
//        BarChart barChart = view.findViewById(R.id.barChart);
//
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(8f, 0));
//        entries.add(new BarEntry(2f, 1));
//        entries.add(new BarEntry(5f, 2));
//        entries.add(new BarEntry(20f, 3));
//        entries.add(new BarEntry(15f, 4));
//        entries.add(new BarEntry(19f, 5));
//        entries.add(new BarEntry(8f, 6));
//        entries.add(new BarEntry(2f, 7));
//        entries.add(new BarEntry(5f, 8));
//        entries.add(new BarEntry(20f, 9));
//        entries.add(new BarEntry(15f, 10));
//        entries.add(new BarEntry(19f, 11));
//
//        BarDataSet bardataset = new BarDataSet(entries, "Cells");
//
//        ArrayList<String> labels = new ArrayList<String>();
//        labels.add("Dec");
//        labels.add("Nov");
//        labels.add("Oct");
//        labels.add("Sep");
//        labels.add("Aug");
//        labels.add("Jul");
//        labels.add("Jun");
//        labels.add("May");
//        labels.add("Apr");
//        labels.add("Mar");
//        labels.add("Feb");
//        labels.add("Jan");
//
//        BarData barData = new BarData(bardataset);
//        barChart.setData(barData);
//
//        Description description = new Description();
//        description.setText("Studence Monthly Report");
//
//        barChart.setDescription(description);
//
//        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        barChart.animateY(4000);

        return view;
    }

}
