package com.example.mytraveljournal.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mytraveljournal.R
import com.example.mytraveljournal.data.Trip

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var trips = emptyList<Trip>()

    class MyViewHolder(tripView: View) : RecyclerView.ViewHolder(tripView) {

        val tripLayout: ConstraintLayout = itemView.findViewById(R.id.layout_trip)
        val tripLocationTextView: TextView = itemView.findViewById(R.id.text_view_location)
        val tripDateTextView: TextView = itemView.findViewById(R.id.text_view_date)
        val editButton: Button = itemView.findViewById(R.id.button_edit)
        val tripImageView: ImageView = itemView.findViewById(R.id.image_view_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.trip_view, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTrip = trips[position]

        holder.tripLocationTextView.text = currentTrip.location

        val dateString = "${currentTrip.day}/${currentTrip.month}/${currentTrip.year}"
        holder.tripDateTextView.text = dateString

        holder.tripImageView.setImageBitmap(currentTrip.picture)

        holder.tripLayout.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavDetails(currentTrip)
            holder.itemView.findNavController().navigate(action)
        }

        holder.editButton.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavEdit(currentTrip)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(tripList: List<Trip>) {
        this.trips = tripList
        notifyDataSetChanged()
    }
}