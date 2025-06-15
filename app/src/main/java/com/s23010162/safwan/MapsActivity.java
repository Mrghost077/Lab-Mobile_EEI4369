package com.s23010162.safwan;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText etAddress;
    private Button btnShowLocation;
    private Button btnToSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        etAddress = findViewById(R.id.etAddress);
        btnShowLocation = findViewById(R.id.btnShowLocation);
        btnToSensor = findViewById(R.id.btnToSensor);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mMap);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        btnShowLocation.setOnClickListener(v -> showLocationOnMap());
        btnToSensor.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this,SensorActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private void showLocationOnMap() {
        String location = etAddress.getText().toString();
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter an address", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (addresses == null || addresses.isEmpty()) {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
                return;
            }

            Address address = addresses.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Geocoder failed", Toast.LENGTH_SHORT).show();
        }
    }
}
