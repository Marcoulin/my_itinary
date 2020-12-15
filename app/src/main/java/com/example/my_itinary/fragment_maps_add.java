package com.example.my_itinary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mapbox.geojson.Point;

public class fragment_maps_add extends Fragment {

    Point point1, point2, point3;

    public  fragment_maps_add(Point point1)
    {
        this.point1 = point1;
    }

    public fragment_maps_add(Point point1, Point point2, Point point3)
    {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }



    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng location = new LatLng(point1.latitude(),point1.longitude());
            if(point1 != null && point2 != null && point3 != null)
            {

                PolylineOptions polylineOptions = new PolylineOptions()
                        .add(new LatLng(point1.latitude(), point1.longitude()))
                        .add(new LatLng(point2.latitude(), point2.longitude()))
                        .add(new LatLng(point3.latitude(), point3.longitude()));
                Polyline polyline = googleMap.addPolyline(polylineOptions);
            }
            googleMap.addMarker(new MarkerOptions().position(location).title("Marker"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
            googleMap.setOnPoiClickListener(pointOfInterest -> {
                Toast.makeText(getContext(), "Clicked: " +
                                pointOfInterest.name + "\nPlace ID:" + pointOfInterest.placeId +
                                "\nLatitude:" + pointOfInterest.latLng.latitude +
                                " Longitude:" + pointOfInterest.latLng.longitude,
                        Toast.LENGTH_SHORT).show();
            });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}