package com.brenomotta.myfinances.service.util

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.brenomotta.myfinances.service.constants.FinancesConstants
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
        private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        fun intToDateString(year: Int, month: Int, dayOfMonth: Int): String  {
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            return dateFormat.format(calendar.time)
        }

        @TypeConverter
        fun stringToDate(value: String?): LocalDate? {
            return value?.let { LocalDate.parse(it, formatter) }
        }

        @TypeConverter
        fun dateToString(date: LocalDate?): String? {
            return date?.format(formatter)
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

        // Formata um valor Double para a visualização do usuário
        fun maskMonetaryValue(editText: EditText) {
            editText.addTextChangedListener(object : TextWatcher {
                private var isEditing = false

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (isEditing) return

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
            var cleanString = value.toString().replace(Regex("[R$,.]"), "").trim()

            if (cleanString.isNotEmpty()) {
                val parsed = cleanString.toDouble()

                cleanString = currencyFormatter.format(parsed / 100)

            }
            return cleanString
        }

    }
}