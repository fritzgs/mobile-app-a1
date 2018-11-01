package assigment1.fritz_20071968.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.main.MainApp
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.startActivityForResult

class SettingsActivity : AppCompatActivity()
{
  lateinit var app : MainApp
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.settings)
    app = application as MainApp

    toolbarSettings.title = title
    setSupportActionBar(toolbarSettings)

    name_settings.setText("NAME")
    email_settings.setText("EMAIL")
    pass_settings.setText("hidden")
    total_visited.setText(String.format(getString(R.string.total_visited), 12))
  }

  fun onClick(view: View)
  {
    if(view.id == R.id.save_settings)
    {
      if(!name_settings.text.isNullOrBlank() and !email_settings.text.isNullOrBlank() and !pass_settings.text.isNullOrBlank())
      {
        //TODO UPDATE JSON
        startActivity(Intent(this@SettingsActivity, HillfortListActivity::class.java))
        finish()
      }
      else
      {
        Toast.makeText(this@SettingsActivity, "Missing Entries", Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_settings, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.settings_logout -> {
        //CHANGE/SET THE USER SOMEHOW
        startActivityForResult<LoginActivity>(0)
        finish()
      }
      R.id.settings_cancel ->
      {
        startActivityForResult<HillfortListActivity>(0)
        finish()
      }
    }
    return super.onOptionsItemSelected(item)

  }

}