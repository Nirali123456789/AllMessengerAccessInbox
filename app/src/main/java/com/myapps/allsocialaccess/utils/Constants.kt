/**Created By Nirali Pandya
 * Date :2023-08-31
 * Time :4:53 p.m.
 * Project Name :AI E-mail Assistant
 *
 */
package com.myapps.allsocialaccess.utils

import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.myapps.allsocialaccess.MainApplication
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.interfaces.SafeClickListener
import me.zhanghai.android.materialratingbar.MaterialRatingBar
import java.lang.ref.WeakReference
import java.util.Locale


class Constants {
    companion object {

        val languageCodes = arrayOf(
            "en",
            "hi", // English
            "ar", // German
            "de", // Spanish
            "ru", // French
            "pt" // Italian

        )
        val languageCodesconversion = arrayOf(
            "en",
            "हिंदी", // English
            "العربية", // German
            "española", // Spanish
            "русский", // French
            "русский" // Italian

        )

        fun setLocal(activity: Activity, languageCode: String) {
            Log.d("TAG", "onCreateView: $languageCode")
            val languageCode = languageCode // The desired language code (e.g., "es" for Spanish)
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            val context = activity.createConfigurationContext(activity.resources.configuration)
            val newResources = context.resources

            // Update your UI with the new language resources

            // Optionally, recreate the activity to apply the language change immediately
            activity.recreate()
        }


        var language = MainApplication.prefs1!!.languageCode

        var pos = when (language) {
            "en" -> 0
            "hi" -> 1
            "ar" -> 2
            "de" -> 3
            "ru" -> 4
            "pt" -> 5
            else -> 0

        }

        public fun hasActiveInternetConnection(context: Context?): Boolean {
            val connectivityManager =
                context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    ?: false
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
        }

        private fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            return activeNetworkInfo != null
        }


        private var ratingCount: Float = 0.0f

