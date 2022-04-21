package com.mirea.kotov.mireaproject.googleMaps

import android.Manifest
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mirea.kotov.mireaproject.R
import com.mirea.kotov.mireaproject.databinding.FragmentMapsBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.Buffer
import java.util.concurrent.Executors
import android.os.Handler
import kotlinx.coroutines.channels.ticker
import javax.net.ssl.HttpsURLConnection

class MapsFragment : Fragment() {
    private var map: GoogleMap? = null
    private lateinit var binding: FragmentMapsBinding

    //region Coordinates

    private val mireaMainLocation = LatLng(55.6687271,37.4738044)
    private val mireaLomonosLocation = LatLng(55.6682676,37.4603034)
    private val mireaStromLocation = LatLng(55.7935328,37.6990902)

    //endregion

    private val callback = OnMapReadyCallback { googleMap: GoogleMap ->
        map = googleMap

        googleMap.setInfoWindowAdapter(MyInfoWindow(requireContext()))

        val mireaMainMarkerOptions = MarkerOptions().position(mireaMainLocation)
            .title("Главное здание МИРЭА")
            .snippet("Дата основания: 28 мая 1947 г.\n" +
                    "Адрес: пр. Вернадского, 78, Москва, 119454\n" +
                    "Координаты: ${mireaMainLocation.latitude}," +
                    " ${mireaMainLocation.longitude}")

        val mireaLomonosMarkerOptions = MarkerOptions().position(mireaLomonosLocation)
            .title("Институт тонких химических технологий имени М.В. Ломоносова")
            .snippet("Дата основания: 1 июля 1900 г.\n" +
                    "Адрес: пр. Вернадского, 86, Москва, 119571\n" +
                    "Координаты: ${mireaLomonosLocation.latitude}," +
                    " ${mireaLomonosLocation.longitude}")

        val mireaStromMarkerOptions = MarkerOptions().position(mireaStromLocation)
            .title("Корпус МИРЭА на Стромынке")
            .snippet("Дата основания: 29 августа 1936 г.\n" +
                    "Адрес: ул. Стромынка, 20, Москва, 107996\n" +
                    "Координаты: ${mireaStromLocation.latitude}," +
                    " ${mireaStromLocation.longitude}")


        googleMap.addMarker(mireaMainMarkerOptions)
        googleMap.addMarker(mireaLomonosMarkerOptions)
        googleMap.addMarker(mireaStromMarkerOptions)

        val cameraPosition = CameraPosition.Builder().target(mireaMainLocation).zoom(11f).build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)



        //region Event handlers


//        Тут Должны были быть дороги, но этот серви оказался платным
//
//        binding.apply {
//
//            buttonToMB.setOnClickListener{getRouteToPlace(mireaMainLocation, mireaStromLocation)}
//        }

        //endregion

    }

    private fun getRouteToPlace(origin: LatLng, destination: LatLng){
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude
        val str_dest = "destination=" + destination.latitude + "," + destination.longitude
        val parameters = str_origin + "&" + str_dest + "&sensor=false"

        val request = "https://maps.googleapis.com/maps/api/directions/json?" + parameters +
                "&key=" + "AIzaSyBwsGnDbpCniWgTxm5PX4T-WOnlWXQllbs"

        Thread {
            val response: FloatArray = handleRequest(request)

            Handler(Looper.getMainLooper()).post{

            }
        }.start()
    }

    private fun handleRequest(request: String): FloatArray{
        var url = URL(request)

        with(url.openConnection() as HttpsURLConnection){
            readTimeout = 100000
            connectTimeout = 100000
            requestMethod = "GET"
            instanceFollowRedirects = true
            useCaches = true
            doInput = true

            if(responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }

                    val responseJSON = JSONObject(response.toString())
                    val routesPoints: JSONArray = responseJSON.getJSONArray("routes")


                }
            } else{
                Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
            }


        }
        return FloatArray(5,{0f})

    }

}