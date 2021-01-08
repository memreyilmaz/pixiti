package com.example.pixiti.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentDetailBinding
import com.example.pixiti.model.Image
import com.example.pixiti.ui.detail.DetailActivity.Companion.BUNDLE_IMAGE

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
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
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding?.apply {
            Glide.with(requireContext())
                .load(image?.largeImageURL)
                .placeholder(R.drawable.pixabay_logo)
                .error(R.drawable.pixabay_logo)
                .into(imageViewDetail)

            imageViewDetail.setOnPhotoTapListener { _, _, _ ->
                constraintLayoutDetailTop.isVisible = !constraintLayoutDetailTop.isVisible
                constraintLayoutDetailBottom.isVisible = !constraintLayoutDetailBottom.isVisible
            }

            Glide.with(requireContext())
                .load(image?.userImageURL)
                .placeholder(R.drawable.pixabay_logo)
                .error(R.drawable.pixabay_logo)
                .into(imageViewImageOwner)
            //todo set if zero
            textViewImageOwner.text = image?.user
            textViewThumbCount.text = image?.likes.toString()
            textViewFavouriteCount.text = image?.favorites.toString()
            textViewCommentCount.text = image?.comments.toString()

            imageViewSave.setOnClickListener {
                //TODO implement image download
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
        binding = null
    }

    companion object {

        fun newInstance(image: Image?) = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(BUNDLE_IMAGE, image)
            }
        }
    }
}