package com.mirea.kotov.mireaproject

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible

class MusicFragment : Fragment() {
    private var buttonPlayMusic: Button? = null
    private var buttonStopMusic: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.nav_fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region Defining variables

        buttonPlayMusic = view.findViewById(R.id.buttonPlayMusic)
        buttonStopMusic = view.findViewById(R.id.buttonStopMusic)

        //endregion

        //region Adding button handlers

        buttonPlayMusic!!.setOnClickListener{onPlayMusicBtnClick()}
        buttonStopMusic!!.setOnClickListener{onStopMusicBtnClick()}

        //endregion
    }

    private fun onPlayMusicBtnClick(){
        activity?.startService(Intent(activity, MusicService::class.java))

        buttonPlayMusic?.visibility = View.GONE
        buttonStopMusic?.visibility = View.VISIBLE
    }

    private fun onStopMusicBtnClick(){
        activity?.stopService(Intent(activity, MusicService::class.java))

        buttonStopMusic?.visibility = View.GONE
        buttonPlayMusic?.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = MusicFragment()
    }
}