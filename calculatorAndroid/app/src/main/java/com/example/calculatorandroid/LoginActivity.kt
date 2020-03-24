package com.example.calculatorandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var tUsername : EditText = findViewById(R.id.tUsername)
        var tPassword : EditText = findViewById(R.id.tPassword)
        val bSignIn : Button = findViewById(R.id.bSignIn)
        val bSignUp : Button = findViewById(R.id.bSignUp)
        val loginErrorMessage : TextView = findViewById(R.id.loginErrorMessage)

        bSignUp.setOnClickListener {
            val registerIntent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(registerIntent)
        }

        tUsername.setText(intent.getStringExtra("Username"))
        tPassword.setText(intent.getStringExtra("Password"))

        val userLogin = intent.getStringExtra("Username")
        val userPassword = intent.getStringExtra("Password")

        bSignIn.setOnClickListener {
            if( (userLogin != null || userPassword != null)
              &&((tUsername.text.toString().equals(userLogin.toString()) && tPassword.text.toString().equals(userPassword.toString()))
              ||(tUsername.text.toString().equals("admin") && tPassword.text.toString().equals("admin"))))
            {
                val registerIntent = Intent(this@LoginActivity, MainActivity::class.java)
                registerIntent.putExtra("Username", tUsername.text.toString())
                registerIntent.putExtra("Password", tPassword.text.toString())
                startActivity(registerIntent)
            }
            else
            {
                loginErrorMessage.setText("Incorrect login or password.")
            }
        }
    }
}