        fun rateUsDialog(activity: Activity) {
            val handler = Handler(Looper.myLooper()!!)

            // Using WeakReference to prevent potential memory leaks
            val weakActivity = WeakReference(activity)


            handler.postDelayed({
                val validActivity = weakActivity.get()
                if (validActivity != null && !validActivity.isDestroyed && !validActivity.isFinishing) {
                    // Your dialog code here
                    var rateUsDialog = Dialog(validActivity)
                    rateUsDialog.window!!.setBackgroundDrawableResource(R.color.transparent)
                    rateUsDialog.setContentView(R.layout.dialog_rate_us)
                    rateUsDialog.setCancelable(true)
                    rateUsDialog.setCanceledOnTouchOutside(true)

                    val btn_not_now: TextView = rateUsDialog.findViewById(R.id.btn_not_now)

                    val btn_rate: TextView = rateUsDialog.findViewById(R.id.btn_rate)
                    val btn_later: ImageView = rateUsDialog.findViewById(R.id.btn_later)
                    val feedback_text: EditText = rateUsDialog.findViewById(R.id.feedbacktext)
                    var image: ImageView = rateUsDialog.findViewById(R.id.imglogo)
                    var submit: TextView = rateUsDialog.findViewById(R.id.submit)
                    var textTitle: TextView = rateUsDialog.findViewById(R.id.enjoying_text)
                    var rate_text:TextView = rateUsDialog.findViewById(R.id.rate_text)

                    val emojiRatingBar: MaterialRatingBar =
                        rateUsDialog.findViewById(R.id.emoji_rating_bar)
                    emojiRatingBar.onRatingChangeListener =
                        MaterialRatingBar.OnRatingChangeListener { _, rating ->
                            // Handle the rating change here
                            ratingCount = rating
                            if (rating <= 1f) {
                                image.setImageResource(R.drawable.first)
                            } else if (rating <= 2f) {
                                image.setImageResource(R.drawable.second)
                            } else if (rating <= 3f) {
                                image.setImageResource(R.drawable.third)
                            } else if (rating <= 4f) {
                                image.setImageResource(R.drawable.fourth)
                            } else if (rating <= 5f) {
                                image.setImageResource(R.drawable.fifth)
                            }
                        }


                    btn_not_now.setOnClickListener { v: View? ->
                        // hideRateUseDialog()
                        ActivityCompat.finishAffinity(activity)
                    }

                    btn_later.setOnClickListener { v: View? ->
                        // hideRateUseDialog()
                        rateUsDialog.dismiss()
                    }

                    btn_rate.setOnClickListener { v: View? ->
                        Log.d("TAG", "rateUsDialog: $ratingCount")

                        if (ratingCount >= 4) {
                            MainApplication.prefs1?.userHasRatedApp = true
                            rate_text.visibility = View.VISIBLE
                            feedback_text.isVisible = false
                            rateUsDialog.dismiss()
                            rateUs(activity)
                            //  hideRateUseDialog()
                        } else if (ratingCount <= 3 && ratingCount > 0) {
                            feedback_text.isVisible = true
                            emojiRatingBar.isVisible = false
                            submit.isVisible = true
                            btn_rate.isVisible = false
                            textTitle.text = activity.getString(R.string.Help)
                            rate_text.visibility = View.GONE

                            submit.setOnClickListener {

                                //gotoFeedBack(activity, feedback_text.text.toString().trim())
                                rateUsDialog.dismiss()
                                MainApplication.prefs1?.userHasRatedApp = true
                                Toast.makeText(
                                    activity,
                                    activity.resources.getString(R.string.thanks_for_feedback),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }


                        } else {
                            rateUsDialog.dismiss()
                            Toast.makeText(
                                activity,
                                activity.getResources().getString(R.string.star_to_give_us),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    rateUsDialog.setOnDismissListener { dialog ->

                    }
                    if (!activity.isFinishing)
                        rateUsDialog.show()
                }
            }, 500)
        }

        private fun gotoFeedBack(activity: Activity, text: String) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("some@email.address"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "App Feedback")
            intent.putExtra(Intent.EXTRA_TEXT, text)

        }


        fun showExitConfirmationDialog(activity: Activity) {
            val builder = AlertDialog.Builder(activity)
            val inflater = activity.layoutInflater
            val dialogView = inflater.inflate(R.layout.layout_delete, null)

            builder.setView(dialogView)
            val dialog = builder.create()

            // Find the "Yes" and "No" buttons by their IDs
            val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
            val btnNo = dialogView.findViewById<Button>(R.id.btnNo)

            val title = dialogView.findViewById<TextView>(R.id.title)
            val message = dialogView.findViewById<TextView>(R.id.message)
            title.text = activity.getString(R.string.Exit)
            message.text = activity.getString(R.string.sure_exit)

            btnYes.setOnClickListener {
                activity.finish()
            }
            btnNo.setOnClickListener { dialog.dismiss() }

            dialog.show()
        }

        fun rateUs(activity: Activity) {
            val uri: Uri = Uri.parse("market://details?id=${activity!!.packageName}")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(
                Intent.FLAG_ACTIVITY_NO_HISTORY or
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                activity.startActivity(goToMarket)
            } catch (e: ActivityNotFoundException) {
                activity.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=$activity!!.packageName")
                    )
                )
            }
        }

        fun shareUs(activity: Activity?) {

            val appPackageName = activity!!.packageName
            val appMarketLink = "https://play.google.com/store/apps/details?id=$appPackageName"

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Download our app from Google Play:\n$appMarketLink"
            )

            activity.startActivity(Intent.createChooser(intent, "Share via"))

        }

        public fun hideSystemUI(_decorView: View) {
            val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
            _decorView.setSystemUiVisibility(uiOptions)
        }

        public fun showSystemUI(_decorView: View) {
            val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
            _decorView.setSystemUiVisibility(uiOptions)
        }

        fun Activity.privacyPolicy() {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacypolicy)))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        fun Activity.contactus() {
            val recipientEmail = "swainfo.myapps@gmail.com"
            val subject = "Issue Report"
            val message = "I encountered an issue with your app. Here are the details:\n\n"

            // Create an Intent with ACTION_SENDTO
            val emailIntent = Intent(Intent.ACTION_SENDTO)

            // Set the email recipient
            emailIntent.data = Uri.parse("mailto:$recipientEmail")

            // Set the email subject and message
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, message)

            // Start the email client
            startActivity(emailIntent)
        }

        fun Activity.setStatusBarColor(activity: Activity,window: Window){
            val isDarkMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES

            if (isDarkMode) {
                // Dark mode is active, set status bar color to dark
                updateStatusBarColor(activity,window, R.color.black)
                // You may also set the status bar icons to light if needed
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                        android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                // Light mode is active, set status bar color to light
                updateStatusBarColor(activity,window,R.color.transparent)
                // Clear the light status bar flag if set
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                        android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
        }
        private fun updateStatusBarColor(activity: Activity,window:Window,colorResId: Int) {
            window.statusBarColor = ContextCompat.getColor(activity, colorResId)
        }
        fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
            val safeClickListener = SafeClickListener {
                onSafeClick(it)
            }
            setOnClickListener(safeClickListener)
        }


    }

}