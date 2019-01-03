/**
 * @author Fritz Gerald Santos
 * Adapted from the labs
 * Activity for listing the hillfort entries
 */

package assigment1.fritz_20071968.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.main.MainApp
import assigment1.fritz_20071968.models.HillfortModel
import kotlinx.android.synthetic.main.hillfort_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class HillfortListActivity : AppCompatActivity(), HillfortListener {

  lateinit var app: MainApp


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.hillfort_list)
    app = application as MainApp

    toolbarMain.title = title
    setSupportActionBar(toolbarMain)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager

    if(intent.hasExtra("fav"))
    {
      loadFavourites()
    }
    else if(intent.hasExtra("norm"))
    {
      loadHillforts()
    }

  }

  //lists all the entries in Hillfort list
  private fun loadHillforts() {
    showHillforts(app.users.findAll(app.getEmail())) //finds hillfort list by user
  }

  //shows list in activity
  fun showHillforts (hillforts: List<HillfortModel>) {
    recyclerView.adapter = HillfortAdapter(hillforts, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  private fun loadFavourites()
  {
    showHillforts(app.users.findFav(app.getEmail()))
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_list, menu)
    if(intent.hasExtra("fav"))
    {
      menu?.getItem(0)?.setChecked(true)
      menu?.getItem(0)?.setIcon(android.R.drawable.btn_star_big_on)
    }
    else if(intent.hasExtra("norm"))
    {
      menu?.getItem(0)?.setChecked(false)
      menu?.getItem(0)?.setIcon(android.R.drawable.btn_star_big_off)
    }
    return super.onCreateOptionsMenu(menu)

  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {

      R.id.fav ->
      {
        if(item.isChecked)
        {
          item.setChecked(false)
          item.setIcon(android.R.drawable.btn_star_big_off)
          startActivity(Intent(this@HillfortListActivity, HillfortListActivity::class.java).putExtra("norm", "norm"))
          finish()
        }
        else
        {
          item.setChecked(true)
          item.setIcon(android.R.drawable.btn_star_big_on)
          startActivity(Intent(this@HillfortListActivity, HillfortListActivity::class.java).putExtra("fav", "fav"))
          finish()
        }
      }

      R.id.item_add ->
      {
        startActivityForResult<HillfortActivity>(0)
        finish()
      }
      R.id.settings ->
      {
        startActivityForResult<SettingsActivity>(0)
        finish()
      }
      R.id.logout ->
      {
        //Logout the user
        val confirmAlert = AlertDialog.Builder(this@HillfortListActivity)
        confirmAlert.setTitle("Logout")
        confirmAlert.setPositiveButton("YES")
        {
          dialog, which ->
          app.setEmail("") //sets the user email in mainapp
          startActivityForResult<LoginActivity>(0) //goes back to login activity
          finish()
        }
        confirmAlert.setNegativeButton("NO") {dialog, which -> }
        confirmAlert.create().show()

      }
    }

    return super.onOptionsItemSelected(item)
  }

  //when hillfort entry is clicked - open hillfort activity as edit mode.
  override fun onHillfortClick(hillfort: HillfortModel) {
    startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    if(intent.hasExtra("fav")) {
      loadFavourites()
    }
    else if(intent.hasExtra("norm"))
    {
      loadHillforts()
    }
    super.onActivityResult(requestCode, resultCode, data)
  }
}