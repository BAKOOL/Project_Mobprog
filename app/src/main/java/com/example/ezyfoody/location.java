package com.example.ezyfoody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class location extends AppCompatActivity implements LocationListener {


    TextView currentlocation,centerdistance,anotherdistance,storecenter,storeanother;
    double latcur , lngcur;
    double latcenter = -6.150760, lngcenter= 106.826927, latanother = -6.300641, lnganother =  106.814095;
    Button searchloaction;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);

        searchloaction= findViewById(R.id.loaction_search);
        currentlocation= findViewById(R.id.currentlocation);
        centerdistance= findViewById(R.id.location_center_distance);
        anotherdistance= findViewById(R.id.location_another_distance);
        storecenter=findViewById(R.id.location_center);
        storeanother=findViewById(R.id.loaction_another);

        if(ContextCompat.checkSelfPermission(location.this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(location.this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        try {

            Geocoder geocoder= new Geocoder( location.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            latcur= location.getAltitude();
            lngcur= location.getLongitude();
            currentlocation.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void listener(View view) {
        if (view.getId()==R.id.loaction_search){
            getLocation();
            calculateDistance(latcur, lngcur, latcenter, lngcenter, centerdistance);
            calculateDistance(latcur, lngcur, latanother, lnganother, anotherdistance);
        }else if(view.getId()==R.id.button_center){
            Intent intent = new Intent(this, myOrder.class);
            intent.putExtra("address", currentlocation.getText().toString());
            intent.putExtra("store", storecenter.getText().toString());
            startActivity(intent);
        }else if(view.getId()==R.id.button_another){
            Intent intent = new Intent(this, myOrder.class);
            intent.putExtra("address", currentlocation.getText().toString());
            intent.putExtra("store", storeanother.getText().toString());
            startActivity(intent);
        }
    }

    private void calculateDistance(double lat1, double lng1, double lat2, double lng2, TextView totaldistance) {
        double longdiff= lng1 - lng2;
        double distance= Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(longdiff));
        distance= Math.acos(distance);
        distance= rad2deg(distance);
        distance= distance* 60 * 1.1515;
        distance= distance* 1.609344;
        totaldistance.setText(String.format(Locale.US, "%2f Km",distance));
    }

    private double rad2deg(double distance) {
        return (distance*180.0/Math.PI);
    }

    private double deg2rad(double latcur) {
        return (latcur*Math.PI/180.0);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager= (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, location.this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}