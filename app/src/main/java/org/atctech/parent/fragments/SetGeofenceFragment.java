package org.atctech.parent.fragments;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.R;
import org.atctech.parent.preferences.Session;
import org.atctech.parent.utils.GPSTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetGeofenceFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,OnMapReadyCallback {

    ApiRequest service;
    SupportMapFragment mSupportMapFragment;

    private GoogleMap googleMap;

    Location mcurrentLocation;

    GPSTracker mGpsTracker;

    GoogleApiClient mLocationClient;

    DraggableCircle mDraggableCircle;


    double DEFAULT_RADIUS = 1500;

    public static final double RADIUS_OF_EARTH_METERS = 6371009;

    private List<DraggableCircle> mCircles = new ArrayList<>(1);

    private int mStrokeColor;
    private int mFillColor;
    Session session;
    Marker centerMarker;
    Marker radiusMarker;
    Circle circle;
    Marker currentMarker;

    double Radius;
    double lat = 0;
    double lon = 0;
    float start;
    float end;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences.Editor editor;

    LatLng mLatLng;
    Timer mTimer;
    float zoomLevel = 10.0f;

    public SetGeofenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Set Alert");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);
        session = Session.getInstance(getActivity().getSharedPreferences("prefs",getContext().MODE_PRIVATE));
        mGpsTracker = new GPSTracker(getActivity(),getActivity());
        mLocationClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mTimer=new Timer();

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        View rootView = inflater.inflate(R.layout.fragment_set_geofence, container, false);

        mSupportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2));
        if (mSupportMapFragment == null) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mSupportMapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.main_fragment, mSupportMapFragment);
            fragmentTransaction.commitAllowingStateLoss();

        }

        mSupportMapFragment.getMapAsync(this);

        mLocationClient.connect();

        return rootView;
    }

    public void currentLocation()
    {
        if(mGpsTracker.canGetLocation())
        {
            double lat = mGpsTracker.getLatitude();
            double lon = mGpsTracker.getLongitude();

            LatLng currentLocation=new LatLng(lat, lon);

            googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .position((currentLocation)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,zoomLevel));

        }
    }
    public void showGeofence()
    {
        if(sharedpreferences.contains("latitude"))
        {
            lat=(double)sharedpreferences.getFloat("latitude",0);
        }
        if(sharedpreferences.contains("longitude"))
        {
            lon=(double)sharedpreferences.getFloat("longitude",0);
        }
        if(sharedpreferences.contains("radius"))
        {
            Radius=(double)sharedpreferences.getFloat("radius",0);
        }
        if(sharedpreferences.contains("zoom"))
        {
            zoomLevel= sharedpreferences.getFloat("zoom",0);
        }
        mLatLng=new LatLng(lat, lon);

        mCircles.clear();
        googleMap.clear();
        mDraggableCircle = new DraggableCircle(mLatLng, Radius);
        mCircles.add(mDraggableCircle);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,zoomLevel));
    }

    public void SetGeofence()
    {
        editor.putFloat("latitude",(float) circle.getCenter().latitude);
        editor.putFloat("longitude",(float) circle.getCenter().longitude);
        editor.putFloat("radius",(float) circle.getRadius());
        editor.putFloat("zoom", googleMap.getCameraPosition().zoom);
        editor.commit();

        String latitude = String.valueOf(circle.getCenter().latitude);
        String longitude = String.valueOf(circle.getCenter().longitude);
        String radius = String.valueOf(circle.getRadius());
        String zoom = String.valueOf(googleMap.getCameraPosition().zoom);
        String student_id = session.getUser().getU_id();

        Call<ResponseBody> responseBodyCall = service.updateGeofence(latitude,longitude,radius,zoom,student_id);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    Snackbar.make(getView(),"Geofence update successfully",Snackbar.LENGTH_INDEFINITE).
                            setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).
                            show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    public void setUpMap() {

        mStrokeColor = Color.BLACK;

        final Timer mTimer = new Timer();

        System.out.println("in timer before run");
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub

                if(googleMap != null)
                {

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            googleMap.setOnMarkerDragListener(SetGeofenceFragment.this);

                            mStrokeColor = Color.BLACK;

                            if(mGpsTracker.canGetLocation())
                            {
                                {
                                    lat = mGpsTracker.getLatitude();
                                    lon = mGpsTracker.getLongitude();
                                }
                                if(sharedpreferences.contains("latitude"))
                                {
                                    lat=(double)sharedpreferences.getFloat("latitude",0);
                                }
                                if(sharedpreferences.contains("longitude"))
                                {
                                    lon=(double)sharedpreferences.getFloat("longitude",0);
                                }
                                if(sharedpreferences.contains("radius"))
                                {
                                    Radius=(double)sharedpreferences.getFloat("radius",0);
                                }
                                if(sharedpreferences.contains("zoom"))
                                {
                                    zoomLevel=(float)sharedpreferences.getFloat("zoom",0);
                                }
                                System.out.println("latitude in connected "+lat);

                                mLatLng=new LatLng(lat, lon);
                                mCircles.clear();
                                googleMap.clear();
                                if(Radius>DEFAULT_RADIUS)
                                {
                                    mDraggableCircle = new DraggableCircle(mLatLng, Radius);
                                    mCircles.add(mDraggableCircle);
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,zoomLevel));
                                }
                                else
                                {
                                    mDraggableCircle = new DraggableCircle(mLatLng, DEFAULT_RADIUS);
                                    mCircles.add(mDraggableCircle);
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 12.0f));
                                }
                            }

                        }
                    });
                    mTimer.cancel();
                }

            }

        }, 2000, 2000);

    }

    @Override
    public void onMapReady(GoogleMap googleMap1) {

        if (sharedpreferences.contains("latitude") || sharedpreferences.contains("longitude") || sharedpreferences.contains("radius")
                || sharedpreferences.contains("zoom")) {
            setUpMap();
        }
        {
            googleMap = googleMap1;
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            GPSTracker gps = new GPSTracker(getContext(), getActivity());

            googleMap = googleMap1;
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            LatLng latLng = new LatLng(gps.getLatitude(), gps.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Location");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currentMarker = googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        }

    }

    private class DraggableCircle {

        public DraggableCircle(LatLng center, double radius) {

            Radius = radius;

            centerMarker = googleMap.addMarker(new MarkerOptions()
                    .position(center)
                    .draggable(true));

            radiusMarker = googleMap.addMarker(new MarkerOptions()
                    .position(toRadiusLatLng(center, radius))
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE)));

            circle = googleMap.addCircle(new CircleOptions()
                    .center(center)
                    .radius(radius)
                    .strokeColor(mStrokeColor)
                    .fillColor(mFillColor));

            float[] results = new float[2];

            Location.distanceBetween(centerMarker.getPosition().latitude, centerMarker.getPosition().longitude, radiusMarker.getPosition().latitude, radiusMarker.getPosition().longitude, results);

            start=results[0];
        }

        public boolean onMarkerMoved(Marker marker) {
            if (marker.equals(centerMarker)) {

                circle.setCenter(marker.getPosition());
                radiusMarker.setPosition(toRadiusLatLng(marker.getPosition(), Radius));
                return true;
            }
            if (marker.equals(radiusMarker)) {

                Radius = toRadiusMeters(centerMarker.getPosition(), radiusMarker.getPosition());
                circle.setRadius(Radius);
                return true;
            }
            return false;
        }
        /** Generate LatLng of radius marker */
        private LatLng toRadiusLatLng(LatLng center, double radius) {
            double radiusAngle = Math.toDegrees(radius / RADIUS_OF_EARTH_METERS) /
                    Math.cos(Math.toRadians(center.latitude));
            return new LatLng(center.latitude, center.longitude + radiusAngle);
        }

        private double toRadiusMeters(LatLng center, LatLng radius) {
            float[] result = new float[1];
            Location.distanceBetween(center.latitude, center.longitude,
                    radius.latitude, radius.longitude, result);

            return result[0];
        }

    }
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onConnected(Bundle arg0) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    @Override
    public void onMarkerDrag(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMarkerDragEnd(Marker arg0) {

        onMarkerMoved(arg0);

        float[] results = new float[2];
        zoomLevel=googleMap.getCameraPosition().zoom;
        Location.distanceBetween(mGpsTracker.getLatitude(), mGpsTracker.getLongitude(),
                circle.getCenter().latitude, circle.getCenter().longitude, results);

        end=results[0];
    }
    private void onMarkerMoved(Marker marker) {
        for (DraggableCircle draggableCircle : mCircles) {
            if (draggableCircle.onMarkerMoved(marker)) {
                break;
            }
        }
    }
    @Override
    public void onMarkerDragStart(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onMarkerClick(Marker arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.geofence_menu, menu);
        final MenuItem addGeofence = menu.findItem(R.id.add_geofence);
        final MenuItem currentLocationParent = menu.findItem(R.id.geofenceCurrentLocation);
        final MenuItem showAllGeofence = menu.findItem(R.id.geofenceShow);


        addGeofence.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SetGeofence();
                return true;
            }
        });

        currentLocationParent.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                currentLocation();
                return true;
            }
        });

        showAllGeofence.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (sharedpreferences.contains("latitude") || sharedpreferences.contains("longitude") || sharedpreferences.contains("radius")
                        || sharedpreferences.contains("zoom")) {
                    showGeofence();
                }
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}
