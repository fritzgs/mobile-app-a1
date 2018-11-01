package assigment1.fritz_20071968.models

class User
{
  var name: String? = null
  var email: String? = null
  var password: String? = null
  var list : ArrayList<HillfortModel>? = null


  constructor() : super() {}

  constructor(name: String, email: String, password: String, list: ArrayList<HillfortModel>) : super() {
    this.name = name
    this.email = email
    this.password = password
    this.list = list
  }

}