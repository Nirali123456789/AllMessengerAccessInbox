package com.myapps.allsocialaccess.fragments

import com.myapps.allsocialaccess.adapters.SocialMediaAppAdapter
import com.myapps.allsocialaccess.models.SocialMediaApp
import com.myapps.allsocialaccess.ui.WebViewActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapps.allsocialaccess.MainApplication.Companion.database
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.databinding.FragmentHomeBinding
import com.myapps.allsocialaccess.databinding.FragmentWebviewBinding
import com.myapps.allsocialaccess.interfaces.AdapterCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(), AdapterCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: SocialMediaAppAdapter
    private lateinit var recyclerView: RecyclerView
    private var shouldReplaceFragment: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        recyclerView = binding!!.homeRecyclerView
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SocialMediaAppAdapter(emptyList(), this, true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun updateData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val selectedApps = getSelectedAppsFromDatabase()
            try {
                Log.d("TAG", "updateData: ${selectedApps.size}")
                if (selectedApps.size==0) {
                    visibility(true)

                } else
                    adapter.updateData(selectedApps, emptyList())
                    visibility(false)

            } catch (e: Exception) {

            }
        }
    }

    private fun visibility(isVisible: Boolean) {
        if (isVisible) {
            binding?.noDataimage!!.visibility = View.VISIBLE
            binding!!.noData.visibility = View.VISIBLE
        } else {
            binding?.noDataimage!!.visibility = View.GONE
            binding!!.noData.visibility = View.GONE
        }
    }

    private suspend fun getSelectedAppsFromDatabase(): List<SocialMediaApp> {
        return withContext(Dispatchers.IO) {
            database.selectedAppDao().getAllSelectedApps()
        }
    }

    override fun onItemClicked(data: SocialMediaApp) {
        startActivity(
            Intent(activity, WebViewActivity::class.java).putExtra(
                "package",
                data.packageName
            )
        )
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResume: calling")
        updateData()
//        if (shouldReplaceFragment) {
//            // Check if a replacement was requested
//            parentFragmentManager.popBackStack()
//            shouldReplaceFragment = false // Reset the flag
//        }
    }
}