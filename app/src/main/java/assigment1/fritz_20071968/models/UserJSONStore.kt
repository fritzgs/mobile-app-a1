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
      return User(foundUser.name, foundUser.email, foundUser.hillfortList)
    }
    return null!!
  }

  //no longer needed due to firebase authentication
//  /**
//   * checks if user exist
//   * @param String email
//   * @param String password
//   * @return boolean
//   */
//  override fun userExist(userEmail : String, pass : String) : Boolean
//  {
//    var result : Boolean = false
//    var foundUser : User? = users.find { u -> u.email.equals(userEmail) } //looks for user by email
//    if(foundUser != null) //if not null and password is right
//    {
//      result = true
//    }
//    return result
//  }

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
    serialize()
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
      foundHillfort.location = hillfort.location
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
      serialize()
    }
  }

  /**
   * updates users data
   */
  override fun updateUser(name: String, newEmail : String, oldEmail : String) {
    var foundUser : User? = users.find { h -> h.email.equals(oldEmail)}
    if(foundUser != null)
    {
      foundUser.name = name
      foundUser.email = newEmail
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

  /**
   * return hillfort list of all hillforts that are favourited
   */
  override fun findFav(userEmail: String): MutableList<HillfortModel> {
    var foundUser : User? = users.find { h -> h.email.equals(userEmail)}

    var favList : MutableList<HillfortModel> = mutableListOf()
    for(h : HillfortModel in foundUser!!.hillfortList)
    {
      if(h.favourite==true)
      {
        favList.add(h)
      }
    }

    return favList
  }


  /**
   * return hillfort list that contain the query string in its title
   */
  override fun findQuery(query : String, userEmail: String): MutableList<HillfortModel>
  {
    var foundUser : User? = users.find { h -> h.email.equals(userEmail)}

    var res : MutableList<HillfortModel> = mutableListOf()

    for(h : HillfortModel in foundUser!!.hillfortList)
    {
      if(h.title.toLowerCase().contains(query.toLowerCase()))
      {
        res.add(h)
      }
    }
    return res
  }


}