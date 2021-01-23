package com.example.pixiti.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentFilterBinding
import com.example.pixiti.utils.select
import com.example.pixiti.utils.unSelect
import com.example.pixiti.viewmodel.ImageViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel by sharedViewModel<ImageViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Filter"
        initTypeList()
        initFilterList()
        initOrientationList()
        initSafeSearchList()
    }

    private fun initTypeList() {
        val typeArray = resources.getStringArray(R.array.image_type)

        val listOfTypeTextView = mutableListOf<TextView>()
        binding.apply {
            typeArray.forEach { filterType ->
                val textViewFilter =
                    LayoutInflater.from(context).inflate(
                        R.layout.item_filter,
                        flexboxLayoutImageType,
                        false
                    ) as TextView
                textViewFilter.apply {
                    text = filterType
                    tag = typeArray.indexOf(filterType)
                }
                flexboxLayoutImageType.addView(textViewFilter)
                listOfTypeTextView.add(textViewFilter)
                textViewFilter.setOnClickListener { selectedTextView ->
                    if (!selectedTextView.isSelected) {
                        listOfTypeTextView.forEach { textView ->
                            textView.unSelect()
                        }
                        selectedTextView.select()
                        viewModel.setImageType(typeArray[selectedTextView.tag as Int].toLowerCase())
                    } else {
                        selectedTextView.unSelect()
                        viewModel.setImageType(null)
                    }
                }
            }
        }
    }

    private fun initFilterList() {
        val filterArray = resources.getStringArray(R.array.image_filter)

        val listOfFilterTextView = mutableListOf<TextView>()
        binding.apply {
            filterArray.forEach { filterType ->
                val textViewFilter =
                    LayoutInflater.from(context).inflate(
                        R.layout.item_filter,
                        flexboxLayoutFilter,
                        false
                    ) as TextView
                textViewFilter.apply {
                    text = filterType
                    tag = filterArray.indexOf(filterType)
                }
                flexboxLayoutFilter.addView(textViewFilter)
                listOfFilterTextView.add(textViewFilter)
                textViewFilter.setOnClickListener { selectedTextView ->
                    if (!selectedTextView.isSelected) {
                        listOfFilterTextView.forEach { textView ->
                            textView.unSelect()
                        }
                        selectedTextView.select()

                        val isEditorsChoice = selectedTextView.tag != 0
                        viewModel.setEditorsChoice(isEditorsChoice)
                    } else {
                        selectedTextView.unSelect()
                        viewModel.setEditorsChoice(null)
                    }
                }
            }
        }
    }

    private fun initOrientationList() {
        val orientationArray = resources.getStringArray(R.array.image_orientation)

        val listOfOrientationTextView = mutableListOf<TextView>()
        binding.apply {
            orientationArray.forEach { filterType ->
                val textViewFilter =
                    LayoutInflater.from(context).inflate(
                        R.layout.item_filter,
                        flexboxLayoutOrientation,
                        false
                    ) as TextView
                textViewFilter.apply {
                    text = filterType
                    tag = orientationArray.indexOf(filterType)
                }
                flexboxLayoutOrientation.addView(textViewFilter)
                listOfOrientationTextView.add(textViewFilter)
                textViewFilter.setOnClickListener { selectedTextView ->
                    if (!selectedTextView.isSelected) {
                        listOfOrientationTextView.forEach { textView ->
                            textView.unSelect()
                        }
                        selectedTextView.select()

                        viewModel.setOrientation(orientationArray[selectedTextView.tag as Int].toLowerCase())
                    } else {
                        selectedTextView.unSelect()
                        viewModel.setOrientation(null)
                    }
                }
            }
        }
    }

    private fun initSafeSearchList() {
        val safeSearchArray = resources.getStringArray(R.array.safe_search)

        val listOfSafeSearchTextView = mutableListOf<TextView>()
        binding.apply {
            safeSearchArray.forEach { filterType ->
                val textViewFilter =
                    LayoutInflater.from(context).inflate(
                        R.layout.item_filter,
                        flexboxLayoutSafeSearch,
                        false
                    ) as TextView
                textViewFilter.apply {
                    text = filterType
                    tag = safeSearchArray.indexOf(filterType)
                }
                flexboxLayoutSafeSearch.addView(textViewFilter)
                listOfSafeSearchTextView.add(textViewFilter)
                textViewFilter.setOnClickListener { selectedTextView ->
                    if (!selectedTextView.isSelected) {
                        listOfSafeSearchTextView.forEach { textView ->
                            textView.unSelect()
                        }
                        selectedTextView.select()
                        val isSafeSearch = selectedTextView.tag == 0
                        viewModel.setSafeSearch(isSafeSearch)
                    } else {
                        selectedTextView.unSelect()
                        viewModel.setSafeSearch(null)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FilterFragment()
    }
}