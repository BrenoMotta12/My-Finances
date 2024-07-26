package com.brenomotta.myfinances.service.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import java.text.NumberFormat
import java.util.Locale

class FinancesFormatter {

    companion object {
        private val localeBR = Locale("pt", "BR")
        private val currencyFormatter = NumberFormat.getCurrencyInstance(localeBR)

        // Transforma o valor formatado para visualização em um double
        fun maskToDouble(value: String): Double {
            val cleanString = value.replace(Regex("[R$,.]"), "").trim()

            return if (cleanString.isNotEmpty()) {
                cleanString.toDouble() / 100 // Divide por 100 se necessário
            } else {
                0.0 // Valor padrão se não houver número válido
            }
        }

        // Formara um valor Double para a visualização do usuário
        fun maskMonetaryValue(editText: EditText) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    editText.removeTextChangedListener(this)

                    val cleanString = s.toString().replace(Regex("[R$,.]"), "").trim()

                    if (cleanString.isNotEmpty()) {
                        val parsed = cleanString.toDouble()
                        val formatted = currencyFormatter.format(parsed / 100)

                        editText.setText(formatted)
                        editText.setSelection(formatted.length)
                    }
                    editText.addTextChangedListener(this)
                }
            })
        }

    }
}