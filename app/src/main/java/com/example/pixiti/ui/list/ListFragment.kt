package com.example.pixiti.ui.list

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pixiti.viewmodel.ImageViewModel
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentListBinding
import com.example.pixiti.ui.detail.DetailActivity
import com.example.pixiti.utils.showIf
import com.example.pixiti.utils.toast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment() {

    private val viewModel by sharedViewModel<ImageViewModel>()
    private val arguments: ListFragmentArgs by navArgs()
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val query = arguments.query
        activity?.title = query.capitalize()
        initListAdapter()
        lifecycleScope.launch {
            viewModel.searchImages(query).collectLatest {
                listAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    private fun initListAdapter() {
        binding.recyclerViewList.apply {
            setHasFixedSize(true)
            layoutManager =
                StaggeredGridLayoutManager(LIST_GRID_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = listAdapter.withLoadStateHeaderAndFooter(
                header = ListLoadStateAdapter().apply {
                    onRetryClickListener = { listAdapter.retry() }
                },
                footer = ListLoadStateAdapter().apply {
                    onRetryClickListener = { listAdapter.retry() }
                }
            )
            isNestedScrollingEnabled = false
        }

        listAdapter.addLoadStateListener { loadState ->
            binding.apply {
                recyclerViewList.showIf(loadState.source.refresh is LoadState.NotLoading)
                progressBar.showIf(loadState.source.refresh is LoadState.Loading)
                buttonListMainRetry.showIf(loadState.source.refresh is LoadState.Error)
                buttonBackToSearch.showIf(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached)
                textViewEmptySearchList.showIf(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached)
            }

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                requireContext().applicationContext.toast("${it.error}")
            }
        }
        binding.buttonListMainRetry.setOnClickListener { listAdapter.retry() }
        binding.buttonBackToSearch.setOnClickListener {
            view?.findNavController()?.navigate(R.id.nav_search)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_filter_list -> {
                view?.findNavController()?.navigate(R.id.action_nav_list_to_settingsFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val LIST_GRID_COUNT = 2

        fun newInstance() = ListFragment()
    }
}