/***
 * @author Fritz Gerald Santos - adapted from the Mobile App Development labs by Dr. Eamonn de Leaster
 *
 * This acittivy is for adding/editing hillfort entries
 */

package assigment1.fritz_20071968.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
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


class HillfortActivity : AppCompatActivity(), AnkoLogger {

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
      description.setText(hillfort.description)
      chooseImage.setText("Change Image")
      btnAdd.setText(R.string.save)

      //Image button - if there's a picture already, change the button text
      hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
      if(hillfort.image != null) {
        chooseImage.setText(R.string.change_hillfort_image)
      }

      //location button - set the location to the data in the hillfort
      hillfortLocation.setOnClickListener {
        val location = Location(52.245696, -7.139102, 15f)
        if (hillfort.zoom != 0f) {
          location.lat =  hillfort.lat
          location.lng = hillfort.lng
          location.zoom = hillfort.zoom
        }
        startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST) //start google maps activity
      }

      // @author Fritz Gerald Santos - as described in edit mode.
      if(hillfort.visited == true)
      {
        visited_checkbox.setChecked(true)
        visited_checkbox.setText(String.format(getString(R.string.checkbox), hillfort.date))
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

    hillfortLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (hillfort.zoom != 0f) {
        location.lat =  hillfort.lat
        location.lng = hillfort.lng
        location.zoom = hillfort.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    btnAdd.setOnClickListener()
    {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      } else {
        if (edit) {
          app.users.updateHillfort(hillfort.copy(), app.getEmail())
        } else {
          app.users.createHillfort(hillfort.copy(), app.getEmail())
        }
        info("Add Button Pressed:  $hillfortTitle")
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
    }//end btnAdd



    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }//End chooseImage
  }//ENF onCreate

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_add, menu)
    return super.onCreateOptionsMenu(menu)
  }//END onCreateOptionsMenu

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {

      R.id.item_cancel -> {
        // /TODO CHANGE LOGIN TO LIST
        startActivityForResult<HillfortListActivity>(0)
        finish()
      }

      R.id.item_delete ->
      {
        if(intent.hasExtra("hillfort_edit"))
        {
          val confirmAlert = AlertDialog.Builder(this@HillfortActivity)
          confirmAlert.setTitle("Delete Item")
          confirmAlert.setMessage("Confirm to Delete "+ hillfort.title)
          confirmAlert.setPositiveButton("YES")
          {
            dialog, which ->
            app.users.deleteHillfort(hillfort.copy(), app.getEmail())
            startActivityForResult<HillfortListActivity>(0)
            finish()
          }
          confirmAlert.setNegativeButton("NO") {dialog, which -> }
          confirmAlert.create().show()
        }
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
          hillfort.lat = location.lat
          hillfort.lng = location.lng
          hillfort.zoom = location.zoom
        }
      }
    }
  }//End onActivityResult
}

