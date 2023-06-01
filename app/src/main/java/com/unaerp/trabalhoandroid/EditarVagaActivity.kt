package com.unaerp.trabalhoandroid

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unaerp.trabalhoandroid.databinding.EditarVagaBinding

class EditarVagaActivity : AppCompatActivity() {
    private lateinit var binding: EditarVagaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditarVagaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoVoltarEditarVaga.setOnClickListener {
            finish()
        }

        binding.botaoEditarVagaEmpresa.setOnClickListener {
            Toast.makeText(this, "Anuncio de vaga editado", Toast.LENGTH_SHORT).show()
            finish()
        }


        val dateInicioEditText = binding.dateInicioEditVagaInicio
        val dateFinalEditText = binding.dateFinalEditVagaFinal

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