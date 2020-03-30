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

class SignInFragment : Fragment(), OnBackPressed{
    override fun onBackPressed() {
        //var signUpFragment  = SignUpFragment()
        //var fragmentManager : FragmentManager? = getFragmentManager()
        //var fragmentTransaсtion : FragmentTransaction = fragmentManager!!.beginTransaction()
        //fragmentTransaсtion.replace(R.id.host, signUpFragment)
        //fragmentTransaсtion.commit()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView : View = inflater.inflate(R.layout.fragment_sign_in, container, false)
        var tUsername : EditText = rootView.findViewById(R.id.tUsername)
        var tPassword : EditText = rootView.findViewById(R.id.tPassword)
        val bSignIn : Button = rootView.findViewById(R.id.bSignIn)
        val bSignUp : Button = rootView.findViewById(R.id.bSignUp)
        val loginErrorMessage : TextView = rootView.findViewById(R.id.loginErrorMessage)

        bSignUp.setOnClickListener {
            var signUpFragment  = SignUpFragment()
            var fragmentManager : FragmentManager? = getFragmentManager()
            var fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.host, signUpFragment)
            fragmentTransaction.commit()

        }


        var userLogin : String? = null
        var userPassword : String? = null

        if(arguments != null)
        {
            tPassword.setText(arguments!!.getString("Password", "admin"))
            tUsername.setText(arguments!!.getString("Username", "admin"))

            userLogin = arguments!!.getString("Username", "admin")
            userPassword = arguments!!.getString("Password", "admin")

        }
        bSignIn.setOnClickListener {
            if((tUsername.text.toString().equals("admin") && tPassword.text.toString().equals("admin")
                        || ((userLogin != null && userPassword != null)
                        && (tUsername.text.toString().equals(userLogin.toString()) && tPassword.text.toString().equals(userPassword.toString())))))
            {
                //var calculatorFragment  = CalculatorFragment()
                var postListFragment = PostListFragment()
                var args : Bundle = Bundle()
                args.putString("Username", userLogin)
                args.putString("Password", userPassword)
                postListFragment.setArguments(args)
                var fragmentManager : FragmentManager? = getFragmentManager()
                var fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()
                //fragmentTransaction.replace(R.id.host, calculatorFragment)
                fragmentTransaction.replace(R.id.host, postListFragment, "postListFragment")
                fragmentTransaction.commit()

            }
            else
            {
                loginErrorMessage.setText("Incorrect login or password.")
            }
        }
        return rootView
    }


}
