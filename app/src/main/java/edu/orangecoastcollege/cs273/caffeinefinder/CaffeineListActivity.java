package edu.orangecoastcollege.cs273.caffeinefinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

public class CaffeineListActivity extends AppCompatActivity implements onMapReadyCallback{

    private DBHelper db;
    private List<Location> allLocationsList;
    private ListView locationsListView;
    private LocationListAdapter locationListAdapter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caffeine_list);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        db.importLocationsFromCSV("locations.csv");

        allLocationsList = db.getAllCaffeineLocations();
        locationsListView = (ListView) findViewById(R.id.locationsListView);
        locationListAdapter = new LocationListAdapter(this, R.layout.location_list_item, allLocationsList);
        locationsListView.setAdapter(locationListAdapter);

        // Hook up our support map fragment to this activity
        SupportMapFragment caffeineMapFragment =
                (SupportFragment) getSupportFragmentManager().findFragmentById(R.id.caffeineMapFragment);

        caffeineMapFragment.getMapAsync(this);
	
    }
	
	@Override 
	public void onMapReady(GoogleMap googleMap){
		// Use the latitude and longitude for each Location to create a marker on the GoogleMap
		mMap = googleMap;
		// Loop through each locationListAdapter
		for (Location location : allLocationsList){
			LatLng coordinate = new LatLng(location.getLatitude(), location.getLatitude());
            // Add a marker at that coordinate
            mMap.addMarker(new MarkerOptions(). position(corrdinate),title(location.getName()));
		}
        LatLng currentPos = new LatLng(33.671028, -117.911305);
        CameraPosition cameraPostion = new CameraPosition.Builder().target(currentPos).zoom(14.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPostion(cameraPosition);
        mMap.moveCamera(cameraUpdate);
	}
}
