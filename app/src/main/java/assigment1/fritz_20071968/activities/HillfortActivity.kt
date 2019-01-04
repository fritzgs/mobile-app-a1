/***
 * @author Fritz Gerald Santos - adapted from the Mobile App Development labs by Eamonn Deleastar
 *
 * This acittivy is for adding/editing hillfort entries
 */

package assigment1.fritz_20071968.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import assigment1.fritz_20071968.main.MainApp
import assigment1.fritz_20071968.models.HillfortModel
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.helpers.readImage
import assigment1.fritz_20071968.helpers.readImageFromPath
import assigment1.fritz_20071968.helpers.showImagePicker
import assigment1.fritz_20071968.models.Location
import kotlinx.android.synthetic.main.main.*
import org.jetbrains.anko.*
import java.util.*


class HillfortActivity : AppCompatActivity(), AnkoLogger{

  var hillfort = HillfortModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  /**
   * Runs when the activity starts
   */
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Hillfort Started")

    app = application as MainApp
    var edit = false


    //WHEN EDIT MODE
    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit") //gets the hilfort model
      //sets the layout components text using hillfort data
      hillfortTitle.setText(hillfort.title)
      carddescription.setText(hillfort.description)
      chooseImage.setText("Change Image")
      btnAdd.setText(R.string.save)
      ratingBar.rating = hillfort.rating

      //set the icon of the favourite feature
      //If it has been set as favoruite - set the rating bar to true and change the icon to on
      if(hillfort.favourite == true)
      {
        favourite.setChecked(true)
        favourite.background = resources.getDrawable(android.R.drawable.btn_star_big_on)
        favourite.textOn = ""
      }
      else //set the rating bar to false and the icon to off
      {
        favourite.setChecked(false)
        favourite.background = resources.getDrawable(android.R.drawable.btn_star_big_off)
        favourite.textOff = ""
      }


      //Image button - if there's a picture already, change the button text
      hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
      if(hillfort.image != null) {
        chooseImage.setText(R.string.change_hillfort_image)
      }

