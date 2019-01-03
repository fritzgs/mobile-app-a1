package assigment1.fritz_20071968.models

interface UserStore
{
  fun findUser(userEmail: String) : User
  fun userExist(userEmail: String, password : String) : Boolean
  fun findAll(userEmail: String) : MutableList<HillfortModel>
  fun createUser(user : User)
  fun updateUser(name : String, newEmail : String, password: String, oldEmail : String)
  fun deleteUser(user: User)
  fun createHillfort(hillfort : HillfortModel, userEmail : String)
  fun updateHillfort(hillfort : HillfortModel, userEmail: String)
  fun deleteHillfort(hillfort: HillfortModel, userEmail: String)
  fun findFav(userEmail : String) : MutableList<HillfortModel>
}