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
import com.dalamsyah.siaprint.databinding.FragmentUploadBinding
import com.dalamsyah.siaprint.models.Upload
import com.dalamsyah.siaprint.ui.utils.BaseFragment

class UploadFragment : BaseFragment() {

    private lateinit var viewModel: UploadViewModel
    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null
    private lateinit var adapter: UploadAdapter
    private var idUpload = 0
    private var uploads = mutableListOf<Upload>()
    private lateinit var upload: Upload

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

        adapter = UploadAdapter( UploadListener { model ->
            uploads.remove(model)
            viewModel.listUpload.value = uploads
        })
        binding.recyclerView.adapter = adapter

        viewModel.listUpload.value = mutableListOf()
        viewModel.listUpload.observe(viewLifecycleOwner, {
            adapter.submitList(it.toMutableList())
            Log.d("DEBUGGG", "refrehsss....")
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
                    val filename = ""+path?.lastIndexOf("/")?.plus(1)?.let { path.substring(it) }
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

}