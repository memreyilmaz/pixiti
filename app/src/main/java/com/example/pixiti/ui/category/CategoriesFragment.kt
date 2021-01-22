package com.example.pixiti.ui.category

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pixiti.viewmodel.ImageViewModel
import com.example.pixiti.ui.MainActivity
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentCategoriesBinding
import com.example.pixiti.model.Category
import com.example.pixiti.ui.AboutFragment
import com.example.pixiti.ui.list.ListFragmentArgs
import com.example.pixiti.utils.KEY_DAY_NIGHT
import com.example.pixiti.utils.PREFS_FILE
import org.koin.android.viewmodel.ext.android.sharedViewModel

class CategoriesFragment : Fragment() {

    private val viewModel by sharedViewModel<ImageViewModel>()

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val categoriesAdapter by lazy {
        CategoriesAdapter().apply {
            onItemClickListener = { category ->
                val query = ListFragmentArgs(category).toBundle()
                // val query = ListFragmentArgs.setQuery(category).build().toBundle()
                view?.findNavController()?.navigate(R.id.nav_list, query)
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
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.label_categories)
        binding.recyclerViewCategory.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), CATEGORY_GRID_COUNT)
            adapter = categoriesAdapter
        }
        categoriesAdapter.updateItems(Category.createCategoryList())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_categories, menu)
        menu.findItem(R.id.item_day_night_categories).isChecked = getDayNightPreference()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_info_categories -> {
                showAboutFragment()
                return true
            }
            R.id.item_day_night_categories -> {
                item.isChecked = !item.isChecked
                (activity as MainActivity).setDayNightModePreferences(item.isChecked)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDayNightPreference(): Boolean {
        val preferences = activity?.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
        return preferences?.getBoolean(KEY_DAY_NIGHT, true) ?: false
    }

    private fun showAboutFragment(){
        val aboutFragment: DialogFragment = AboutFragment.newInstance()
        aboutFragment.show(childFragmentManager, AboutFragment.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CATEGORY_GRID_COUNT = 3
        fun newInstance() = CategoriesFragment()
    }
}