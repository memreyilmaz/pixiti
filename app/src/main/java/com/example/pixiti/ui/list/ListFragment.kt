package com.example.pixiti.ui.list

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pixiti.ImageViewModel
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentListBinding
import com.example.pixiti.ui.detail.DetailActivity
import com.example.pixiti.utils.toast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment() {

    private val viewModel by sharedViewModel<ImageViewModel>()
    private val arguments : ListFragmentArgs by navArgs()
    private var binding: FragmentListBinding? = null

    private val listAdapter by lazy {
        ListAdapter().apply {
            onItemClickListener = { image ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.BUNDLE_IMAGE, image as Parcelable)
                }
                startActivity(intent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val query = arguments.query
        activity?.title = query.capitalize()
        binding?.recyclerViewList?.apply {
            setHasFixedSize(true)
            layoutManager =
                StaggeredGridLayoutManager(LIST_GRID_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = listAdapter.withLoadStateHeaderAndFooter(
                header = ListLoadStateAdapter().apply {
                    onRetrylickListener = { listAdapter.retry() }
                },
                footer = ListLoadStateAdapter().apply {
                    onRetrylickListener = { listAdapter.retry() }
                }
            )
            isNestedScrollingEnabled = false
        }

        lifecycleScope.launch {
            viewModel.searchImages(query).collectLatest {
                listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val LIST_GRID_COUNT = 2

        fun newInstance() = ListFragment()
    }
}