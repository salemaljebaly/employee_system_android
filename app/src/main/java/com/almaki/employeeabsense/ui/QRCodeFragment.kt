package com.almaki.employeeabsense.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.almaki.employeeabsense.R
import com.almaki.employeeabsense.helper.HandleUI
import kotlinx.android.synthetic.main.fragment_q_r_code.*


class QRCodeFragment : Fragment() {

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

        btn_come_at.setOnClickListener{
            val intent = Intent(requireContext().applicationContext,
                Scanner::class.java)
            intent.putExtra("state", 0)
            startActivity(intent)
        }

        btn_leave_at.setOnClickListener{
            val intent = Intent(requireContext().applicationContext,
            Scanner::class.java)
            intent.putExtra("state", 1)
            startActivity(intent)
        }

        HandleUI.setToolBarFunctions(
            requireActivity(), getString(R.string.attendance_registration), true,
            toolbarVisible = true
        )
    }
    /** ========================================================================================= */


}