package com.mirea.kotov.mireaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewFragment : Fragment() {
    private var webView: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webView = view?.findViewById(R.id.webView)
        webView?.webViewClient = WebViewClient()
        webView?.settings?.javaScriptEnabled = true
        webView?.loadUrl("https://developer.android.com/guide/fragments")

    }

    companion object {

        @JvmStatic
        fun newInstance() = WebViewFragment()
    }
}