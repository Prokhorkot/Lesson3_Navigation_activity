package com.mirea.kotov.mireaproject.googleMaps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.mirea.kotov.mireaproject.R

class MyInfoWindow(context: Context): GoogleMap.InfoWindowAdapter {
    init {
        context.also { mContext = it }
        LayoutInflater.from(context).inflate(R.layout.cutom_info_window, null)
            .also { mWindow = it }
    }

    private var mWindow: View? = null
    private var mContext: Context? = null

    private fun setWindowText(marker: Marker){
        val tvTitle: TextView = mWindow!!.findViewById(R.id.tvTitle)
        val tvSnippet: TextView = mWindow!!.findViewById(R.id.tvSnippet)

        val title = marker.title
        val snippet = marker.snippet

        tvTitle.text = title
        tvSnippet.text = snippet
    }

    override fun getInfoContents(marker: Marker): View? {
        setWindowText(marker)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        setWindowText(marker)
        return mWindow    }
}