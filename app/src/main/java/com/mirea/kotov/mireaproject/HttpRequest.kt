package com.mirea.kotov.mireaproject

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mirea.kotov.mireaproject.databinding.FragmentHttpRequestBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.jvm.Throws

class HttpRequest : Fragment() {
    private lateinit var binding: FragmentHttpRequestBinding
    private val url: String = "https://jsonplaceholder.typicode.com/posts/1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHttpRequestBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGetInfo.setOnClickListener{onClick()}
    }

    private fun onClick(){
        val connectivityManager: ConnectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        var networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        if(networkInfo != null && networkInfo.isConnected){
            DownloadPageTask().execute(url)
        }
        else{
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class DownloadPageTask: AsyncTask<String?, Void, String?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            binding.tvInfo.text = "Downloading"
        }

        override fun doInBackground(vararg urls: String?): String? {
            try{
                return downloadIpInfo(urls[0]!!)
            } catch (e: IOException){
                e.printStackTrace()
                return "error"
            }
        }

        override fun onPostExecute(result: String?) {
            if (result != null) {
                Log.d(MainActivity::class.simpleName, result)
            }

            try{
                val responseJson = JSONObject(result)
                val info: String = responseJson.getString("title")

                binding.tvInfo.text = info

                Log.d(MainActivity::class.simpleName, info)
            } catch (e: JSONException){
                e.printStackTrace()
            }
            super.onPostExecute(result)
        }

    }

    @Throws(IOException::class)
    private fun downloadIpInfo(address: String): String{
        var inputStream: InputStream? = null
        var data: String = ""

        try{
            val url = URL(address)
            val connection: HttpsURLConnection = url.openConnection() as HttpsURLConnection

            connection.readTimeout = 100000
            connection.connectTimeout = 100000
            connection.requestMethod = "GET"
            connection.instanceFollowRedirects = true
            connection.useCaches = true
            connection.doInput = true

            val responseCode: Int = connection.responseCode

            if(responseCode == HttpsURLConnection.HTTP_OK){
                inputStream = connection.inputStream
                val bos = ByteArrayOutputStream()
                var read: Int = inputStream.read()

                while(read != -1){
                    bos.write(read)
                    read = inputStream.read()
                }

                val result: ByteArray = bos.toByteArray()
                bos.close()
                data = String(result)
            } else{
                data = connection.responseMessage + " . Error Code : " + responseCode
            }
            connection.disconnect()
        } catch (e: MalformedURLException){
            e.printStackTrace()
        } catch (e: IOException){
            e.printStackTrace()
        } finally{
            inputStream?.close()
        }
        return data
    }

    companion object {
        @JvmStatic
        fun newInstance() = HttpRequest()
    }
}