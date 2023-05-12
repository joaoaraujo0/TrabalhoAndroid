package com.unaerp.trabalhoandroid

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class EditarVaga : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editar_vaga)

        val botaoCancelar = findViewById<MaterialButton>(R.id.botaoVoltarEditarVaga)
        val botaoEditar = findViewById<MaterialButton>(R.id.botaoEditarVagaEmpresa)

        botaoCancelar.setOnClickListener {
            finish()
        }

        botaoEditar.setOnClickListener {
            Toast.makeText(this, "Anuncio de vaga editado", Toast.LENGTH_SHORT).show()
            finish()
        }


        val dateInicioEditText =findViewById<TextInputEditText>(R.id.dateInicioEditVagaInicio)
        val dateFinalEditText = findViewById<TextInputEditText>(R.id.dateFinalEditVagaFinal)

        val calendar = Calendar.getInstance()

        val datePickerInicio = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dateInicioEditText.setText(SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        val datePickerFinal = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dateFinalEditText.setText(SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        dateInicioEditText.setOnClickListener {
            datePickerInicio.show()
        }

        dateFinalEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                datePickerFinal.show()
                dateFinalEditText.clearFocus()
            }
        }



    }
}