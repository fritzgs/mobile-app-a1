package assigment1.fritz_20071968.main

import android.app.Application
import assigment1.fritz_20071968.models.HillfortJSONStore
import assigment1.fritz_20071968.models.HillfortModel
import assigment1.fritz_20071968.models.HillfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {
  lateinit var hillforts: HillfortStore
  var userEmail : String = ""

  override fun onCreate()
  {

    super.onCreate()
    hillforts = HillfortJSONStore(applicationContext)
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