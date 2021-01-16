package com.example.pixiti.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pixiti.ImageViewModel
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentCategoriesBinding
import com.example.pixiti.model.Category
import com.example.pixiti.ui.list.ListFragmentArgs
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CategoriesFragment : Fragment() {

    private val viewModel by sharedViewModel<ImageViewModel>()

    private var binding: FragmentCategoriesBinding? = null
    private val categoriesAdapter by lazy {
        CategoriesAdapter().apply {
            onItemClickListener = { category->
                val query = ListFragmentArgs(category).toBundle()
               // val query = ListFragmentArgs.setQuery(category).build().toBundle()
                view?.findNavController()?.navigate(R.id.nav_list, query)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.label_categories)
        binding?.recyclerViewCategory?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), CATEGORY_GRID_COUNT)
            adapter = categoriesAdapter
        }
        categoriesAdapter.updateItems(Category.createCategoryList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val CATEGORY_GRID_COUNT = 3
        fun newInstance() = CategoriesFragment()
    }
}