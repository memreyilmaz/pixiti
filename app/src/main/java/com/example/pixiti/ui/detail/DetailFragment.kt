package com.example.pixiti.ui.detail

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentDetailBinding
import com.example.pixiti.model.Image
import com.example.pixiti.ui.detail.DetailActivity.Companion.BUNDLE_IMAGE
import com.example.pixiti.utils.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var image: Image? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            image = it.getParcelable(BUNDLE_IMAGE) as? Image
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.apply {
            imageViewDetail.apply {
                loadImage(imageUrl = image?.largeImageURL, requireContext())
                setOnPhotoTapListener { _, _, _ ->
                    constraintLayoutDetailTop.showIfNotVisible()
                    constraintLayoutDetailBottom.showIfNotVisible()
                }
            }

            imageViewImageOwner.loadImage(
                imageUrl = image?.userImageURL,
                context = requireContext()
            )
            textViewImageOwner.text = image?.user
            textViewThumbCount.text = image?.likes.toIntOrZero().toString()
            textViewFavouriteCount.text = image?.favorites.toIntOrZero().toString()
            textViewCommentCount.text = image?.comments.toIntOrZero().toString()

            imageViewSave.setOnClickListener {
                if (PermissionUtil.isStoragePermissionGranted(requireContext())) {
                    downloadImage()
                } else {
                    requestPermissionForImageDownload()
                }
            }

            imageViewShare.setOnClickListener {
                createShareIntent()
            }

            imageViewInfo.setOnClickListener {
                showInfoDialog()
            }

            imageViewClose.setOnClickListener {
                activity?.finish()
            }
        }
    }

    private fun showInfoDialog() {
        image.let {
            val imageDetailInfoFragment: DialogFragment =
                ImageDetailInfoFragment.newInstance(it!!)
            imageDetailInfoFragment.show(
                childFragmentManager.beginTransaction(),
                ImageDetailInfoFragment.TAG
            )
        }
    }

    private fun downloadImage() {
        Glide.with(requireContext())
            .asBitmap()
            .load(image?.largeImageURL)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    //no-op
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    saveImage(resource)
                }
            })
    }

    private fun saveImage(image: Bitmap): String? {
        var imagePath: String? = null
        val imageFileName = "${System.currentTimeMillis()}.jpg"

        val storageDirectory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/pixiti"
        )
        var success = true
        if (!storageDirectory.exists()) {
            success = storageDirectory.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDirectory, imageFileName)
            imagePath = imageFile.absolutePath
            try {
                val outputStream: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream.close()
                requireContext().applicationContext.toastForDetail(getString(R.string.desc_download_success))
            } catch (e: Exception) {
                e.printStackTrace()
                requireContext().applicationContext.toastForDetail(getString(R.string.desc_image_download_error))

            }
            addImageToGallery(imagePath)
        }
        return imagePath
    }

    private fun addImageToGallery(imagePath: String?) {
        imagePath?.let {
            val file = File(it)
            MediaScannerConnection.scanFile(
                context,
                arrayOf(file.toString()),
                arrayOf(file.name),
                null
            )
        }
    }

    private fun requestPermissionForImageDownload() {
        Dexter.withContext(requireContext())
            .withPermissions(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        when {
                            it.areAllPermissionsGranted() -> {
                                downloadImage()
                            }
                            it.isAnyPermissionPermanentlyDenied -> {
                                showSettingsDialog()
                            }
                            else -> {
                                requireContext().applicationContext.toastForDetail(getString(R.string.desc_permission_missing_permission))
                            }
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }
            ).withErrorListener {
                requireContext().applicationContext.toastForDetail(getString(R.string.desc_permission_error))
            }
            .onSameThread()
            .check()
    }

    private fun showSettingsDialog() {
        context?.showAlertDialog(
            title = getString(R.string.desc_permission_denial_title),
            message = getString(R.string.desc_permission_denial_detail),
            negativeButtonText = getString(R.string.label_cancel),
            positiveButtonText = getString(R.string.label_go_to_settings),
            positiveButtonListener = {
                openSettings()
            }
        )
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context?.packageName, null)
        intent.data = uri
        startActivityForResult(intent, GO_TO_SETTINGS_REQUEST_CODE)
    }

    private fun createShareIntent() {
        val shareString = StringBuilder()
        shareString.append(getString(R.string.desc_share_image_message))
            .append("\n")
            .append(image?.pageURL)

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.apply {
            putExtra(Intent.EXTRA_TEXT, shareString.toString())
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.desc_share_with)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val GO_TO_SETTINGS_REQUEST_CODE = 100
        fun newInstance(image: Image?) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_IMAGE, image)
            }
        }
    }
}
