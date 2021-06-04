package com.dalamsyah.siaprint.ui.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import java.io.*


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
        const val REQUEST_CODEP_PERMISSION = 102
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

        checkPermission()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                    Logger.d("PERMISSION_GRANTED")
                }
                requireActivity().shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected. In this UI,
                    // include a "cancel" or "no thanks" button that allows the user to
                    // continue using your app without granting the permission.
                    Logger.d("shouldShowRequestPermissionRationale")
                }
                else -> {
                    // You can directly ask for the permission.
                    // The registered ActivityResultCallback gets the result of this request.
                    requireActivity().requestPermissions(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        REQUEST_CODEP_PERMISSION
                    )

                    Logger.d("requestPermissions")
                }
            }
        }
    }

    private fun openImageChooser2() {
        Intent(Intent.ACTION_PICK).also {
            val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    private fun openImageChooser() {
//        Intent(Intent.ACTION_PICK).also {
//            it.type = "*/*"
//            it.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult( Intent.createChooser(it, "Select a file"),  REQUEST_CODE_PICK_IMAGE)
//        }

        val intent = Intent()
            .setType("*/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult( Intent.createChooser(intent, "Select a file"),  REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    val path = data?.data?.path

                    Logger.d(data)

                    /*
                     * Get the file's content URI from the incoming Intent, then
                     * get the file's MIME type
                    */
                    val mimeType: String? = data?.data?.let { returnUri ->
                        requireActivity().contentResolver.getType(returnUri)
                    }

                    Logger.d(mimeType)

                    /*
                     * Get the file's content URI from the incoming Intent,
                     * then query the server app to get the file's display name
                     * and size.
                    */
                    data?.data?.let { returnUri ->
                        requireActivity().contentResolver.query(returnUri, null, null, null, null)
                    }?.use { cursor ->
                        /*
                         * Get the column indexes of the data in the Cursor,
                         * move to the first row in the Cursor, get the data,
                         * and display it.
                         */
                        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                        cursor.moveToFirst()

                        Logger.d(cursor.getString(nameIndex))
                        Logger.d(cursor.getLong(sizeIndex).toString())

                        uploads.add( Upload(idUpload++, cursor.getString(nameIndex) ) )
                        viewModel.listUpload.value = uploads

//                        countPages(  )


                    }

                    val filename = ""+path?.lastIndexOf("/")?.plus(1)?.let { path.substring(it) }

                    Logger.d(filename)

//                    binding.ivResultImage.setImageURI(selectedImageUri)
//                    binding.tvFileName.text = filename

                }
            }
        }
    }

    @Throws(IOException::class)
    private fun countPages(pdfFile: File) {
        try {
            val parcelFileDescriptor =
                ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
            var pdfRenderer: PdfRenderer? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                pdfRenderer = PdfRenderer(parcelFileDescriptor)
                Logger.d(pdfRenderer.pageCount)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun upload(){

        val parcelFileDescriptor = requireActivity().contentResolver.openFileDescriptor(selectedImageUri!!, "r") ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(selectedImageUri!!))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pdf = PdfRenderer(parcelFileDescriptor)
            Logger.d(pdf.pageCount)
        }

//        countPages( file )

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