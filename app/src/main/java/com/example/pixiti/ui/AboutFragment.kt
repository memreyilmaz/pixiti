package com.example.pixiti.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.pixiti.databinding.FragmentDialogAboutBinding

class AboutFragment : DialogFragment() {

    private var binding: FragmentDialogAboutBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogAboutBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireContext())
            .setView(binding?.root)
            .create()
    }

    companion object {
        const val TAG = "AboutFragment"
        fun newInstance() = AboutFragment()
    }
}