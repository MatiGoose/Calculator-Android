package com.example.calculatorandroid

import java.util.*
import android.R.string
import android.app.Notification
import android.os.Message
import android.view.textclassifier.ConversationActions
import java.lang.Exception
import java.math.BigDecimal
import java.text.DecimalFormat


class ReversePolishNotation {

    private fun convertInfixToRPN(infixNotation :Array<String>) : Queue<String>
    {
        var prededence :MutableMap<String, Int> = HashMap()
        prededence.put("^", 6)
        prededence.put("√", 6)
        prededence.put("s", 7)
        prededence.put("c", 7)
        prededence.put("o", 7)
        prededence.put("÷", 5)
        prededence.put("×", 5)
        prededence.put("+", 4)
        prededence.put("-", 4)
        prededence.put("(", 0)

        var queue:Queue<String> = LinkedList()
        var stack:Stack<String> = Stack()

        for(token : String in infixNotation)
        {
            if("(".equals(token))
            {
                stack.push(token)
                continue
            }
            if(")".equals(token))
            {
                while (!"(".equals(stack.peek()))
                    queue.add((stack.pop()))
                stack.pop()
                continue
            }
            if(prededence.containsKey(token.toString()))
            {
                while (!stack.isEmpty() && (prededence.get(token.toString())!! <= prededence.get(stack.peek().toString())!!))
                {
                    queue.add(stack.pop())
                }
                stack.push(token)
                continue
            }
            if(isNumber(token))
            {
                queue.add(token)
                continue
            }
        }
        while (!stack.isEmpty())
        {
            queue.add((stack.pop()))
        }
        return queue

    }
    fun isNumber(string : String) :Boolean
    {
        try
        {
            string.toDouble()
            return true
        }
        catch (ex : Exception)
        {
            return false
        }
    }
    fun calculate(expression: Array<String>) : Double
    {
        var stack : Stack<String> = Stack()
        var queue : Queue<String> = LinkedList(convertInfixToRPN(expression))
        var string : String = queue.remove().toString()
        while (queue.count() >= 0)
        {
            if(isNumber(string))
            {
                stack.push(string)
                if(queue.count() <= 0)
                    break
                string = queue.remove().toString()
            }
            else
            {
                var summ : Double = 0.0
                try
                {
                    when(string)
                    {
                        "+" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            val b : Double = stack.pop().toDouble()
                            summ = a + b;
                        }

                        "-" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            val b : Double = stack.pop().toDouble()
                            summ = b - a;
                        }

                        "×" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            val b : Double = stack.pop().toDouble()
                            summ = b * a;
                        }

                        "÷" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            val b : Double = stack.pop().toDouble()
                            if(a.toString().equals("0.0"))
                                throw Exception("DivisionByZero")
                            summ = b / a
                        }
                        "^" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            val b : Double = stack.pop().toDouble()
                            summ = Math.pow(b, a);
                        }
                        "√" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            if(a<0)
                                throw Exception("SqrtError")
                            summ = Math.sqrt(a)
                        }
                        "s" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            summ = Math.sin(a)
                        }
                        "c" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            summ = Math.cos(a)
                        }
                        "o" ->
                        {
                            val a : Double = stack.pop().toDouble()
                            if(a.toString().equals("0") || a.toString().equals("0.0"))
                                summ = a
                            else
                                summ = a *(-1)
                        }
                    }
                }
                catch (e:Exception)
                {
                    throw e
                }
                stack.push(summ.toString())
                if(queue.count() >0)
                    string = queue.remove().toString()
                else
                    break
            }
        }
        return stack.pop().toDouble()
    }
}