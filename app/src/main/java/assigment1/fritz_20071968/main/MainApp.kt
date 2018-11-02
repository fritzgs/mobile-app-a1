package assigment1.fritz_20071968.main

import android.app.Application
import assigment1.fritz_20071968.models.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {
//  lateinit var hillforts: HillfortStore
  lateinit var users : UserStore
  var userEmail : String = ""

  override fun onCreate()
  {

    super.onCreate()
//    hillforts = HillfortJSONStore(applicationContext)
    users = UserJSONStore(applicationContext)
    info("Hillfort App Started")
  }

  fun setEmail(email : String)
  {
    userEmail = email
  }

  fun getEmail() : String
  {
    return userEmail
  }



}