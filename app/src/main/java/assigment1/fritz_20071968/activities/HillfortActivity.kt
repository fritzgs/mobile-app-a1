package assigment1.fritz_20071968.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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


class HillfortActivity : AppCompatActivity(), AnkoLogger {

  var hillfort = HillfortModel()
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Hillfort Started")

    app = application as MainApp
    var edit = false

    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      hillfortTitle.setText(hillfort.title)
      description.setText(hillfort.description)
      chooseImage.setText("Change Image")
      btnAdd.setText(R.string.save)

      hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
      if(hillfort.image != null) {
        chooseImage.setText(R.string.change_hillfort_image)
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
    }

    btnAdd.setOnClickListener()
    {
      hillfort.title = hillfortTitle.text.toString()
      hillfort.description = description.text.toString()
      if (hillfort.title.isEmpty()) {
        toast(R.string.enter_hillfort_title)
      } else {
        if (edit) {
          app.hillforts.update(hillfort.copy())
        } else {
          app.hillforts.create(hillfort.copy())
        }
      }
      info("Add Button Pressed:  $hillfortTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
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
        //TODO CHANGE LOGIN TO LIST
        startActivityForResult<HillfortListActivity>(0)
        finish()
      }
      R.id.item_delete ->
      {
        //TODO delete from JSON
        startActivityForResult<HillfortListActivity>(0)
        finish()
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

