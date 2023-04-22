package com.unaerp.trabalhoandroid

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class CadastroVaga : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cadastra_anuncio)

        val dateInicioEditText = findViewById<TextInputEditText>(R.id.dateInicioEditText)
        val dateFinalEditText = findViewById<TextInputEditText>(R.id.dateFinalEditText)

        val calendar = Calendar.getInstance()

        val datePickerInicio = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dateInicioEditText.setText(SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        val datePickerFinal = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dateFinalEditText.setText(SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        dateInicioEditText.setOnClickListener {
            datePickerInicio.show()
        }

        dateFinalEditText.setOnFocusChangeListener {_,hasFocus ->
            if (hasFocus) {
                datePickerFinal.show()
                dateFinalEditText.clearFocus()
            }
        }
    }

}