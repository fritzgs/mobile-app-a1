package assigment1.fritz_20071968.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.main.MainApp
import assigment1.fritz_20071968.models.HillfortModel
import assigment1.fritz_20071968.models.User
import kotlinx.android.synthetic.main.signup.*

class SignUpActivity : AppCompatActivity() {


  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.signup)
    app = application as MainApp
  }


  fun onClick(view: View)
  {
    if(view.id == R.id.signup) {
      val name: String = newName.text.toString()
      val email: String  = newEmail.text.toString()
      val pass: String = newPass.text.toString()
      if (name.equals("") || email.equals("") || pass.equals(""))
      {
        Toast.makeText(this@SignUpActivity, "Missing Entries", Toast.LENGTH_SHORT).show()
      }
      else{
        app.users.createUser(User(name, email, pass,  ArrayList<HillfortModel>()))
        app.setEmail(email)
        val loginIntent = Intent(this@SignUpActivity, HillfortListActivity::class.java)
        startActivity(loginIntent)
        finish()
      }
    }
    else if(view.id == R.id.signup_cancel)
    {
      startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
      finish()
    }
  }
}
