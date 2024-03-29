package com.hakkicanbuluc.foursquarejavaparseclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageView imageView;
    private TextView nameText, typeText, atmosphereText;
    String placeName, placeObjectID;
    Double lat, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameText = findViewById(R.id.name_text_detail_activity);
        typeText = findViewById(R.id.type_text_detail_activity);
        atmosphereText = findViewById(R.id.atmosphere_text_detail_activity);
        imageView = findViewById(R.id.imageview_detail_activity);

        Intent intent = getIntent();
        placeObjectID = intent.getStringExtra("id");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById
                        (R.id.mapDetail);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        getData();
    }

    public void getData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Places");
        query.whereEqualTo("objectId", placeObjectID);
        query.findInBackground((objects, e) -> {
            if (e != null) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            } else {
                if (objects.size() > 0) {
                    for (ParseObject object : objects) {
                        ParseFile parseFile = (ParseFile) object.get("image");
                        parseFile.getDataInBackground((data, e1) -> {
                            if (e1 == null && data != null) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                imageView.setImageBitmap(bitmap);

                                placeName = object.getString("name");
                                nameText.setText(placeName);
                                typeText.setText(object.getString("type"));
                                atmosphereText.setText(object.getString("atmosphere"));

                                lat = Double.parseDouble(object.getString("latitude"));
                                longi = Double.parseDouble(object.getString("longitude"));
                                mMap.clear();
                                LatLng latLng = new LatLng(lat, longi);
                                mMap.addMarker(new MarkerOptions().position(latLng).title(placeName));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                            }
                        });
                    }
                }
            }
        });
    }
}