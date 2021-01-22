package com.example.pixiti.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pixiti.R
import com.example.pixiti.databinding.FragmentFilterBinding
import com.example.pixiti.utils.select
import com.example.pixiti.utils.unSelect
import com.google.android.flexbox.FlexboxLayout

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

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
        initList(resources.getStringArray(R.array.image_type), binding.flexboxLayoutImageType)
        initList(resources.getStringArray(R.array.image_filter), binding.flexboxLayoutFilter)
        initList(
            resources.getStringArray(R.array.image_orientation),
            binding.flexboxLayoutOrientation
        )
        initList(resources.getStringArray(R.array.safe_search), binding.flexboxLayoutSafeSearch)
    }

    private fun initList(array: Array<String>, layout: FlexboxLayout) {
        val listOfFilterTextView = mutableListOf<TextView>()
        binding.apply {
            array.forEach { filterType ->
                val textViewFilter =
                    LayoutInflater.from(context).inflate(
                        R.layout.item_filter,
                        layout,
                        false
                    ) as TextView
                textViewFilter.text = filterType
                layout.addView(textViewFilter)
                listOfFilterTextView.add(textViewFilter)
                textViewFilter.setOnClickListener { selectedTextView ->
                    if (!selectedTextView.isSelected) {
                        listOfFilterTextView.forEach { textView ->
                            textView.unSelect()
                        }
                        selectedTextView.select()
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

