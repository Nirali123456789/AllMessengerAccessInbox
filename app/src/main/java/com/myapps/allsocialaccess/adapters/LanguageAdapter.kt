/**Created By Nirali Pandya
 * Date :2023-07-26
 * Time :12:21 p.m.
 * Project Name :Calculator
 *
 */
package com.myapps.allsocialaccess.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.myapps.allsocialaccess.MainApplication
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.databinding.ListItemBinding
import com.myapps.allsocialaccess.interfaces.AdapterOnClick
import com.myapps.allsocialaccess.models.Language

class LanguageAdapter(
    private val adapterOnClick: AdapterOnClick,
    private val languageList: ArrayList<Language>,
    private val activity: AppCompatActivity,
    private val bottomSheetDialog: BottomSheetDialog?
) :
    RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    var languagelist = languageList
    var languageActivity = activity

    companion object {
        var selectedItemPosition: Int = -1
    }

    class LanguageViewHolder(
        private val binding: ListItemBinding,
        private val adapter: LanguageAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        var container: RelativeLayout? = null
        var name: TextView? = null
        fun bind(
            item: Language,
            languageActivity: AppCompatActivity,
            adapterOnClick: AdapterOnClick,
            bottomSheetDialog: BottomSheetDialog
        ) {
            container = binding.container
            name = binding.name

            binding.apply {
                // Assuming you have a 'setStatus' function in the binding class to set the status
                // Assuming you have an ImageView and a TextView in the list_item.xml layout with IDs 'flagImage' and 'languageName'
                flagImage.setImageResource(item.flag)
                name.text = item.language


                binding.name.setOnClickListener {
                    selectedItemPosition = adapterPosition
                    adapter.notifyDataSetChanged()
                    adapterOnClick.onClickLanguage(
                        item.language,
                        bottomSheetDialog,
                        selectedItemPosition
                    )


                    // container.setBackgroundResource(R.drawable.btn_bg_select)
                }


            }
            setSelected()
        }

        fun setSelected() {
            // selectedItemPosition = pos
            //adapter.notifyDataSetChanged()
        }
    }

    // ...

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        val currentLanguage = languagelist[position]
        holder.bind(currentLanguage, languageActivity, adapterOnClick, bottomSheetDialog!!)
        if (selectedItemPosition == holder.adapterPosition) {
            holder.container!!.setBackgroundResource(R.drawable.bg_round)
            holder.name!!.setTextColor(Color.WHITE)
        } else {
            holder.container!!.setBackgroundResource(0)
            holder.name!!.setTextColor(activity.getColor(R.color.textcolor))
        }

        if (MainApplication.prefs1!!.position!! == position)
            holder.container!!.setBackgroundResource(R.drawable.bg_round)
            holder.name!!.setTextColor(activity.getColor(R.color.textcolor))
    }

    override fun getItemCount(): Int {
        // You should return the actual number of items in the list
        return languagelist.size
    }


}










