package com.almaki.employeeabsense.helper

import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Validate(val view: View, val context: FragmentActivity) {

    /** ==========================================================================================
     * @param employee_no phone entered
     * @param employee_password password entered
     * */
    fun validateLogin(employee_no: String, employee_password: String) : Boolean{
        return if(!TextUtils.isEmpty(employee_no) && !TextUtils.isEmpty(employee_password)){
            true
        }else {
            MakaniSnackbar.showMsg(view, context, "Please insert data", "OK", android.R.color.holo_red_light)
            false
        }
    }

    /** ==========================================================================================
     * @param makani_phone set the current phone from input field
     * @return string of valid phone
     * */
    fun validatePhone(makani_phone: String): String {
        val makani_phone_validate: String
        if (makani_phone.startsWith("+218")) {
            makani_phone_validate = makani_phone
        } else if(makani_phone.startsWith("0")){
            makani_phone_validate = "+218" + makani_phone.substring(1)
        }  else {
            makani_phone_validate = "+218$makani_phone"
        }
        return makani_phone_validate
    }
    /** ==========================================================================================
     * @param inputs take a list of EditText
     * @param materialButton take submit btn of current form and set state enable depends on fields is empty or not
     * */

    fun validateAllFields(inputs : List<TextInputEditText>, inputLayouts : List<TextInputLayout>, materialButton : MaterialButton) {
        // todo fix to check all inputs not only one
        for (input in inputs){
            input.doOnTextChanged { inputText, _, _, count ->
                if(inputText.toString() != "" && inputText != null){
                    /** -------------------------------------------------------------------------------- */
                    // check if button is enabled or not
                    if(!materialButton.isEnabled){
                        materialButton.isEnabled = true
                    }
                    /** -------------------------------------------------------------------------------- */
                }else {
                    /** -------------------------------------------------------------------------------- */
                    // check if button is enabled or not
                    if(materialButton.isEnabled){
                        materialButton.isEnabled = false
                    }
                    /** -------------------------------------------------------------------------------- */
                }
            }

            for (inputLayout in inputLayouts){
                input.doOnTextChanged { inputText, _, _, count ->
                    if(inputText.toString() != "" && inputText != null){
                        /** -------------------------------------------------------------------------------- */
                        inputLayout.boxStrokeWidth = 3

                        Log.e("input layouts", "NOT EMPTY")
                        /** -------------------------------------------------------------------------------- */

                    }else {
                        /** -------------------------------------------------------------------------------- */
                        inputLayout.boxStrokeWidth =  0
                        Log.e("input layouts", " EMPTY")
                        /** -------------------------------------------------------------------------------- */
                    }
                }

            }

        }
    }
    /** ========================================================================================== */

}