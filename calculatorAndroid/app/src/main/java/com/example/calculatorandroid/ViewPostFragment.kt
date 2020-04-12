package com.example.calculatorandroid

import android.annotation.TargetApi
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_view_post.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList

class ViewPostFragment : Fragment(), OnBackPressed {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        //here is your list array
        val tapedPostId = bundle!!.getInt("tapedPostId")
        postImageView.setImageURI(Uri.parse(PostListFragment.postList[tapedPostId].imageUri))
        titleTextView.setText(PostListFragment.postList[tapedPostId].title)
        descriptionTextView.setText(PostListFragment.postList[tapedPostId].description)
        descriptionTextView.setMovementMethod(ScrollingMovementMethod())
        deletePostButton.setOnClickListener {
            PostListFragment.postList.removeAt(tapedPostId)
            saveToJSON(PostListFragment.postList, "newPostsList")
            var postListFragment  = PostListFragment()
            var fragmentManager : FragmentManager? = getFragmentManager()
            var fragmentTransaсtion : FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaсtion.replace(R.id.host, postListFragment)
            fragmentTransaсtion.commit()
        }
    }
    private fun saveToJSON(list : ArrayList<PostCard>, filename : String)
    {
        var sharedPreferences = activity!!.getSharedPreferences(filename, Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        var gson = Gson()
        var jsonString : String = gson.toJson(list)
        jsonString = encryptString(jsonString)
        editor.putString("posts", jsonString)
        editor.apply()
    }
    @TargetApi(Build.VERSION_CODES.O)
    private fun encryptString(string : String) :String
    {
        val key : SecretKeySpec = SecretKeySpec("asd543poi65kl23m".toByteArray(charset("UTF8")), "AES")
        val input = string.toByteArray(charset("UTF8"))
        synchronized(Cipher::class.java)
        {
            val cipher : Cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val cipherText = ByteArray(cipher.getOutputSize(input.size))
            var ctLength = cipher.update(
                input, 0, input.size,
                cipherText, 0
            )
            ctLength += cipher.doFinal(cipherText, ctLength)
            return String(Base64.getEncoder().encode(cipherText))
        }
    }
    override fun onBackPressed() {
        var postListFragment  = PostListFragment()
        var fragmentManager : FragmentManager? = getFragmentManager()
        var fragmentTransaсtion : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaсtion.replace(R.id.host, postListFragment)
        fragmentTransaсtion.commit()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_post, container, false)
    }

}
