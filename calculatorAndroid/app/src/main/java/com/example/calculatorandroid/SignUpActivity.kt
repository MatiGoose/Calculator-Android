package com.example.calculatorandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val trName: EditText = findViewById(R.id.trName)
        val trUsername: EditText = findViewById(R.id.trUserName)
        val trPassword: EditText = findViewById(R.id.trPassword)
        val trPasswordRepeat: EditText = findViewById(R.id.trRepeatPassword)
        val bRegister: Button = findViewById(R.id.bRegister)
        val errorMessage : TextView = findViewById(R.id.errorMessage)

        bRegister.setOnClickListener {
            try
            {
                if(!validPassword(trPassword.text.toString()))
                    throw Exception("Password must have at least 1 numeric character, [a-z], [A-Z]. As minimus 8 characters.")
                if(!trPassword.text.toString().equals(trPasswordRepeat.text.toString()))
                    throw Exception("Passwords must be the same.")
                if(trName.text.toString().count() >= 15 || trName.text.toString().count() <= 2)
                    throw Exception("Name must have more than 2 and less than 15 characters.")
                if(trUsername.text.toString().count() >= 15 || trUsername.text.toString().count() <= 2)
                    throw Exception("Username must have more than 2 and less than 15 characters.")

                val registerIntent = Intent(this@SignUpActivity, LoginActivity::class.java)
                registerIntent.putExtra("Username", trUsername.text.toString())
                registerIntent.putExtra("Password", trPassword.text.toString())
                startActivity(registerIntent)
            }
            catch (ex : Exception)
            {
                errorMessage.setText(ex.toString().substring(21, ex.toString().length))
            }
        }

    }
    fun validPassword(password :String) : Boolean
    {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$".toRegex()
        if (password.matches(pattern))
            return true;
        else
            return false;
    }

}
