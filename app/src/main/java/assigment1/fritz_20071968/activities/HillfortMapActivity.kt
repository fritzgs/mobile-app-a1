package assigment1.fritz_20071968.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.main.MainApp
import assigment1.fritz_20071968.models.HillfortModel
import assigment1.fritz_20071968.models.Location
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HillfortMapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{
    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var mMap: GoogleMap
    private lateinit var app : MainApp


    override fun onBackPressed() {
        startActivity(Intent(this@HillfortMapActivity, HillfortListActivity::class.java).putExtra("norm", "norm"))
        finish()
        super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        app = application as MainApp

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    //create a marker for location and use title as title
    fun createMarker(location : Location, title: String): Marker
    {
        var loc = LatLng(location.lat, location.lng)
        return mMap.addMarker(MarkerOptions().position(loc)
                .title(title)
                .snippet("Geo: "+loc.toString()))

    }


    override fun onMapReady(googleMap: GoogleMap)
    {
        mMap = googleMap

        //hillfort list of all hillforts associate with user
        var hillfortList : MutableList<HillfortModel> = app.users.findAll(app.getEmail())

        //for each hillfort in list that's not default location, create a marker
        for (hillfort in hillfortList) {
            if (hillfort.location.lat != 0.0 && hillfort.location.lng != 0.0 && hillfort.location.zoom != 0f)
                createMarker(hillfort.location, hillfort.title)
        }

        //this enables this activity to run when the hillfort list is empty.
        if(hillfortList.isNotEmpty())
        {
            var loc = LatLng(hillfortList[0].location.lat, hillfortList[0].location.lng)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 5f))
        }



    }





}