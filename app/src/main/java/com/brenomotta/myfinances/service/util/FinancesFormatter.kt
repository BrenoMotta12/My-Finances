package com.brenomotta.myfinances.service.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.brenomotta.myfinances.service.constants.FinancesConstants
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class FinancesFormatter {

    companion object {
        private val localeBR = Locale("pt", "BR")
        private val currencyFormatter = NumberFormat.getCurrencyInstance(localeBR)
        private var dateFormat = SimpleDateFormat(FinancesConstants.DATE.DATE_FORMAT)

        fun intToDateString(year: Int, month: Int, dayOfMonth: Int): String {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            return dateFormat.format(calendar.time)
        }

        // Converte a data do banco de dados diretamente para um Local Date
        fun toDate(dateString: String): LocalDate {
            val formatter = DateTimeFormatter.ofPattern(FinancesConstants.DATE.DATE_FORMAT)
            return LocalDate.parse(dateString, formatter)
        }

        // Converte Local date para String
        fun toString(date: LocalDate): String {
            val formatter = DateTimeFormatter.ofPattern(FinancesConstants.DATE.DATE_FORMAT)
            return date.format(formatter)
        }

        // Transforma o valor formatado para visualização em um double
        fun monetaryToDouble(value: String): Double {
            val cleanString = value.replace(Regex("[R$,.]"), "").trim()

            return if (cleanString.isNotEmpty()) {
                cleanString.toDouble() / 100 // Divide por 100 se necessário
            } else {
                0.0 // Valor padrão se não houver número válido
            }
        }

        //         Formata o editText para a visualização do usuário
        fun maskMonetaryValue(editText: EditText) {
            editText.addTextChangedListener(object : TextWatcher {
                private var isEditing = false

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (isEditing) return

                    var firstUpdate = true
                    isEditing = true


                    val cleanString = s.toString().replace(Regex("[R$,.]"), "").trim()

                    if (cleanString.isNotEmpty()) {
                        val parsed = cleanString.toDouble() / 100
                        val formatted = currencyFormatter.format(parsed)

                        editText.setText(formatted)
                        editText.setSelection(formatted.length)
                    } else {
                        editText.setText("")
                        editText.setSelection(0)
                    }

                    isEditing = false
                }
            })
        }


        fun maskMonetaryValue(value: Double): String {
            // Cria um formatador para o Brasil
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            // Formata o valor e retorna como string
            return numberFormat.format(value)
        }

    }

}