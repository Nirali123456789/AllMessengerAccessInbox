package com.myapps.allsocialaccess.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.interfaces.AdapterCallback
import com.myapps.allsocialaccess.models.SocialMediaApp

class SocialMediaAppAdapter(
    private var apps: List<SocialMediaApp>,
    listner: AdapterCallback,
    var fromhome: Boolean = false
) :
    RecyclerView.Adapter<SocialMediaAppAdapter.ViewHolder>() {
    private val selectedItems = mutableListOf<SocialMediaApp>()
    private val removedItems = mutableListOf<SocialMediaApp>()
    private var storeList: List<SocialMediaApp> = arrayListOf()
    var callback: AdapterCallback? = listner

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appIcon: ImageView = itemView.findViewById(R.id.appIcon)
        val appName: TextView = itemView.findViewById(R.id.appName)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.social_media_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        holder.appIcon.setImageResource(app.iconUrl)
        holder.appName.text = app.name
        holder.checkBox.isVisible = !fromhome
        holder.checkBox.isChecked = app.selected || storeList.contains(app)
        Log.d("setStoreData", "setStoreData: " + storeList.contains(app))

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            app.selected = isChecked // Update the selected state of the app

            if (isChecked) {
                selectedItems.add(app) // Add the item to the selectedItems list
            } else {
                removedItems.add(app)
                selectedItems.remove(app) // Remove the item from the selectedItems list
            }
        }
        holder.itemView.setOnClickListener { callback?.onItemClicked(app) }
    }

    fun updateData(newData: List<SocialMediaApp>, data: List<SocialMediaApp>) {

        apps = newData
        storeList = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    fun getSelectedItems(): List<SocialMediaApp> {
        return selectedItems
    }

    fun getRemovedItems(): List<SocialMediaApp> {
        return removedItems
    }
}
