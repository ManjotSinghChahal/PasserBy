package com.example.passerby.ui.activities.home.fragments.privacyPolicy


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings

import com.example.passerby.R
import com.example.passerby.data.model.privacyPolicy.PrivacyPolicyModel
import com.example.passerby.data.model.termsConditions.TermsConditionsModel
import com.example.passerby.utils.Constants.PRIVACYPOLICY_DATA
import com.example.passerby.utils.Constants.TERMS_CONDITIONS_DATA
import kotlinx.android.synthetic.main.fragment_privacy_policy.*
import kotlinx.android.synthetic.main.fragment_privacy_policy.view.*


class PrivacyPolicy : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.fragment_privacy_policy, container, false)


        initializer(view)



        return view
    }


    fun initializer(view : View)
    {

        val bundle = arguments
        if (bundle!=null && bundle.containsKey(PRIVACYPOLICY_DATA))
        {
            val privacyPolicy = bundle.getParcelable<PrivacyPolicyModel>(PRIVACYPOLICY_DATA)

            val webSettings = view.webview_privacyPolicy!!.settings
            webSettings.javaScriptEnabled = true
            webSettings.builtInZoomControls = true
            webSettings.setTextSize(WebSettings.TextSize.SMALLER)
            view.webview_privacyPolicy.loadDataWithBaseURL(null,  privacyPolicy.body.description, "text/html", "utf-8", null);

             view.title_policyCond.text = getString(R.string.privacy_policy)
        }
        else  if (bundle!=null && bundle.containsKey(TERMS_CONDITIONS_DATA))
        {
            val privacyPolicy = bundle.getParcelable<TermsConditionsModel>(TERMS_CONDITIONS_DATA)

            val webSettings = view.webview_privacyPolicy!!.settings
            webSettings.javaScriptEnabled = true
            webSettings.builtInZoomControls = true
            webSettings.setTextSize(WebSettings.TextSize.SMALLER)
            view.webview_privacyPolicy.loadDataWithBaseURL(null,  privacyPolicy.body.description, "text/html", "utf-8", null);

            view.title_policyCond.text = getString(R.string.terms_conditions)
        }

        view.back_privacyPolicy.setOnClickListener {
            activity?.let { it.onBackPressed() }
        }

    }


}
