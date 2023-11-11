package com.example.e2android.models

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat

class DecimalTextWatcher(private val editText: EditText) : TextWatcher {
    private val decimalFormat = DecimalFormat("#.##")

    override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(editable: Editable?) {
        editText.removeTextChangedListener(this)

        val input = editable.toString()

        if (input.isNotEmpty()) {
            try {
                val parsedValue = input.toDouble()
                val formattedValue = decimalFormat.format(parsedValue)
                editText.setText(formattedValue)
                editText.setSelection(formattedValue.length)
            } catch (e: NumberFormatException) {
                // Handle the case where the input is not a valid number
            }
        }

        editText.addTextChangedListener(this)
    }
}