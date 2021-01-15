package com.example.pixiti.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.findNavController
import com.example.pixiti.ImageViewModel
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentSearchBinding
import com.example.pixiti.ui.list.ListFragmentArgs
import com.example.pixiti.utils.hideKeyboard
import com.example.pixiti.utils.loadImageWithout
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private var binding: FragmentSearchBinding? = null
    private val viewModel by viewModel<ImageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding?.imageViewSearchInfo?.setOnClickListener {          //todo give info of background image and give detail navigation
             }
        viewModel.randdomImage.observe(viewLifecycleOwner, {
            it.let {
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}