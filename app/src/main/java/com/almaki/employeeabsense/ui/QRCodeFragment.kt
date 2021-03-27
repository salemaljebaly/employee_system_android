package com.almaki.employeeabsense.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.helper.HandleUI
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import kotlinx.android.synthetic.main.fragment_q_r_code.*


class QRCodeFragment : Fragment() {

    lateinit var codeScanner : CodeScanner
    lateinit var codeScannerView : CodeScannerView
    /** ========================================================================================= */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        /** ----------------------------------------------------------------------------------- */


    }
    /** ========================================================================================= */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_code, container, false)
    }
    /** ========================================================================================= */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scan_code.setOnClickListener{
            startActivity(
                Intent(
                    requireContext().applicationContext,
                    Scanner::class.java
                )
            )
        }

        HandleUI.setToolBarFunctions(
            requireActivity(), getString(R.string.attendance_registration), true,
            toolbarVisible = true
        )


    }
    /** ========================================================================================= */


}