package com.unaerp.trabalhoandroid.fragments

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.unaerp.trabalhoandroid.databinding.FragmentAnunciarVagaBinding

class AnunciarVagaFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentAnunciarVagaBinding.inflate(inflater, container, false)
        val view = binding.root

        val dateInicioEditText = binding.dateInicioEditText
        val dateFinalEditText = binding.dateFinalEditText

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


        binding.submitBotao.setOnClickListener {
        }


        return view
    }
}
