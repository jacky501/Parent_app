package org.atctech.parent.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import org.atctech.parent.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HolidayFragment extends Fragment {



    HolidayAdapter adapter;
    int current_date;
    int current_month;
    int current_year;
    ArrayList<String> day_array;
    GridView gridView;
    int max_days;
    ArrayList<String> month_array;
    ArrayList<String> note_array;
    ArrayList<String> year_array;

    public HolidayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_holiday, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Holiday");

        note_array = new ArrayList<String>();

        month_array = new ArrayList<String>();
        month_array.add("January");
        month_array.add("February");
        month_array.add("March");
        month_array.add("April");
        month_array.add("May");
        month_array.add("Jun");
        month_array.add("July");
        month_array.add("Augest");
        month_array.add("September");
        month_array.add("October");
        month_array.add("November");
        month_array.add("December");

        year_array = new ArrayList<>();
        day_array = new ArrayList<>();
        day_array.add("Sun");
        day_array.add("Mon");
        day_array.add("Tue");
        day_array.add("Wed");
        day_array.add("Thu");
        day_array.add("Fri");
        day_array.add("Sat");

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        current_year = c.get(Calendar.YEAR);
        SimpleDateFormat sformator = new SimpleDateFormat("MM");

        current_month =  Integer.parseInt(sformator.format(c.getTime())); // c.get(Calendar.MONTH);

        for (int i = 2016; i <= year ; i++){
            year_array.add(String.valueOf(i));
        }

        max_days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        current_date = c.get(Calendar.DATE);

        Spinner spinnermonth = view.findViewById(R.id.spinnermonth);
        Spinner spinneryear = view.findViewById(R.id.spinneryear);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, month_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnermonth.setAdapter(spinnerArrayAdapter);
        spinnermonth.setSelection(current_month - 1);
        spinnermonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_month = position + 1;
                // new getAttendenceTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, year_array); //selected item will look like a spinner set from XML
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(spinnerArrayAdapter2);
        spinneryear.setSelection(year_array.indexOf(String.valueOf(current_year)));
        spinneryear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                current_year = Integer.parseInt(year_array.get(position));
                //   new getAttendenceTask().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button btnShow = view.findViewById(R.id.btnshow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gridView = view.findViewById(R.id.gridView2);
        adapter = new HolidayAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        return view;
    }
    public class HolidayAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return max_days;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView= inflater.inflate(R.layout.row_of_holiday, null);
            TextView date = convertView.findViewById(R.id.date);
            //TextView month = convertView.findViewById(R.id.month);
            TextView day = convertView.findViewById(R.id.day);
            TextView note = convertView.findViewById(R.id.note);
            date.setText(String.valueOf(position + 1));
            //month.setText(month_array.get(current_month - 1));

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date a_date = format.parse(String.valueOf(current_year)+"-"+String.valueOf(current_month)+"-"+String.valueOf(position + 1));
                day.setText(day_array.get(a_date.getDay()));
                if (day_array.get(a_date.getDay()).equalsIgnoreCase("Sun")){
                    convertView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }
                date.setText(String.valueOf(position + 1));

            }catch (ParseException e) {
                e.printStackTrace();
            }
            return convertView;
        }
    }

}
