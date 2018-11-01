package assigment1.fritz_20071968.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.models.Location

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener{
  override fun onMarkerClick(marker: Marker): Boolean {
    val loc = LatLng(location.lat, location.lng)
    marker.setSnippet("GPS : " + loc.toString())
    return false  }

  private lateinit var mMap: GoogleMap
  var location = Location()

  override fun onMarkerDragStart(marker: Marker) {


    TODO("not implemented") //To change body of created functions use File | SettingsActivity | File Templates.
  }

  override fun onMarkerDragEnd(marker: Marker) {
    location.lat = marker.position.latitude
    location.lng = marker.position.longitude
    location.zoom = mMap.cameraPosition.zoom
  }

  override fun onMarkerDrag(marker: Marker) {
    TODO("not implemented") //To change body of created functions use File | SettingsActivity | File Templates.
  }

  override fun onBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    setResult(Activity.RESULT_OK, resultIntent)
    finish()
    super.onBackPressed()
  }



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_maps)
    location = intent.extras.getParcelable<Location>("location")
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    val mapFragment = supportFragmentManager
        .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
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
  override fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap
    mMap.setOnMarkerDragListener(this)
    mMap.setOnMarkerClickListener(this)
    // Add a marker in Sydney and move the camera
    val loc = LatLng(location.lat, location.lng)
    val options = MarkerOptions()
        .title("Hillfort")
        .snippet("GPS: " + loc.toString())
        .draggable(true)
        .position(loc)
    mMap.addMarker(options)
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
  }

}
