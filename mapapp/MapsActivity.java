package com.example.mapapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mapapp.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    LatLng centerLocation;
    Context mContext;

    Vector<MarkerOptions> markerOptions;

    private String url = "https://api.npoint.io/3e7531b912d4e09a8560";

    RequestQueue requestQueue;
    Gson gson;
    Hazard[] hazards;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gson = new GsonBuilder().create();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        centerLocation = new LatLng(6.4521, 100.2778);

        markerOptions = new Vector<>();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLocation, 8));
        enableMyLocation();
        sendRequest();

        mMap.addMarker(new MarkerOptions().position(centerLocation).title("You are Here"));
    }

    private void enableMyLocation() {
        String perms[] = {"android.permission.ACCESS_FINE_LOCATION"};

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                Log.d("user", "permission granted");
            }
        } else {
            //Permission to access the location is missing. Show rationale and request permission
            Log.d("user", "permission not granted");
            ActivityCompat.requestPermissions(this, perms, 200);
        }

    }

    public void sendRequest() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, onSuccess, onError);
        requestQueue.add(stringRequest);
    }

    public Response.Listener<String> onSuccess = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            hazards = gson.fromJson(response, Hazard[].class);

            Log.d("Hazard", "Number of data Hazard Point :" + hazards.length);

            if (hazards.length < 1) {
                Toast.makeText(getApplicationContext(), "Problem retrieving JSON data", Toast.LENGTH_LONG).show();
                return;
            }

            for (Hazard info : hazards) {
                String location = info.getHzLocation();
                String snippet = info.getHzDesc();
                Double lng = Double.parseDouble(info.getHzLng());
                Double lat = Double.parseDouble(info.getHzLat());

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(location)
                        .position(new LatLng(lat, lng))
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                mMap.addMarker(marker);

            }
        }
    };

    public Response.ErrorListener onError = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            ConnectivityManager cm = (ConnectivityManager) mContext
                                    .getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo activeNetwork = null;
                            if (cm != null) {
                                activeNetwork = cm.getActiveNetworkInfo();
                            }
                            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                                Toast.makeText(MapsActivity.this, "Server is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MapsActivity.this, "Your device is not connected to internet.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else if (error instanceof NetworkError || error.getCause() instanceof ConnectException
                                || (error.getCause().getMessage() != null
                                && error.getCause().getMessage().contains("connection"))) {
                            Toast.makeText(MapsActivity.this, "Your device is not connected to internet.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error.getCause() instanceof MalformedURLException) {
                            Toast.makeText(MapsActivity.this, "Bad Request.", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException
                                || error.getCause() instanceof JSONException
                                || error.getCause() instanceof XmlPullParserException
                        ) {
                            Toast.makeText(MapsActivity.this, "Parse Error (because of invalid json or xml).",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error.getCause() instanceof OutOfMemoryError) {
                            Toast.makeText(MapsActivity.this, "Out Of Memory Error.", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(MapsActivity.this, "server couldn't find the authenticated request.",
                                    Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
                            Toast.makeText(MapsActivity.this, "Server is not responding.", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException
                                || error.getCause() instanceof ConnectTimeoutException
                                || error.getCause() instanceof SocketException
                                || (error.getCause().getMessage() != null
                                && error.getCause().getMessage().contains("Connection timed out"))) {
                            Toast.makeText(MapsActivity.this, "Connection timeout error",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MapsActivity.this, "An unknown error occurred.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    };

    @Override
    public void finish() {
        super.finish();
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}


        //requestQueue.add(stringRequest);

    /*public Response.Listener<JSONArray>() onSuccess = new Response.Listener<JSONArray>() {

        @Override
        public void onResponse(JSONArray response) {
            hazards = gson.fromJson(response, Hazard[].class);

            //this will de displayed on logcat as debug
            Log.d("Hazard", "Number of Hazard Data Point: " + hazards.length);

            if (hazards.length <1){
                Toast.makeText(getApplicationContext(), "Problem retrieving JSON data", Toast.LENGTH_LONG). show();
                return;
            }

            for (Hazard info: hazards){

                Double lat = Double.parseDouble(info.getHzLat());
                Double lng = Double.parseDouble(info.getHzLng());

                String title = info.getHzType();
                //String time = info.hzTime;
                //String date = info.hzDate;
                String snippet = info.getHzRepname();

                MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lng))
                        .title(title)
                        .position(new LatLng(lat, lng))
                        .snippet(snippet)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

                mMap.addMarker(marker);
            }
        }

    };

        //check permission/ permission request

    public Response.ErrorListener onError = error -> Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
}


    ///**
     //* Enables the My Location layer if the fine location permission has been granted.
     //*/


    //@Override
    //protected void onResume() {
        //super.onResume();
//startLocationUpdates();
    //}

    //public void startLocationUpdates() {
        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.requestPermissions(this, perms, 200);
            //return;
        //}
        //fusedLocationClient.requestLocationUpdates(locationRequest,
                //locationCallback,
                //Looper.getMainLooper());
    //}






