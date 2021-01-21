package com.example.pixiti.ui.search

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pixiti.ImageViewModel
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentSearchBinding
import com.example.pixiti.model.Image
import com.example.pixiti.ui.detail.DetailActivity
import com.example.pixiti.ui.list.ListFragmentArgs
import com.example.pixiti.utils.hideKeyboard
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val viewModel by viewModel<ImageViewModel>()
    private var backgroundImage: Image? = null
    private var isImageLoaded: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = ""
        initView()
    }

    private fun initView() {
        binding?.imageViewSearchBackground?.setOnClickListener {
            if (isImageLoaded){
                backgroundImage.let { image ->
                    val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                        putExtra(DetailActivity.BUNDLE_IMAGE, image as Parcelable)
                    }
                    startActivity(intent)
                }
            }
        }
        viewModel.randomImage.observe(viewLifecycleOwner, { backgroundImage ->
            backgroundImage.let {
                this.backgroundImage = backgroundImage
                binding?.imageViewSearchBackground?.let {
                    Glide.with(requireContext())
                        .load(backgroundImage.largeImageURL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                isImageLoaded = false
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                isImageLoaded = true
                                return false
                            }
                        }).into(it)
                }
            }
        })

        binding?.searchViewMain?.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                //no-op
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                view?.let {
                    activity?.hideKeyboard(it)
                }
                if (query.trim().isNotEmpty()) {
                    val searchQuery = query.trim()
                    val query = ListFragmentArgs(searchQuery).toBundle()
                    // val query = ListFragmentArgs.Builder().setQuery(searchQuery).build().toBundle()
                    view?.findNavController()?.navigate(R.id.nav_list, query)
                }
                return true
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}