/**
 * @author Fritz Gerald Santos
 *
 * Settings Activity - allows uer to edit name, email and change password -
 * it shows the total number of hillforts visited
 * nd allows deletion of the user
 */

package assigment1.fritz_20071968.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.main.MainApp
import assigment1.fritz_20071968.models.HillfortModel
import assigment1.fritz_20071968.models.User
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.startActivityForResult

class SettingsActivity : AppCompatActivity()
{
  lateinit var app : MainApp

  /**
   * sets the text of the components as name, email and password hint according to user data
   */
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.settings)
    app = application as MainApp

    toolbarSettings.title = title
    setSupportActionBar(toolbarSettings)

    name_settings.setText(app.users.findUser(app.getEmail()).name)
    email_settings.setText(app.users.findUser(app.getEmail()).email)
    pass_settings.setHint("PASSWORD")
    //Couts the total visited
    var count : Int = 0
    for(i in app.users.findAll(app.getEmail()))
    {
      if(i.visited==true)
      {
        count += 1
      }
    }
    total_visited.setText(String.format(getString(R.string.total_visited) , count, app.users.findUser(app.getEmail()).hillfortList.size))
  }

  //Event handler
  fun onClick(view: View)
  {
    if(view.id == R.id.save_settings) //if save
    {
      if(!name_settings.text.isNullOrBlank() and !email_settings.text.isNullOrBlank() and !pass_settings.text.isNullOrBlank()) //as long as non of the text fields are empty
      {
        app.users.updateUser(User(name_settings.text.toString(), email_settings.text.toString(), pass_settings.text.toString(), app.users.findAll(app.getEmail()))) //update the user
        app.setEmail(email_settings.text.toString()) //reset the email string in mainapp
        startActivity(Intent(this@SettingsActivity, HillfortListActivity::class.java))
        finish()
      }
      else
      {
        Toast.makeText(this@SettingsActivity, "Missing Entries", Toast.LENGTH_SHORT).show()
      }
    }
    //if delete user - has alert dialog
   if(view.id==R.id.delete_user)
   {
     val confirmAlert = AlertDialog.Builder(this@SettingsActivity)
     confirmAlert.setTitle("Delete")
     confirmAlert.setMessage("Are you sure you want to delete this user?")
     confirmAlert.setPositiveButton("YES")
     {
       //deletes the user from the json array
       dialog, which ->
       Toast.makeText(this@SettingsActivity, "Deleted User", Toast.LENGTH_SHORT).show()
       app.users.deleteUser(app.users.findUser(app.getEmail()))
       app.setEmail("")
       startActivityForResult<LoginActivity>(0)
       finish()
     }
     confirmAlert.setNegativeButton("NO") {dialog, which -> } //No does nothing
     confirmAlert.create().show()
   }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_settings, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.settings_logout -> //logs out the user
      {
        val confirmAlert = AlertDialog.Builder(this@SettingsActivity)
        confirmAlert.setTitle("Logout")
        confirmAlert.setPositiveButton("YES")
        {
          dialog, which ->
          app.setEmail("") //resets the email in mainapp
          startActivityForResult<LoginActivity>(0) //goes back to login activity
          finish()
        }
        confirmAlert.setNegativeButton("NO") {dialog, which -> }
        confirmAlert.create().show()

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