package com.mirea.kotov.mireaproject.Stories

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mirea.kotov.mireaproject.R
import com.mirea.kotov.mireaproject.databinding.FragmentStoriesBinding
import com.mirea.kotov.mireaproject.databinding.StoryDialogBinding
import com.mirea.kotov.mireaproject.ui.home.HomeFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Stories : Fragment() {
    val addButton: FloatingActionButton? = null

    private var dialog: Dialog? = null
    private lateinit var bindingLayout: FragmentStoriesBinding
    private lateinit var bindingDialog: StoryDialogBinding
    private val adapter = StoryAdapter()

    private var isWork: Boolean = false
    private var imageUri: Uri? = null

    val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            bindingDialog.apply {
                storyImagePreview.setImageURI(imageUri)
                applyStoryButton.setOnClickListener{
                    val story = Story(imageUri!!,
                            imageCaption.text.toString(),
                        SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()))
                    adapter.addStory(story)

                    dialog?.dismiss()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingLayout = FragmentStoriesBinding.inflate(inflater, container, false)

        return bindingLayout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingLayout.apply {
            addStory.setOnClickListener{onCreateNewStory()}
        }

        init()
    }

    private fun onCreateNewStory(){
        //region Checking permissions

        dialog = Dialog(requireContext())
        val cameraPermissionStatus =
            ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.CAMERA)

        val storagePermissionStatus =
            ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true

        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION_CAMERA
            )
        }

        //endregion

        bindingDialog = StoryDialogBinding.inflate(LayoutInflater.from(context))
        dialog!!.setContentView(bindingDialog.root)

        //region Taking and saving photo
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null && isWork) {
            var photoFile: File? = null

            try {
                photoFile = createImageFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val authorities = requireActivity().applicationContext.packageName + ".fileprovider"
            imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile!!)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startForResult.launch(cameraIntent)
        }
        //endregion

        TimeUnit.SECONDS.sleep(1)

        dialog!!.show()
    }

    private fun init() = with(bindingLayout){
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
    }

    //Запрос разрешений
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA){
            isWork = (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"
        val storageDirectory =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(imageFileName, ".jpg", storageDirectory)
    }

    companion object {
        @JvmStatic
        fun newInstance() = Stories()
        private const val REQUEST_CODE_PERMISSION_CAMERA = 100
    }
}