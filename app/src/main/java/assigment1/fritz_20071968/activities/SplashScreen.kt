/**
 * @author Fritz Gerald Santos
 * From Let's Finish this APP - KotLin and Swift Tutorials
 * "Build a Splash Screen - Android Kotlin Tutorial" @ https://www.youtube.com/watch?v=nMj3hugs6Lk
 */

package assigment1.fritz_20071968.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import assigment1.fritz_20071968.R
import java.util.*

class SplashScreen : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.splash)
    Timer().schedule( object : TimerTask(){
      override fun run(){
        val itent= Intent(this@SplashScreen, LoginActivity::class.java)
        startActivity(itent)
        finish()
      }
    } , 1700L)

  }
}
