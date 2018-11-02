//package assigment1.fritz_20071968.models
//
//import android.content.Context
//import android.widget.Toast
//import assigment1.fritz_20071968.activities.HillfortActivity
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import com.google.gson.reflect.TypeToken
//import org.jetbrains.anko.AnkoLogger
//import assigment1.fritz_20071968.helpers.*
//import assigment1.fritz_20071968.main.MainApp
//import java.util.*
//
//val JSON_FILE = "hillfortsdata.json"
//val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
//val listType = object : TypeToken<java.util.ArrayList<HillfortModel>>() {}.type
//
//fun generateRandomId(): Long {
//  return Random().nextLong()
//}
//
//
//
//class HillfortJSONStore : HillfortStore, AnkoLogger {
//
////  var app : MainApp = MainApp()
//  val context: Context
//  var hillforts = mutableListOf<HillfortModel>()
////  var user = mutableListOf<User>()
//
//  constructor (context: Context) {
//    this.context = context
//    if (exists(context, JSON_FILE)) {
//      deserialize()
//    }
//  }
//
//  override fun findAll(): MutableList<HillfortModel> {
//    return hillforts
//  }
//
//  override fun create(hillfort: HillfortModel) {
////    var foundUser : User? = user.find { h -> h.email== app.getEmail() }
////    if(foundUser != null)
////    {
//      hillfort.id = generateRandomId()
//      hillforts.add(hillfort)
//      serialize()
////    }
//
//  }
//
//  override fun delete(hillfort: HillfortModel)
//  {
//    var foundHillfort : HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
//    if(foundHillfort != null)
//    {
//      hillforts.remove(hillfort)
//    }
//
//  }
//
//
//  override fun update(hillfort: HillfortModel) {
//    var foundHillfort : HillfortModel? = hillforts.find { h -> h.id == hillfort.id }
//    if(foundHillfort != null)
//    {
//      foundHillfort.title = hillfort.title
//      foundHillfort.description = hillfort.description
//      foundHillfort.image = hillfort.image
//      foundHillfort.lat = hillfort.lat
//      foundHillfort.lng = hillfort.lng
//      foundHillfort.zoom = hillfort.zoom
//      foundHillfort.visited = hillfort.visited
//      serialize()
//    }
//  }
//
//  private fun serialize() {
//    val jsonString = gsonBuilder.toJson(hillforts, listType)
//    write(context, JSON_FILE, jsonString)
//  }
//
//  private fun deserialize() {
//    val jsonString = read(context, JSON_FILE)
//    hillforts = Gson().fromJson(jsonString, listType)
//  }
//}