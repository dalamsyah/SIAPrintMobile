package com.dalamsyah.siaprint.ui.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toFile
import androidx.lifecycle.ViewModelProvider
import com.dalamsyah.siaprint.BuildConfig
import com.dalamsyah.siaprint.R
import com.dalamsyah.siaprint.databinding.FragmentUploadBinding
import com.dalamsyah.siaprint.models.Upload
import com.dalamsyah.siaprint.retrofit.Status
import com.dalamsyah.siaprint.ui.utils.BaseFragment
import com.dalamsyah.siaprint.utils.getFileName
import com.orhanobut.logger.Logger
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class UploadFragment : BaseFragment() {

    private lateinit var viewModel: UploadViewModel
    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null
    private lateinit var adapter: UploadAdapter
    private var idUpload = 0
    private var uploads = mutableListOf<Upload>()
    private lateinit var upload: Upload
    private var progress = 0

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(UploadViewModel::class.java)
        _binding = FragmentUploadBinding.inflate(inflater, container, false)

        binding.btnUpload.setOnClickListener {
            openImageChooser()
        }

        binding.btnHitung.setOnClickListener {
            upload()
        }

        adapter = UploadAdapter( UploadListener { model ->
            uploads.remove(model)
            viewModel.listUpload.value = uploads
        })
        binding.recyclerView.adapter = adapter

        viewModel.listUpload.value = mutableListOf()
        viewModel.listUpload.observe(viewLifecycleOwner, {
            adapter.submitList(it.toMutableList())
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun openImageChooser2() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "application/*"
//            val mimeTypes = arrayOf("image/jpeg", "image/png")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "application/*"
            it.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    val path = data?.data?.path

                    Logger.d(path)

                    val filename = ""+path?.lastIndexOf("/")?.plus(1)?.let { path.substring(it) }

                    Logger.d(filename)

//                    binding.ivResultImage.setImageURI(selectedImageUri)
//                    binding.tvFileName.text = filename

                    uploads.add( Upload(idUpload++, filename ) )
                    viewModel.listUpload.value = uploads
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun upload(){

        Logger.d(selectedImageUri)

        val parcelFileDescriptor = requireActivity().contentResolver.openFileDescriptor(selectedImageUri!!, "r") ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        progress = 0
        val body = UploadRequestBody(file, "image", object : UploadRequestBody.UploadCallback{
            override fun onProgressUpdate(percentage: Int) {
//                Logger.d(percentage)
            }
        } )

        viewModelAPI.upload(
            pref.apiToken,
            pref.user?.id!!,
            MultipartBody.Part.createFormData(
                "inputFile1",
                file.name,
                body
            ),
            MultipartBody.Part.createFormData(
                "inputFile2",
                file.name,
                body
            ),
            MultipartBody.Part.createFormData(
                "inputFile3",
                file.name,
                body
            ),
            MultipartBody.Part.createFormData(
                "inputFile4",
                file.name,
                body
            ),
            MultipartBody.Part.createFormData(
                "inputFile5",
                file.name,
                body
            ),
            RequestBody.create(MediaType.parse("multipart/form-data"), "json")
        ).observe(viewLifecycleOwner, {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        mainViewModel.showProgress(true)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.showProgress(false)

                        it.data?.let { api ->

                            Logger.d( api.result )

                            progress = 100
                        }
                    }
                    Status.ERROR -> {

                        progress = 0

                        if (BuildConfig.DEBUG){
                            mainViewModel.showDialog("${it.message}", false )
                        } else {
                            mainViewModel.showDialog(resources.getString(R.string.error_api), false )
                        }
                        mainViewModel.showProgress(false)
                    }
                }
            }
        })

    }

    private fun doUpload(){
        viewModelAPI.status(pref.apiToken, pref.user?.id.toString()).observe(viewLifecycleOwner, {
            it.let {
                when(it.status){
                    Status.LOADING -> {
                        mainViewModel.showProgress(true)
                    }
                    Status.SUCCESS -> {
                        mainViewModel.showProgress(false)

                        it.data?.let { api ->

                            Logger.d( api.result )

                            if (api.result != null) {

                            } else {

                            }
                        }
                    }
                    Status.ERROR -> {
                        if (BuildConfig.DEBUG){
                            mainViewModel.showDialog("${it.message}", false )
                        } else {
                            mainViewModel.showDialog(resources.getString(R.string.error_api), false )
                        }
                        mainViewModel.showProgress(false)
                    }
                }
            }
        })
    }

}