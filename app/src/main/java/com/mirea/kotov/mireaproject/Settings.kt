package com.mirea.kotov.mireaproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Settings : Fragment() {
    private var editText: EditText? = null
    private var textView: TextView? = null
    private var buttonSave: Button? = null
    private var buttonLoad: Button? = null
    private var preferences: SharedPreferences? = null
    val SAVED_TEXT: String = "saved_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.nav_fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region Defining variables

        editText = view.findViewById(R.id.editText)
        textView = view.findViewById(R.id.textView)
        buttonSave = view.findViewById(R.id.buttonSave)
        buttonLoad = view.findViewById(R.id.buttonLoad)
        preferences = activity?.getPreferences(AppCompatActivity.MODE_PRIVATE)

        //endregion

        //region Event handlers

        buttonSave!!.setOnClickListener{onSaveText()}
        buttonLoad!!.setOnClickListener{onLoadText()}

        //endregion
    }

    private fun onSaveText(){
        val editor: SharedPreferences.Editor = preferences!!.edit()

        editor.putString(SAVED_TEXT, editText!!.text.toString())
        editor.apply()

        Toast.makeText(activity, "Text saved", Toast.LENGTH_SHORT).show()
    }

    private fun onLoadText(){
        var text: String? = preferences?.getString(SAVED_TEXT, "Empty")
        textView!!.text = text
    }

    companion object {
        @JvmStatic
        fun newInstance() = Settings()
    }
}