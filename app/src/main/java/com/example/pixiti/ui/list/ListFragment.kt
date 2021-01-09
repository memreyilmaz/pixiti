package com.example.pixiti.ui.list

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.pixiti.ImageViewModel
import com.example.pixiti.databinding.FragmentListBinding
import com.example.pixiti.ui.detail.DetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment() {

    private val viewModel by sharedViewModel<ImageViewModel>()

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

        binding?.recyclerViewList?.apply {
            setHasFixedSize(true)
            layoutManager =
                StaggeredGridLayoutManager(LIST_GRID_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = listAdapter
            isNestedScrollingEnabled = false
        }

        lifecycleScope.launch {
            viewModel.getImagesList("fruit").collectLatest {
                listAdapter.submitData(it)
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