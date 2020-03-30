package com.example.calculatorandroid

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_host.*
import java.lang.Exception


class SignUpFragment : Fragment(), OnBackPressed {
    override fun onBackPressed() {
        var signInFragment  = SignInFragment()
        var fragmentManager : FragmentManager? = getFragmentManager()
        var fragmentTransaсtion : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaсtion.replace(R.id.host, signInFragment)
        fragmentTransaсtion.commit()
    }
    // TODO: Rename and change types of parameters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState)

        var rootView : View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val trName: EditText = rootView.findViewById(R.id.trName)
        val trUsername: EditText = rootView.findViewById(R.id.trUserName)
        val trPassword: EditText = rootView.findViewById(R.id.trPassword)
        val trPasswordRepeat: EditText = rootView.findViewById(R.id.trRepeatPassword)
        val bRegister: Button = rootView.findViewById(R.id.bRegister)
        val errorMessage : TextView = rootView.findViewById(R.id.errorMessage)

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

                var signInFragment  = SignInFragment()
                var args : Bundle = Bundle()
                args.putString("Username", trUsername.text.toString())
                args.putString("Password", trPassword.text.toString())
                signInFragment.setArguments(args)

                var fragmentManager : FragmentManager? = getFragmentManager()
                var fragmentTransaсtion : FragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaсtion.replace(R.id.host, signInFragment)
                fragmentTransaсtion.commit()

                //val registerIntent = Intent(this@SignUpFragment.activity, SignInFragment::class.java)
                //registerIntent.putExtra("Username", trUsername.text.toString())
                //registerIntent.putExtra("Password", trPassword.text.toString())
                //startActivity(registerIntent)
            }
            catch (ex : Exception)
            {
                errorMessage.setText(ex.toString().substring(21, ex.toString().length))
            }
        }
        return rootView
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
