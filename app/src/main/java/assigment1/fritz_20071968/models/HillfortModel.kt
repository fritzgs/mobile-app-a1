/**
 * Data models usig Parcelization
 */

package assigment1.fritz_20071968.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class HillfortModel(var id: Long = 0,
                         var title: String = "",
                         var description: String = "",
                         var image: String = "",
                         var location : Location = Location(0.0, 0.0, 0f),
                         var visited : Boolean = false,
                         var favourite : Boolean = false,
                         var rating : Float = 0f,
                         var date : Date? = null) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

@Parcelize
data class User(var name : String? = "",
                var email : String? ="",
                var password : String? = "",
                var hillfortList : MutableList<HillfortModel>) : Parcelable