package com.example.calculatorandroid

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    var expressionHeight :Int = 0
    var resultHeight :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expressionHeight = tvExpression.layoutParams.height
        resultHeight = tvResult.layoutParams.height
        var memoryValue = 0.0

        //Numbers

        tvOne.setOnClickListener { appendOnExpression("1", true) }
        tvTwo.setOnClickListener { appendOnExpression("2", true) }
        tvThree.setOnClickListener { appendOnExpression("3", true) }
        tvFour.setOnClickListener { appendOnExpression("4", true) }
        tvFive.setOnClickListener { appendOnExpression("5", true) }
        tvSix.setOnClickListener { appendOnExpression("6", true) }
        tvSeven.setOnClickListener { appendOnExpression("7", true) }
        tvEight.setOnClickListener { appendOnExpression("8", true) }
        tvNine.setOnClickListener { appendOnExpression("9", true) }
        tvZero.setOnClickListener { appendOnExpression("0", true) }
        tvOpenBracket.setOnClickListener { appendOnExpression("(", true) }
        tvCloseBracket.setOnClickListener { appendOnExpression(")", true) }

        tvPoint.setOnClickListener { appendOnExpression(".", false) }
        //Operators
        tvPlus.setOnClickListener { appendOnExpression("+", false) }
        tvMinus.setOnClickListener { appendOnExpression("-", false) }
        tvMultiply.setOnClickListener { appendOnExpression("×", false) }
        tvDivision.setOnClickListener { appendOnExpression("÷", false) }
        tvPow.setOnClickListener { appendOnExpression("^", false) }

        tvSin.setOnClickListener { appendOnExpression("sin(", false) }
        tvCos.setOnClickListener { appendOnExpression("cos(", false) }
        tvSqrt.setOnClickListener { appendOnExpression("√(", true) }

        tvEquals.setOnClickListener {
            if(tvExpression.text.isNotEmpty())
            {
                var openBrackets : Int = 0
                var closeBrackets : Int = 0
                for(ch in tvExpression.text)
                {
                    if(ch.equals('('))
                        openBrackets ++
                    if(ch.equals((')')))
                        closeBrackets++
                }
                if(openBrackets != closeBrackets)
                    for(i in 0..openBrackets-closeBrackets-1)
                        tvExpression.append(")")
                if(tvExpression.text.get(tvExpression.text.length-1).equals('.'))
                    tvExpression.text = tvExpression.text.substring(0, tvExpression.text.length-1)
                var result  = ReversePolishNotation()
                var parsedExpression = parseExpression(tvExpression.text.toString())
                try
                {
                    var value : Double = result.calculate(parsedExpression)
                    value = Math.floor(value*10000000000000000.0)/10000000000000000.0
                    //value = Math.round(value*100000000000000.0)/100000000000000.0
                    tvResult.text = value.toString()
                }
                catch (ex:Exception)
                {
                    tvResult.text = "Error"
                }
            }
        }

        //Clears
        tvClearEverything.setOnClickListener {
            tvExpression.text = ""
            tvResult.text = ""}
        tvDelete.setOnClickListener {
            val expression = tvExpression.text;
            if(expression.isNotEmpty())
            {
                if(tvExpression.text.length >= 4 && (tvExpression.text.get(tvExpression.text.length - 2) == 'n' || tvExpression.text.get(tvExpression.text.length - 2) == 's'))
                {
                    tvExpression.text = expression.substring(0, expression.length-4)
                }
                else
                {
                    tvExpression.text = expression.substring(0, expression.length-1)
                }
            }
            tvResult.text = ""
        }

        //Memory Operators
        tvMemoryPlus.setOnClickListener { if(tvResult.text.isNotEmpty() && !tvResult.text.equals("Error") && !tvResult.text.equals("NaN") && !tvResult.text.equals("Infinity")) memoryValue += tvResult.text.toString().toDouble() }
        tvMemoryMinus.setOnClickListener { if(tvResult.text.isNotEmpty() && !tvResult.text.equals("Error") && !tvResult.text.equals("NaN") && !tvResult.text.equals("Infinity")) memoryValue -= tvResult.text.toString().toDouble() }
        tvMemoryClear.setOnClickListener { memoryValue = 0.0 }
        tvMemoryResult.setOnClickListener {
            tvExpression.text = ""
            tvExpression.append(memoryValue.toString())
            tvResult.text = ""}
    }
    fun appendFunction ( symbol: String)
    {
        if(tvResult.text.isNotEmpty())
        {
            tvExpression.text = tvResult.text
            tvResult.text = ""
        }
        if(tvExpression.text.length >= 1
            && (tvExpression.text.get(tvExpression.text.length - 1).toString() in "0123456789"
                    || tvExpression.text.get(tvExpression.text.length - 1).toInt().equals(".")
                    || tvExpression.text.get(tvExpression.text.length - 1).toInt().equals("-")))
            tvExpression.append("×")
        tvExpression.append(symbol)
    }
    fun appendOnExpression(symbol: String, canClear:Boolean)
    {
        if(tvResult.text.equals("Error") || tvResult.text.equals("NaN") || tvResult.text.equals("Infinity"))
            tvResult.text = ""
        if(symbol.equals("-"))
        {
            if(tvResult.text.isNotEmpty())
            {
                tvExpression.text = ""
                tvExpression.append(tvResult.text)
                tvResult.text = ""
            }
            else if(tvExpression.text.isEmpty() || tvExpression.text.get(tvExpression.text.length-1) in "+-÷×^")
                    tvExpression.append("(")
            tvExpression.append(symbol)
            return
        }
        if(symbol.equals(")"))
        {
            var openBrackets : Int = 0
            var closeBrackets : Int = 0
            for(ch in tvExpression.text)
            {
                if(ch.equals('('))
                    openBrackets ++
                if(ch.equals((')')))
                    closeBrackets++
            }
            if(openBrackets > closeBrackets)
                tvExpression.append(symbol)
            return
        }
        if(symbol.equals("sin("))
        {
            appendFunction(symbol)
            return
        }

        if(symbol.equals("cos("))
        {
            appendFunction(symbol)
            return
        }
        if(symbol.equals("√("))
        {
            appendFunction(symbol)
            return
        }
        if(canClear)
        {
            if(!tvExpression.text.isEmpty() && tvExpression.text.last().toString().equals(")"))
                return
            if(!tvExpression.text.isEmpty() && tvExpression.text.last() in "0123456789" && symbol.equals("("))
                return
            if(tvResult.text.isNotEmpty())
                tvExpression.text = ""
            tvResult.text = ""
            tvExpression.append(symbol)
        }
        else
        {
            if(tvExpression.text.isEmpty())
                return
            if(symbol.equals(".") && (tvExpression.text.contains(".") || tvResult.text.contains(".")))
                return
            if(tvExpression.text.last().equals('.') && symbol.toString() in "+-÷×^")
            {
                tvExpression.text = tvExpression.text.substring(0, tvExpression.text.length-1)
                tvExpression.append(symbol)
                return
            }
            if(tvExpression.text.last() in "(+-÷×^.")
                return

            if(tvResult.text.isNotEmpty())
                tvExpression.text = ""
            tvExpression.append(tvResult.text)
            tvExpression.append(symbol)
            tvResult.text = ""
        }


    }
    fun parseExpression(string:String) : Array<String>
    {
        var parsedString  = Array<String>(string.length){"it = $it"}
        var stringIndex: Int = 0
        var temp : CharArray = string.toCharArray()
        var i : Int = 0
        while(i < temp.size)
        {
            when(temp[i])
            {
                '1','2','3','4','5','6','7','8','9','0','.' ->
                {
                    var opposite : Boolean = false

                    var tempString :String = temp[i].toString()
                    i++
                    while (temp.count() > i && (temp[i] in "0123456789" || temp[i].equals('.')))
                    {
                        tempString += temp[i].toString()
                        i++
                    }
                    if(tempString.get(tempString.length-1).equals('.'))
                        tempString = tempString.substring(0, tempString.length - 1)

                    parsedString.set(stringIndex, tempString)
                    stringIndex++
                }
                '+', '-', '×','÷','√','^','(',')','s','c', 'E' ->
                {
                    if(temp[i].equals('-') && (i == 0 || temp[i-1].equals('(')))
                    {
                        parsedString.set(stringIndex, "o")
                        stringIndex++
                        i++

                    }
                    else if(temp[i].equals('s'))
                    {
                        parsedString.set(stringIndex, temp[i].toString())
                        stringIndex++
                        i += 3
                    }
                    else if(temp[i].equals('c'))
                    {
                        parsedString.set(stringIndex, temp[i].toString())
                        stringIndex++
                        i += 3
                    }
                    else if(temp[i].equals('E'))
                    {
                        parsedString.set(stringIndex, "×")
                        stringIndex++
                        parsedString.set(stringIndex, "10")
                        stringIndex++
                        parsedString.set(stringIndex, "^")
                        stringIndex++
                        i++
                        if(temp[i].equals('-'))
                        {
                            parsedString.set(stringIndex, "o")
                            stringIndex++
                            i++
                        }
                    }
                    else
                    {
                        parsedString.set(stringIndex, temp[i].toString())
                        stringIndex++
                        i++
                    }
                }
            }
        }
        var resizedParsedString  = Array<String>(stringIndex){"it = $it"}
        for(i in 0..stringIndex-1)
        {
            resizedParsedString.set(i, parsedString[i])
        }
        return resizedParsedString
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tvExpression.layoutParams.height = 120
            tvResult.layoutParams.height = 120
        } else {
            tvExpression.layoutParams.height = expressionHeight
            tvResult.layoutParams.height = resultHeight
        }
    }

    override fun onBackPressed()
    {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Do you want logout?")
        builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, which ->
            val registerIntent = Intent(this@MainActivity, LoginActivity::class.java)
            registerIntent.putExtra("Username", intent.getStringExtra("Username"))
            registerIntent.putExtra("Password", intent.getStringExtra("Password"))
            startActivity(registerIntent)
        })
        builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }
}

