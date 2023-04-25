package com.unaerp.trabalhoandroid.fragments

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.unaerp.trabalhoandroid.R

class AnunciarVagaFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_anunciar_vaga, container, false)

        val dateInicioEditText = view.findViewById<TextInputEditText>(R.id.dateInicioEditText)
        val dateFinalEditText = view.findViewById<TextInputEditText>(R.id.dateFinalEditText)

        val calendar = Calendar.getInstance()

        val datePickerInicio = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dateInicioEditText.setText(SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

        val datePickerFinal = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
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


        val submitBotao =  view.findViewById<MaterialButton>(R.id.submitBotao)

        submitBotao.setOnClickListener {
        }


        return view
    }
}
