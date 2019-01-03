/**
 * Adapted from the Mobile App Development labs by Eamonn Deleastar
 */

package assigment1.fritz_20071968.activities

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assigment1.fritz_20071968.R
import assigment1.fritz_20071968.helpers.readImageFromPath
import assigment1.fritz_20071968.models.HillfortModel
import kotlinx.android.synthetic.main.card_hillfort.view.*


interface HillfortListener {
  fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter

  constructor(private var hillforts: List<HillfortModel>,
                                   private val listener: HillfortListener) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(hillfort: HillfortModel, listener: HillfortListener) {
      itemView.cardtitle.text = hillfort.title
      itemView.carddescription.text = hillfort.description
      itemView.rating.text = "Rating: " + hillfort.rating.toString()

      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image))

      if(hillfort.favourite == true)
      {
        itemView.fav.setImageResource(android.R.drawable.btn_star_big_on)
      }
      else
      {
        itemView.fav.setImageResource(android.R.drawable.btn_star_big_off)
      }

      //Changes the colour of the item depending on the visited status.
      if(hillfort.visited==true)
      {
        itemView.getBackground().setColorFilter(Color.parseColor("#009933"), PorterDuff.Mode.DARKEN)
      }
      else
      {
        itemView.getBackground().setColorFilter(Color.parseColor("#0080ff"), PorterDuff.Mode.DARKEN)
      }
      itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
    }
  }
}