package org.atctech.parent.fragments;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.R;
import org.atctech.parent.model.GetLocation;
import org.atctech.parent.preferences.Session;
import org.atctech.parent.utils.GPSTracker;
import org.atctech.parent.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {


    Handler handler;
    Runnable run;
    SupportMapFragment mapFragment;
    GoogleMap mGoogleMap;
    Marker currentMarker;
    ApiRequest service;
    Session session;
    Spinner spinner;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Location");

        mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
        if (mapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.main_fragment, mapFragment);
            fragmentTransaction.commitAllowingStateLoss();

        }

        mapFragment.getMapAsync(this);

        spinner = view.findViewById(R.id.spinnerMapStyle);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        getLocations();
        mapStyle();


        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {



        GPSTracker gps = new GPSTracker(getContext(),getActivity());

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


//        if (ContextCompat.checkSelfPermission(getContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//
//            mGoogleMap.setMyLocationEnabled(true);
//        }

        LatLng latLng = new LatLng(gps.getLatitude(),gps.getLongitude() );

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(session.getUser().getFname()+" "+session.getUser().getLname());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currentMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));

    }

    public void getLocations()
    {

        session = Session.getInstance(getActivity().getSharedPreferences("prefs",getContext().MODE_PRIVATE));
        String u_id = session.getUser().getU_id();
        Call<GetLocation> getLocationCall = service.getLoacation(u_id);

        getLocationCall.enqueue(new Callback<GetLocation>() {
            @Override
            public void onResponse(Call<GetLocation> call, Response<GetLocation> response) {
                if (response.isSuccessful())
                {
                    GetLocation getLocation = response.body();
                    LatLng latLng = new LatLng(Double.parseDouble(getLocation.getLatitude()),Double.parseDouble(getLocation.getLongitude()) );
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(getLocation.getFname()+" "+getLocation.getLname());
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    currentMarker = mGoogleMap.addMarker(markerOptions);
                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    scheduleThread();

                }
            }

            @Override
            public void onFailure(Call<GetLocation> call, Throwable t) {

            }
        });
    }

    public void mapStyle(){
        String[] mapStyle = {"Select Map Style","Normal","Satellite","Terrain","Hybrid"};

        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_dropdown_item,mapStyle);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#E8EAF6"));
                switch (position)
                {
                    case 0:
                        break;
                    case 1:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 2:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 3:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    case 4:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void scheduleThread(){
        handler=new Handler();
        run=new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                if(Utilities.isNetworkAvailable(getActivity())){
                    currentMarker.remove();
                    getLocations();

                }else{
                    Toast.makeText(getContext(), "Internet is not active", Toast.LENGTH_SHORT).show();
                }
            }
        };
        handler.postDelayed(run, 30000);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(handler!=null){
            handler.removeCallbacks(run);
            run=null;
            handler=null;
        }
    }
}
