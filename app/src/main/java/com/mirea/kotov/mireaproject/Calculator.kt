package com.mirea.kotov.mireaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Calculator.newInstance] factory method to
 * create an instance of this fragment.
 */
class Calculator : Fragment() {

    //region Объявление глобальных переменных

    private var leftNumber: Double? = null
    private var rightNumber: Double? = null
    private var operator: String? = null

    private var textView: TextView? = null
    private var button0: Button? = null
    private var button1: Button? = null
    private var button2: Button? = null
    private var button3: Button? = null
    private var button4: Button? = null
    private var button5: Button? = null
    private var button6: Button? = null
    private var button7: Button? = null
    private var button8: Button? = null
    private var button9: Button? = null
    private var buttonPlus: Button? = null
    private var buttonMinus: Button? = null
    private var buttonMulti: Button? = null
    private var buttonDivide: Button? = null
    private var buttonPoint: Button? = null
    private var buttonEqual: Button? = null
    private var buttonClear: Button? = null

    //endregion

    //region Вспомогательные методы

    private fun onNumberClick(button: Button?){
        var formula = textView?.text.toString()
        formula += button?.text.toString()
        textView?.text = formula
    }

    private fun onSymbolClick(button: Button?){
        leftNumber = textView?.text.toString().toDouble()
        operator = button?.text.toString()
        textView?.text = null
    }

    private fun onEqualClick(){
        rightNumber = textView?.text?.toString()?.toDouble()

        var result: Double = 0.0

        when(operator){
            "+" -> result = leftNumber?.plus(rightNumber!!)!!
            "-" -> result = leftNumber?.minus(rightNumber!!)!!
            "*" -> result = leftNumber?.times(rightNumber!!)!!
            "/" -> result = leftNumber?.div(rightNumber!!)!!
            null -> {}
        }

        textView?.text = result.toString()
    }

    private fun onClearClick(){
        leftNumber = null
        rightNumber = null
        operator = null

        textView?.text = ""
    }

    //endregion


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_nav_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region Объявление View

        textView = view?.findViewById(R.id.textView3)
        button0 = view?.findViewById(R.id.button0)
        button1 = view?.findViewById(R.id.button1)
        button2 = view?.findViewById(R.id.button2)
        button3 = view?.findViewById(R.id.button3)
        button4 = view?.findViewById(R.id.button4)
        button5 = view?.findViewById(R.id.button5)
        button6 = view?.findViewById(R.id.button6)
        button7 = view?.findViewById(R.id.button7)
        button8 = view?.findViewById(R.id.button8)
        button9 = view?.findViewById(R.id.button9)
        buttonPlus = view?.findViewById(R.id.buttonPlus)
        buttonMinus = view?.findViewById(R.id.buttonMinus)
        buttonMulti = view?.findViewById(R.id.buttonMulti)
        buttonDivide = view?.findViewById(R.id.buttonDivide)
        buttonPoint = view?.findViewById(R.id.buttonPoint)
        buttonEqual = view?.findViewById(R.id.buttonEqual)
        buttonClear = view?.findViewById(R.id.buttonClear)

        //endregion

        //region Добавление обработчиков

        button0?.setOnClickListener{onNumberClick(button0)}
        button1?.setOnClickListener{onNumberClick(button1)}
        button2?.setOnClickListener{onNumberClick(button2)}
        button3?.setOnClickListener{onNumberClick(button3)}
        button4?.setOnClickListener{onNumberClick(button4)}
        button5?.setOnClickListener{onNumberClick(button5)}
        button6?.setOnClickListener{onNumberClick(button6)}
        button7?.setOnClickListener{onNumberClick(button7)}
        button8?.setOnClickListener{onNumberClick(button8)}
        button9?.setOnClickListener{onNumberClick(button9)}
        buttonPoint?.setOnClickListener{onNumberClick(buttonPoint)}

        buttonPlus?.setOnClickListener{onSymbolClick(buttonPlus)}
        buttonMinus?.setOnClickListener{onSymbolClick(buttonMinus)}
        buttonMulti?.setOnClickListener{onSymbolClick(buttonMulti)}
        buttonDivide?.setOnClickListener{onSymbolClick(buttonDivide)}

        buttonEqual?.setOnClickListener{onEqualClick()}

        buttonClear?.setOnClickListener{onClearClick()}

        //endregion

    }

    companion object {
        @JvmStatic
        fun newInstance() = Calculator()
    }
}