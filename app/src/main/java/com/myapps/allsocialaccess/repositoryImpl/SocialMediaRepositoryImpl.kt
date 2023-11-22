package com.myapps.allsocialaccess.repositoryImpl

import android.content.Context
import com.myapps.allsocialaccess.R
import com.myapps.allsocialaccess.models.SocialMediaApp
import com.myapps.allsocialaccess.repository.SocialMediaRepository

class SocialMediaRepositoryImpl : SocialMediaRepository {

    override suspend fun getSocialMediaApps(context: Context): List<SocialMediaApp> {
        val socialMediaApps = mutableListOf<SocialMediaApp>()

        // Predefined list of common social media apps
        val commonSocialMediaApps = listOf(
            SocialMediaApp("Facebook", "https://www.facebook.com", R.drawable.ic_facebook),
            SocialMediaApp("Twitter", "https://www.twitter.com", R.drawable.ic_twitter),
            SocialMediaApp("Instagram", "https://www.instagram.com", R.drawable.ic_instagram),
            SocialMediaApp("LinkedIn", "https://www.linkedin.com", R.drawable.ic_linkdin),
            SocialMediaApp("WhatsApp", "https://web.whatsapp.com/", R.drawable.ic_whatsapp),
            SocialMediaApp("Skype", "https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=16&ct=1699462252&rver=7.1.6819.0&wp=MBI_SSL&wreply=https%3A%2F%2Flw.skype.com%2Flogin%2Foauth%2Fproxy%3Fclient_id%3D572381%26redirect_uri%3Dhttps%253A%252F%252Fweb.skype.com%252FAuth%252FPostHandler%26state%3Dd32970f0-ec23-449a-8db0-1947a6450134&lc=1033&id=293290&mkt=en-US&psi=skype&lw=1&cobrandid=2befc4b5-19e3-46e8-8347-77317a16a5a5&client_flight=ReservedFlight33%2CReservedFlight67", R.drawable.ic_skype),
            SocialMediaApp("Telegram", "https://web.telegram.org/k/", R.drawable.ic_telegram),
            SocialMediaApp("Signal", "https://signal.org/", R.drawable.ic_signal),
            SocialMediaApp("WeChat", "https://web.wechat.com/?lang=en", R.drawable.wechat),
            SocialMediaApp("Line", "https://access.line.me/oauth2/v2.1/login?fromDomain=access-auto.line.me&loginState=nBeMaOY7Mdu5aFeSo1hVfn&line_auto_login_error=universal_link_error&loginChannelId=1445834080&returnUri=oauth2v2.1authorizeconsentbot_promptnormalscopeopenidprofileresponse_typecodestateRhiVgBoLDGredirect_urihttps3A2F2Fform.tonariwa.com2Fsuntory2Fmeasure_url3Fcpid3Dtennensui_sampling426lcd3D126type3D126nonceEvutxGZ894client_id1445834080#/", R.drawable.ic_line),
            SocialMediaApp("Viber", "https://www.viber.com/en/", R.drawable.ic_viber),
            SocialMediaApp("Discord", "https://discord.com/channels/@me", R.drawable.ic_discord),
            SocialMediaApp("Yahoo Messenger", "https://login.yahoo.com/?&.src", R.drawable.ic_yahoo),
        )
        socialMediaApps.addAll(commonSocialMediaApps)
        return socialMediaApps

    }
    private fun generatePlayStoreUrl(packageName: String): String {
        return "https://play.google.com/store/apps/details?id=$packageName"
    }


}
