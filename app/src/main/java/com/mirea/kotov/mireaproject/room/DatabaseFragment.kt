package com.mirea.kotov.mireaproject.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mirea.kotov.mireaproject.R
import com.mirea.kotov.mireaproject.databinding.FragmentDatabaseBinding
import com.mirea.kotov.mireaproject.databinding.FragmentGalleryBinding
import java.lang.NumberFormatException

class DatabaseFragment : Fragment() {
    private lateinit var binding: FragmentDatabaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDatabaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with((binding)) {

        val viewModel: DatabaseViewModel =
            ViewModelProvider(requireActivity())[DatabaseViewModel::class.java]
        viewModel.getData().observe(viewLifecycleOwner)
        { data ->
            run {
                tvEmployees.text = data
            }
        }

        //region Event Handlers

        buttonEmployee.post {
            buttonEmployee.setOnClickListener {
                try {
                    val name = etEmployeeName.text.toString()
                    val salary = etEmployeeSalary.text.toString().toInt()

                    viewModel.insertData(name, salary)

                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        requireContext(),
                        "Неверный формат зарплаты", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        //endregion

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = DatabaseFragment()
    }
}
