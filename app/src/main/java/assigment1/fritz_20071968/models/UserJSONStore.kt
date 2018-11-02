package assigment1.fritz_20071968.models

import android.content.Context
import assigment1.fritz_20071968.helpers.exists
import assigment1.fritz_20071968.helpers.read
import assigment1.fritz_20071968.helpers.write
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

val FILE = "hillfortsdata.json"
val builder = GsonBuilder().setPrettyPrinting().create()
val ltype = object : TypeToken<ArrayList<User>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class UserJSONStore : UserStore
{
  override fun findUser(userEmail: String): User {
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) }
    if(foundUser != null)
    {
      return User(foundUser.name, foundUser.email, foundUser.password, foundUser.hillfortList)
    }
    return null!!
  }

  val context: Context
  var users = mutableListOf<User>()

  override fun userExist(userEmail : String, pass : String) : Boolean
  {
    var result : Boolean = false
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) }
    if(foundUser != null && foundUser.password.equals(pass))
    {
      result = true
    }
    return result
  }

  override fun deleteHillfort(hillfort: HillfortModel, userEmail: String) {
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) }
    var foundHillfort : HillfortModel? = foundUser!!.hillfortList.find { h -> h.id == hillfort.id }
    if(foundHillfort != null)
    {
      foundUser!!.hillfortList.remove(foundHillfort)
    }
  }

  override fun updateHillfort(hillfort: HillfortModel, userEmail: String) {
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) }
    var foundHillfort : HillfortModel? = foundUser!!.hillfortList.find { h -> h.id == hillfort.id }
    if(foundHillfort != null)
    {
      foundHillfort.title = hillfort.title
      foundHillfort.description = hillfort.description
      foundHillfort.image = hillfort.image
      foundHillfort.lat = hillfort.lat
      foundHillfort.lng = hillfort.lng
      foundHillfort.zoom = hillfort.zoom
      foundHillfort.visited = hillfort.visited
      serialize()
    }
  }

  override fun createHillfort(hillfort: HillfortModel, userEmail: String) {
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) }
    if(foundUser != null) {
      hillfort.id = generateRandomId()
      foundUser.hillfortList.add(hillfort)
      serialize()
    }
  }

  constructor (context: Context) {
    this.context = context
    if (exists(context, FILE)) {
      deserialize()
    }
  }


  override fun deleteUser(user: User) {
    var foundUser : User? = users.find { h -> h.email == user.email }
    if(foundUser != null)
    {
      users.remove(user)
    }
  }

  override fun updateUser(user: User) {
    var foundUser : User? = users.find { h -> h.email == user.email }
    if(foundUser != null)
    {
      foundUser.name = user.name
      foundUser.email = user.email
      foundUser.password = user.password
      serialize()
    }
  }

  override fun createUser(user: User) {
    users.add(user)
    serialize()
  }

  override fun findAll(userEmail: String): MutableList<HillfortModel> {
    var foundUser : User? = users.find { h -> h.email.equals(userEmail) }

    return foundUser!!.hillfortList
  }

  private fun serialize() {
    val jsonString = builder.toJson(users, ltype)
    write(context, FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, FILE)
    users = Gson().fromJson(jsonString, ltype)
  }

}