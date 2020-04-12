package com.example.calculatorandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R.attr.fragment
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T







class HostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)
        //android:name="com.example.calculatorandroid.SignInFragment"
        var signInFragment  = SignInFragment()
        var fragmentManager : FragmentManager? = supportFragmentManager
        var fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.host, signInFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    override fun onBackPressed() {
        tellFragments()
    }
    private fun tellFragments() {

        val fm = supportFragmentManager
        val fragments = supportFragmentManager.fragments
        var onBackPressedListener : OnBackPressed? = null

        for (f in fragments) {
            if (f != null && f is OnBackPressed)
            {
                onBackPressedListener = f as OnBackPressed
                break
            }
        }
        if(onBackPressedListener != null)
        {
            onBackPressedListener.onBackPressed()
        }
        else
        {
            super.onBackPressed()
        }
    }
}
