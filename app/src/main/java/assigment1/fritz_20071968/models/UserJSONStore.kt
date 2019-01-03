/**
 * @author Fritz Gerald Santos
 * REST API for Hillfort
 */

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
  val context: Context
  var users = mutableListOf<User>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, FILE)) {
      deserialize()
    }
  }


  /**
   * Finds user by email
   * @return User data
   */
  override fun findUser(userEmail: String): User {
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) } //searches user based on email
    if(foundUser != null) //if user is found and not null
    {
      // returns user object
      return User(foundUser.name, foundUser.email, foundUser.password, foundUser.hillfortList)
    }
    return null!!
  }

  /**
   * checks if user exist
   * @param String email
   * @param String password
   * @return boolean
   */
  override fun userExist(userEmail : String, pass : String) : Boolean
  {
    var result : Boolean = false
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) } //looks for user by email
    if(foundUser != null && foundUser.password.equals(pass)) //if not null and password is right
    {
      result = true
    }
    return result
  }

  /**
   * Deletes hillfort based on id
   * @param HillfortModel hillfort
   * @param String user email
   */
  override fun deleteHillfort(hillfort: HillfortModel, userEmail: String) {
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) } //finds user
    var foundHillfort : HillfortModel? = foundUser!!.hillfortList.find { h -> h.id == hillfort.id } //fids hillfort based on user
    if(foundHillfort != null)
    {
      foundUser!!.hillfortList.remove(foundHillfort)
    }
  }

  /**
   * Update hillfort based on id
   * @param HillfortModel hillfort
   * @param String user email
   */
  override fun updateHillfort(hillfort: HillfortModel, userEmail: String) {
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) } //finds user
    var foundHillfort : HillfortModel? = foundUser!!.hillfortList.find { h -> h.id == hillfort.id }// finds hillfort
    if(foundHillfort != null)
    {
      //make changes
      foundHillfort.title = hillfort.title
      foundHillfort.description = hillfort.description
      foundHillfort.image = hillfort.image
      foundHillfort.lat = hillfort.lat
      foundHillfort.lng = hillfort.lng
      foundHillfort.zoom = hillfort.zoom
      foundHillfort.visited = hillfort.visited
      foundHillfort.date = hillfort.date
      foundHillfort.favourite = hillfort.favourite
      foundHillfort.rating = hillfort.rating
      serialize()
    }
  }

  /**
   * add new hillfort to list.
   */
  override fun createHillfort(hillfort: HillfortModel, userEmail: String) {
    var foundUser : User? = users.find { u -> u.email.equals(userEmail) }
    if(foundUser != null) {
      hillfort.id = generateRandomId()
      foundUser.hillfortList.add(hillfort)
      serialize()
    }
  }

  /**
   * deletes user
   */
  override fun deleteUser(user: User) {
    var foundUser : User? = users.find { h -> h.email == user.email }
    if(foundUser != null)
    {
      users.remove(user)
    }
  }

  /**
   * updates users data
   */
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

  /**
   * Creates user
   */
  override fun createUser(user: User) {
    users.add(user)
    serialize()
  }

  /**
   * gets user's hillfort list
   * @return List of HillfortModel
   */
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