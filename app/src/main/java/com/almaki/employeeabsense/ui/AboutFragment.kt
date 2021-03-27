package com.almaki.employeeabsense.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.almaki.employeeabsense.MainActivity
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.helper.HandleUI

class AboutFragment : Fragment() {

    /** ========================================================================================= */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    /** ========================================================================================= */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
    /** ========================================================================================= */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HandleUI.setToolBarFunctions(requireActivity(), getString(R.string.about), true,
            toolbarVisible = true
        )
    }
    /** ========================================================================================= */
}