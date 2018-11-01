package assigment1.fritz_20071968.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import assigment1.fritz_20071968.R
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity(){
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.login)
  }

  fun onClick(view : View)
  {
    if(view.id == R.id.loginBtn)
    {
      if(emailText.text.toString().equals("test@test.com") && passText.text.toString().equals("password"))
      {
        print("Success")
        Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_SHORT).show()
        val loginIntent = Intent(this@LoginActivity, HillfortListActivity::class.java)
        startActivity(loginIntent)

      }
      else
      {
        print("failed")
        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
      }
    }

    else if(view.id == R.id.signupBtn)
    {
      val signUpIntent = Intent(this@LoginActivity, SignUpActivity::class.java)
      startActivity(signUpIntent)
    }
  }

}