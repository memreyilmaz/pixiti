package com.example.pixiti.ui.search

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.navigation.findNavController
import com.example.pixiti.ImageViewModel
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentSearchBinding
import com.example.pixiti.model.Image
import com.example.pixiti.ui.detail.DetailActivity
import com.example.pixiti.ui.list.ListFragmentArgs
import com.example.pixiti.utils.hideKeyboard
import com.example.pixiti.utils.loadImageWithout
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val viewModel by viewModel<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private var backgroundImage: Image? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.label_search)
        initView()
    }

    private fun initView() {
        binding?.imageViewSearchInfo?.setOnClickListener {
            backgroundImage.let { image ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.BUNDLE_IMAGE, image as Parcelable)
                }
                startActivity(intent)
            }
        }
        viewModel.randdomImage.observe(viewLifecycleOwner, {
            it.let {
                backgroundImage = it
                binding?.imageViewSearchBackground?.loadImageWithout(
                    imageUrl = it.largeImageURL,
                    context = requireContext()
                )
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_info_search -> {
                //todo implement about
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}