package com.example.premierelog;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int REQUEST_LOCATION_PERMISSION=999;
    private GoogleMap mMap;
    String posizione;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
                             // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int n=0;
        Double lat,lng;
        try {
            for (int i=0;i<=Info_spedizione.Indirizzo.size();i++){
                    posizione =getLocationFromAddress(Info_spedizione.Indirizzo .get(n).toString());
                    if (posizione!=null){
                    lat=Double.parseDouble(posizione.substring(0,posizione.indexOf(",")));
                    lng=Double.parseDouble(posizione.substring(posizione.substring(0,posizione.indexOf(",")).length()+1,posizione.length()));
                    // Add a marker in Sydney and move the camera
                    LatLng coordinate = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(coordinate).title(Info_spedizione.Destinatario.get(n).toString()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate,8f));
                    }
                    n+=1;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        enableMyLocation();
    }

    public String getLocationFromAddress(String strAddress){
        Geocoder coder=new Geocoder(MapsActivity.this);
        List<Address> address;
        try {
            address= coder.getFromLocationName(strAddress,1);
            if (address==null){
                return null;
            }
            Address location=address.get(0);
            double lat=location.getLatitude();
            double lng=location.getLongitude();
            return lat +","+ lng;
        } catch (Exception e){
            return null;
        }
    }


        private void enableMyLocation() {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                                {Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            // Check if location permissions are granted and if so enable the
            enableMyLocation();
            switch (requestCode) {
                case REQUEST_LOCATION_PERMISSION:
                    if (grantResults.length > 0
                            && grantResults[0]
                            == PackageManager.PERMISSION_GRANTED) {
                        enableMyLocation();
                        break;
                    }
            }


        }
}
