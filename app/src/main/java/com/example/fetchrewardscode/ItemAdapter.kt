package com.example.fetchrewardscode

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val itemsMap: Map<Int, List<String>>) :
    RecyclerView.Adapter<ItemAdapter.CustomViewHolder>() {

    // ViewHolder for holding views in each RecyclerView item
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listIdTextView: TextView = itemView.findViewById(R.id.listIdTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // Use !! to assert that parent is not null
        val itemView =
            LayoutInflater.from(parent!!.context).inflate(R.layout.item_layout, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // Get sorted listIds and current names for the current position
        val listIds = itemsMap.keys.sorted() // Sort listIds
        val currentListId = listIds[position]
        val currentNames = itemsMap[currentListId]

        // Set the List ID text
        holder.listIdTextView.text = "List ID: $currentListId"

        // Prepare the names text with proper indentation
        val namesText = "\t\t\t" + currentNames?.joinToString("\n\t\t\t") + "\n"?: ""
        holder.nameTextView.text = namesText
    }

    override fun getItemCount(): Int {
        // Return the number of items in the RecyclerView
        return itemsMap.size
    }
}