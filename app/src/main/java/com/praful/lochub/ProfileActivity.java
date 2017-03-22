package com.praful.lochub;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    public TextView textViewUserEmail;
    private Button buttonLogout, btnGet;
    public ImageButton imgbtnMap, imgbtnLocate;
    public EditText edtxtLati, edtxtLongi;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Double lat, lont;
    String txtLati, txtLongi;

    /*double total = 44;
    String total2 = String.valueOf(total);*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        SpannableString spannableString = new SpannableString(actionBar.getTitle());
        spannableString.setSpan(new mytypeface("", Typeface.createFromAsset(getAssets(), "lochub.ttf")), 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(spannableString);
        //initializing views
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        btnGet = (Button) findViewById(R.id.btnGet);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        edtxtLati = (EditText) findViewById(R.id.edtxtLati);
        edtxtLongi = (EditText) findViewById(R.id.edtxtLongi);
        imgbtnMap = (ImageButton) findViewById(R.id.imgbtnMap);
        imgbtnLocate = (ImageButton) findViewById(R.id.imgbtnLocate);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat = location.getLatitude();
                txtLati = Double.toString(lat);
                edtxtLati.setText("" + txtLati);
                lont = location.getLongitude();
                txtLongi = Double.toString(lont);
                edtxtLongi.setText("" + txtLongi);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        //VERSION AND PERMISSION CHECKING
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                return;
            }
        } else {
            configureButton();
        }

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();


        //displaying logged in user name
        textViewUserEmail.setText(user.getEmail());

        //adding listener to button
        buttonLogout.setOnClickListener(this);

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                Toast.makeText(ProfileActivity.this, "Please wait whle location is being retrieved...", Toast.LENGTH_SHORT).show();
            }
        });

        imgbtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txtLati)||TextUtils.isEmpty(txtLongi))
                {
                    Toast.makeText(ProfileActivity.this, "Click on Get Location to get location", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent localIntent = new Intent(getApplicationContext(), MapsActivity.class);
                    localIntent.putExtra("txt_latitude", txtLati);
                    localIntent.putExtra("txt_longitude", txtLongi);
                    Toast.makeText(ProfileActivity.this, "Co-ordinates on Map.", Toast.LENGTH_SHORT).show();
                    startActivity(localIntent);
                }
                    }
        });
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permission, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
        }

    }

    private void configureButton() {

        imgbtnLocate.setOnClickListener(this);
    }

    public void onClick(View view) {
        //if logout is pressed
        if (view == buttonLogout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (view == imgbtnLocate) {
            if (TextUtils.isEmpty(txtLati)||TextUtils.isEmpty(txtLongi))
            {
                Toast.makeText(ProfileActivity.this, "Location cannot be empty!", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(getApplicationContext(), Timeline.class);
                intent.putExtra("txt_loc_lati", txtLati);
                intent.putExtra("txt_loc_longi", txtLongi);
                startActivity(intent);
                /*b.putDouble("txt_loc_longi",lont);
                b.putDouble("txt_loc_lati", lat);*/
            }

        }
      }
    }