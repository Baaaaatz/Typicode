package com.batzalcancia.core.helpers

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.batzalcancia.core.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

/**
 * Add dropdown items on AutoCompleteTextView
 **/
fun AutoCompleteTextView.setDropdown(context: Context, list: List<String>) {
    val adapter = ArrayAdapter(
        context,
        android.R.layout.simple_spinner_dropdown_item,
        list
    )

    this.setAdapter(adapter)
}

/**
 * Observe textChanges in editText
 **/
@ExperimentalCoroutinesApi
fun EditText.textChangesFlow() = callbackFlow<String> {
    val listener = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            offer(p0.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    }

    addTextChangedListener(listener)

    awaitClose {
        removeTextChangedListener(listener)
    }
}
