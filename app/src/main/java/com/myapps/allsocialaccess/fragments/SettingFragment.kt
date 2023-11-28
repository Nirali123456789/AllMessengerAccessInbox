package com.myapps.allsocialaccess.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.murgupluoglu.flagkit.FlagKit
import com.myapps.allsocialaccess.MainApplication

import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.adapters.LanguageAdapter
import com.myapps.allsocialaccess.databinding.FragmentSettingLayoutBinding
import com.myapps.allsocialaccess.interfaces.AdapterOnClick
import com.myapps.allsocialaccess.models.Language
import com.myapps.allsocialaccess.ui.MainActivity
import com.myapps.allsocialaccess.utils.Constants
import com.myapps.allsocialaccess.utils.Constants.Companion.contactus
import com.myapps.allsocialaccess.utils.Constants.Companion.privacyPolicy
import com.myapps.allsocialaccess.utils.Constants.Companion.setSafeOnClickListener
import com.myapps.allsocialaccess.utils.themes.Companion.setTheme
import com.myapps.allsocialaccess.utils.themes.Companion.themeDialog

class SettingFragment() : Fragment(), AdapterOnClick {
    private var _binding: FragmentSettingLayoutBinding? = null
    private val binding get() = _binding
    private lateinit var languageList: ArrayList<Language>
    private var language: String? = null
    private lateinit var bottomSheetDialog: BottomSheetDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingLayoutBinding.inflate(inflater, container, false)
        setView()
        return binding!!.root
    }

    private fun setView() {
        languageList = arrayListOf()
        binding!!.selectedTheme.text = activity!!.setTheme()

        if (MainApplication.prefs1!!.theme == "dark") {
            binding!!.mode.setImageResource(R.drawable.ic_mode_night)
        } else {
            binding!!.mode.setImageResource(R.drawable.ic_light)
        }


        binding!!.language.setSafeOnClickListener {
            activity?.let { it1 -> showBottomSheetDialog(it1) }
        }
        binding!!.appTheme.setSafeOnClickListener {
            activity!!.themeDialog()
        }
        binding!!.privacyLayout.setSafeOnClickListener {
            activity!!.privacyPolicy()
        }
        binding!!.rateLayout.setSafeOnClickListener {
            activity?.let { it1 -> Constants.rateUs(it1) }
        }
        binding!!.moreLayout.setOnClickListener {

        }
        binding!!.shareLayout.setSafeOnClickListener {
            activity?.let { it1 -> Constants.shareUs(it1) }
        }
        binding!!.contactLayout.setSafeOnClickListener {
            activity!!.contactus()
        }

    }


    private fun showBottomSheetDialog(activity: Activity) {
        var bottomSheetDialog = BottomSheetDialog(activity!!)
        bottomSheetDialog.setContentView(R.layout.layout_language)

        setAdapter(
            bottomSheetDialog.findViewById<RecyclerView>(R.id.languageRecyclerview)!!,
            activity as MainActivity
        )
        bottomSheetDialog.findViewById<ImageView>(R.id.close)
            ?.setOnClickListener { bottomSheetDialog.dismiss() }
        bottomSheetDialog.show()
    }


    private fun setAdapter(recyclerView: RecyclerView, activity: MainActivity) {

        languageListItems(activity)

        val languageAdapter =
            LanguageAdapter(this, languageList, activity, BottomSheetDialog(activity))

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = languageAdapter

    }

    private fun languageListItems(activity: Activity) {
        val resourceId = FlagKit.getResId(activity!!, "us")
        languageList.add(Language(resourceId, "USA"))
        languageList.add(Language(FlagKit.getResId(activity!!, "in"), "Hindi"))
        languageList.add(Language(FlagKit.getResId(activity!!, "ar"), "Arabic"))
        languageList.add(Language(FlagKit.getResId(activity!!, "de"), "German"))
        languageList.add(Language(FlagKit.getResId(activity!!, "ru"), "Russian"))
        languageList.add(Language(FlagKit.getResId(activity!!, "pt"), "Portuguese"))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(item: String) {
        // MainApplication.prefs1!!.positionTone = position
    }

    override fun onClickLanguage(
        item: String,
        bottomSheetDialog: BottomSheetDialog,
        position: Int
    ) {
        language = item
        Log.d("TAG", "onClickLanguage: " + position)
        Constants.setLocal(activity!!, Constants.languageCodes[position])
        MainApplication.prefs1!!.languageCode = Constants.languageCodes[position]
        MainApplication.prefs1!!.position = position
        bottomSheetDialog.dismiss()
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context!!.startActivity(intent)
    }

    override fun onStop() {
        super.onStop()

    }

}


