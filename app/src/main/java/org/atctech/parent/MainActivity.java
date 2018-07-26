package org.atctech.parent;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.atctech.parent.ApiRequest.ApiRequest;
import org.atctech.parent.fragments.ContactUsFragment;
import org.atctech.parent.fragments.CourseTeacherFragment;
import org.atctech.parent.fragments.FeesFragment;
import org.atctech.parent.fragments.HolidayFragment;
import org.atctech.parent.fragments.HomeFragment;
import org.atctech.parent.fragments.MapFragment;
import org.atctech.parent.fragments.PasswordChangeFragment;
import org.atctech.parent.fragments.ProfileFragment;
import org.atctech.parent.fragments.ResultsFragment;
import org.atctech.parent.fragments.SetGeofenceFragment;
import org.atctech.parent.fragments.SettingsFragment;
import org.atctech.parent.fragments.TeacherFragment;
import org.atctech.parent.fragments.TimeTableFragment;
import org.atctech.parent.model.StudentDetails;
import org.atctech.parent.preferences.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private static final String TAG_RESULT = "result";
    private static final String TAG_TEACHER = "teacher";
    private static final String TAG_HOLIDAY = "holiday";
    private static final String TAG_TIME_TABLE = "time_table";
    private static final String TAG_COURSE_TEACHER = "course_teacher";
    private static final String TAG_FEES = "fees";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_CONTACT_US = "contact us";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_MAP = "map";
    private static final String TAG_ALERT = "alert";
    public static ActionBarDrawerToggle actionBarDrawerToggle;
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = TAG_HOME;
    Toolbar toolbar;
    ActionBar actionBar;
    boolean doublePressedBackToExit = false;
    DrawerLayout drawerLayout;
    NavigationView navigationDrawer;
    View navHeader;
    Session session;
    ApiRequest service;
    TextView parentPhoneNO;
    Button logoutBtn, changePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.myToolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationDrawer = findViewById(R.id.main_drawer);


        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("SMS");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        navHeader = navigationDrawer.getHeaderView(0);
        parentPhoneNO = navHeader.findViewById(R.id.parentPhoneNo);
        logoutBtn = navHeader.findViewById(R.id.logoutBtn);
        changePasswordBtn = navHeader.findViewById(R.id.changePassBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                }

                new AlertDialog.Builder(MainActivity.this).setTitle("Logout")
                        .setMessage("Are you sure you want to logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("UID", "");
                                editor.apply();
                                session.setLoggedIn(false);
                                session.deleteUser();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                finish();
                                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }
        });


        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawers();
                }

                PasswordChangeFragment fragment = new PasswordChangeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commitAllowingStateLoss();

            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(ApiRequest.class);

        session = Session.getInstance(getSharedPreferences("prefs", Context.MODE_PRIVATE));

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        String id = sharedPreferences.getString("PHONE", null);
        getStudentdetails(id);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final Dialog localDialog = new Dialog(MainActivity.this);
//                localDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                localDialog.setContentView(R.layout.dialog_book_img);
//                localDialog.getWindow().setLayout(-1, -1);
//                localDialog.show();
//                ImageView localImageView1 = localDialog.findViewById(R.id.iv_dialog_img);
//                ImageView localImageView2 = localDialog.findViewById(R.id.iv_dialog_cancle);
//
//                if (session.getUser().getFile().equalsIgnoreCase("") && session.getUser().getFile().isEmpty()) {
//
//                    Picasso.with(MainActivity.this).load(R.drawable.profile).into(localImageView1);
//
//
//                }else
//                {
//                    Picasso.with(MainActivity.this).load(session.getUser().getFile()).placeholder(R.drawable.profile).into(localImageView1);
//                }
//
//                localImageView2.setOnClickListener(new View.OnClickListener()
//                {
//                    public void onClick(View paramAnonymousView)
//                    {
//                        localDialog.dismiss();
//                    }
//                });
//            }
//        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawerOpen, R.string.drawerClose) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                invalidateOptionsMenu();
            }
        };


        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

//


        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        setUpNavigationView();

    }


    private void loadHomeFragment() {

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawerLayout.closeDrawers();

            return;
        }

        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.main_fragment, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();

        drawerLayout.closeDrawers();

        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;

            case 1:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 2:
                TeacherFragment teacherFragment = new TeacherFragment();
                return teacherFragment;

            case 3:
                CourseTeacherFragment courseTeacherFragment = new CourseTeacherFragment();
                return courseTeacherFragment;

            case 4:
                ResultsFragment resultsFragment = new ResultsFragment();
                return resultsFragment;

            case 5:
                TimeTableFragment timeTableFragment = new TimeTableFragment();
                return timeTableFragment;

            case 6:
                MapFragment mapFragment = new MapFragment();
                return mapFragment;

            case 7:
                SetGeofenceFragment geofenceFragment = new SetGeofenceFragment();
                return geofenceFragment;

            case 8:
                HolidayFragment holidayFragment = new HolidayFragment();
                return holidayFragment;

            case 9:
                FeesFragment feesFragment = new FeesFragment();
                return feesFragment;

            case 10:
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;

            case 11:
                ContactUsFragment contactUsFragment = new ContactUsFragment();
                return contactUsFragment;

            default:
                return new HomeFragment();
        }
    }

    private void setUpNavigationView() {
        navigationDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.drawerHome:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;

                    case R.id.drawerAccount:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PROFILE;
                        break;

                    case R.id.drawerTeacher:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_TEACHER;
                        break;

                    case R.id.courseTeacher:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_COURSE_TEACHER;
                        break;

                    case R.id.drawerResults:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_RESULT;
                        break;

                    case R.id.drawerTimeTable:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_TIME_TABLE;
                        break;

                    case R.id.drawerMap:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_MAP;
                        break;

                    case R.id.alerts:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_ALERT;
                        break;

                    case R.id.drawerHoliday:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_HOLIDAY;
                        break;

                    case R.id.drawerFees:
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_FEES;
                        break;

                    case R.id.drawerSettings:
                        navItemIndex = 10;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;

                    case R.id.drawerContactUs:
                        navItemIndex = 11;
                        CURRENT_TAG = TAG_CONTACT_US;
                        break;

                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();


        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();

        } else if (fm.getBackStackEntryCount() > 0) {


            fm.popBackStack();

        } else if (doublePressedBackToExit) {
            super.onBackPressed();

        } else {
            this.doublePressedBackToExit = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    doublePressedBackToExit = false;
                    session.deleteUser();
                }
            }, 2000);
        }
    }

    public void getStudentdetails(String id) {

        Call<StudentDetails> studentDetailsCall = service.getStudentDetails(id);

        studentDetailsCall.enqueue(new Callback<StudentDetails>() {
            @Override
            public void onResponse(Call<StudentDetails> call, Response<StudentDetails> response) {
                if (response.isSuccessful()) {
                    StudentDetails studentDetails = response.body();
                    session.saveUser(studentDetails);
                    parentPhoneNO.setText(session.getUser().getGphone());

                } else {
                    Toast.makeText(getApplicationContext(), "not responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StudentDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