      //location button - set the location to the data in the hillfort
      hillfortLocation.setOnClickListener {
        val location = Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom)
        if (location.zoom != 0f) {
          location.lat =  hillfort.location.lat
          location.lng = hillfort.location.lng
          location.zoom = hillfort.location.zoom
        }
        startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST) //start google maps activity
      }

      // @author Fritz Gerald Santos - as described in edit mode.
      if(hillfort.visited == true)
      {
        visited_checkbox.setChecked(true)
        visited_checkbox.setText(String.format(getString(R.string.checkbox), hillfort.date))
      }

      //rating bar listener - when changed, change the hillfort value to new value
      ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
        hillfort.rating = rating
      }

      //favourite listener - if clicked, set check, change hillfort favourite to true and change icon to on
      favourite.setOnCheckedChangeListener{ _ , isChecked ->
        if(favourite.isChecked) {
          favourite.setChecked(true)
          favourite.background = resources.getDrawable(android.R.drawable.btn_star_big_on)
          favourite.textOff = ""
          hillfort.favourite = true

        }
        else { //if clicked, set check to false, change hillfort favourite to false and change icon to off
          favourite.setChecked(false)
          favourite.background = resources.getDrawable(android.R.drawable.btn_star_big_off)
          favourite.textOff = ""
          hillfort.favourite = false

        }
      }

      //@author Fritz Gerald Santos - visisted checkbox
      visited_checkbox.setOnCheckedChangeListener{buttonView, isChecked ->
        if(visited_checkbox.isChecked) //if it has been checked - set the text of the checkbox component to string/checkbox + date it was checked
        {
          hillfort.date = Date()
          visited_checkbox.setChecked(true)
          visited_checkbox.setText(String.format(getString(R.string.checkbox), hillfort.date))
          hillfort.visited = true
        }
        else
        {
          visited_checkbox.setChecked(false)
          visited_checkbox.setText(String.format(getString(R.string.checkbox), "")) //if *not checked* - just set text as "visisted"
          hillfort.visited = false
        }
      }
    }


    //ADD NEW MODE
    //@author Fritz Gerald Santos - as previously described
    visited_checkbox.setOnCheckedChangeListener{buttonView, isChecked ->
      if(visited_checkbox.isChecked)
      {
        hillfort.date = Date()
        visited_checkbox.setChecked(true)
        visited_checkbox.setText(String.format(getString(R.string.checkbox), hillfort.date))
        hillfort.visited = true
      }
      else
      {
        visited_checkbox.setChecked(false)
        visited_checkbox.setText(String.format(getString(R.string.checkbox), ""))
        hillfort.visited = false
      }
    }

    //rating bar listener - change the value of hillfort rating to new value
    ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
      hillfort.rating = rating
    }

    //favourite listener - if clicked, set check, change hillfort favourite to true and change icon to on
    favourite.setOnCheckedChangeListener{ _ , isChecked ->
      if(isChecked) {
        favourite.setChecked(true)
        favourite.background = resources.getDrawable(android.R.drawable.btn_star_big_on)
        favourite.textOn = ""
        hillfort.favourite = true
      }
      else { //if clicked, set check to false, change hillfort favourite to false and change icon to off
        favourite.setChecked(false)
        favourite.background = resources.getDrawable(android.R.drawable.btn_star_big_off)
        favourite.textOff = ""
        hillfort.favourite = false

      }
    }


    //location button listener
    hillfortLocation.setOnClickListener {
      val location = Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom)
      if (location.zoom != 0f) {
        location.lat = hillfort.location.lat
        location.lng = hillfort.location.lng
        location.zoom = hillfort.location.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    //Add/save button
    btnAdd.setOnClickListener()
    {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = carddescription.text.toString()
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title) //notifies if title is missing
      } else {
        if (edit) {
          app.users.updateHillfort(hillfort.copy(), app.getEmail()) //update if edit
          toast("Changes saved") //notifies when change saved

        } else {
          app.users.createHillfort(hillfort.copy(), app.getEmail()) //add if new
          toast("Hillfort has been added") //notifies when add saved
          setResult(AppCompatActivity.RESULT_OK)
          startActivity(Intent(this@HillfortActivity, HillfortListActivity::class.java).putExtra("norm", "norm"))
          finish() //closes the activity

        }
        info("Add Button Pressed:  $hillfortTitle")

      }
    }//end btnAdd



    //image picker listener
    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }//End chooseImage
  }//ENF onCreate


  override fun onBackPressed() {
    startActivity(Intent(this@HillfortActivity, HillfortListActivity::class.java).putExtra("norm", "norm"))
    finish()
    super.onBackPressed()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_add, menu)



    //if in edit more, enable sharing and delete
    if(intent.hasExtra("hillfort_edit"))
    {
      menu?.getItem(0)?.setVisible(true)
      menu?.getItem(2)?.setVisible(true)
    }

    return super.onCreateOptionsMenu(menu)


  }//END onCreateOptionsMenu

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {

      R.id.share ->
      {

        val i = Intent(android.content.Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject test")

        //if the location has been set and not the default, include in the text
        if(hillfort.location.lat != 0.0 && hillfort.location.lng != 0.0 && hillfort.location.zoom != 0f) {
          i.putExtra(android.content.Intent.EXTRA_TEXT, hillfort.title +
                  ": \n" + hillfort.description +
                  " \nLocation: " + hillfort.location.lat + ", " + hillfort.location.lng +
                  " \n Rating: " + hillfort.rating)
          startActivity(Intent.createChooser(i, "Share via"))
        }
        // if the location is still default, put location unknown in the text
        else
        {
          i.putExtra(android.content.Intent.EXTRA_TEXT, hillfort.title +
                  ": \n" + hillfort.description +
                  " \nLocation: Unknown"  +
                  " \n Rating: " + hillfort.rating)
          startActivity(Intent.createChooser(i, "Share via"))
        }
      }


      //when cancle button is pressed
      R.id.item_cancel -> {
        startActivity(Intent(this@HillfortActivity, HillfortListActivity::class.java).putExtra("norm", "norm"))
        finish()
      }

      //@author Fritz Gerald Santos - deletes hillfort entry with alert dialog for confirmation
      R.id.item_delete ->
      {

        val confirmAlert = AlertDialog.Builder(this@HillfortActivity)
        confirmAlert.setTitle("Delete Item")
        confirmAlert.setMessage("Confirm to Delete "+ hillfort.title)
        confirmAlert.setPositiveButton("YES")
        {
          dialog, which ->
          app.users.deleteHillfort(hillfort.copy(), app.getEmail()) //deletes hillfort in list - identified in json by users email
          finish()
        }
        confirmAlert.setNegativeButton("NO") {dialog, which -> }
        confirmAlert.create().show()
      }


    }
    return super.onOptionsItemSelected(item)
  }//End onOptionsItemSelected


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          hillfort.image = data.getData().toString()
          hillfortImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_hillfort_image)
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          val location = data.extras.getParcelable<Location>("location")
          hillfort.location.lat = location.lat
          hillfort.location.lng = location.lng
          hillfort.location.zoom = location.zoom
        }
//
      }
    }
  }//End onActivityResult
}

