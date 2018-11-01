package assigment1.fritz_20071968.models

class Hillfort {
  var name: String? = null
  var description: String? = null
  var image: String? = null
  var lat: Double? = null
  var lng: Double? = null


  constructor() : super() {}

  constructor(name: String, description: String, image: String, lat: Double, lng: Double)
  {
    this.name = name
    this.description = description
    this.image = image
    this.lat = lat
    this.lng = lng
  }
}