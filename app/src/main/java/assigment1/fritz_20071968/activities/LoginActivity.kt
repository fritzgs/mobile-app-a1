/**
 * @author Fritz Gerald Santos
 *
 * Login Activity -Login and SignUp options
 */

package assigment1.fritz_20071968.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.main.MainApp
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity(){
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login)
    app = application as MainApp

  }

  /**
   * Runs when activity starts.
   */
  fun onClick(view : View)
  {
    //When the login button is clicked
    if(view.id == R.id.loginBtn) {
      if(app.users.userExist(emailText.text.toString(), passText.text.toString())) //checks if the crendentials used matches any in the json file
      {
        app.setEmail(emailText.text.toString()) //set the email in the mainapp so that it can be used in the appropriate activities,
        Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_SHORT).show()
        val loginIntent = Intent(this@LoginActivity, HillfortListActivity::class.java) //start the HillfortListActivity
        startActivity(loginIntent)
        finish()
      } else {
        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show() //if credentials are wrong
      }
    }

    //sign up button opens the Sign up activity
    else if(view.id == R.id.signupBtn)
    {
      val signUpIntent = Intent(this@LoginActivity, SignUpActivity::class.java)
      startActivity(signUpIntent)
      finish()
    }

  }
}