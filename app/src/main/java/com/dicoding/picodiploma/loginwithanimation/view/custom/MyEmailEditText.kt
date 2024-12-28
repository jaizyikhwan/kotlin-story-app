package com.dicoding.picodiploma.loginwithanimation.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class MyEmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    private var errorMessage: String = "Email tidak valid"

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Tidak ada aksi sebelum teks berubah
            }

            override fun onTextChanged(char: CharSequence, start: Int, before: Int, count: Int) {
                // validasi input email
                if (char.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(char).matches()) {
                    setError("Email harus dalam format yang valid", null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Tidak ada aksi setelah teks berubah
            }
        })
    }
}