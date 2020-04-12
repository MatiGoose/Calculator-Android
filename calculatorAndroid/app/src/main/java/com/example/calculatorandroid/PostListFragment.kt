package com.example.calculatorandroid

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_post_list.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.lang.reflect.Type
import java.nio.channels.FileChannel
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


@Suppress("DEPRECATION")
class PostListFragment : Fragment(), OnBackPressed {

    companion object
    {
        var postList = ArrayList<PostCard>()

    }
    private var stringBuilder:StringBuilder?=null
    private var postCardAdapter : rvAdapter? = null

    override fun onBackPressed() {
        var signInFragment  = SignInFragment()
        var fragmentManager : FragmentManager? = getFragmentManager()
        var fragmentTransaсtion : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaсtion.replace(R.id.host, signInFragment)
        fragmentTransaсtion.commit()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addPostButton.setOnClickListener {
            var addPostFragment = AddPostFragment()
            var fragmentManager : FragmentManager? = getFragmentManager()
            var fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.host, addPostFragment, "addPostFragment")
            fragmentTransaction.commit()
        }
        initRecyclerView()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_post_list, container, false)


        return rootView
    }

    private fun parseJSON(fileName: String): ArrayList<PostCard>
    {
        val sharedPreferences = activity!!.getSharedPreferences(fileName, MODE_PRIVATE)
        val gson = Gson()
        var jsonString : String = sharedPreferences.getString("posts", null).toString()
        if (jsonString.equals("null"))
            return ArrayList<PostCard>()
        jsonString = decryptString(jsonString)
        val type : Type = object : TypeToken<ArrayList<PostCard>>() {}.type
        val list : ArrayList<PostCard> = gson.fromJson(jsonString, type)
        return list
    }
    private fun decryptString(string : String) : String
    {
        val key : SecretKeySpec = SecretKeySpec("asd543poi65kl23m".toByteArray(charset("UTF8")), "AES")
        val input = Base64.decode(string.trim() { it <= ' '}.toByteArray(charset("UTF8")), 0)
        synchronized(Cipher::class.java) {
            val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
            cipher.init(Cipher.DECRYPT_MODE, key)

            val plainText = ByteArray(cipher.getOutputSize(input.size))
            var ptLength = cipher.update(input, 0, input.size, plainText, 0)
            ptLength += cipher.doFinal(plainText, ptLength)
            val decryptedString = String(plainText)
            return decryptedString.trim { it <= ' ' }
        }
    }
    private fun initRecyclerView()
    {
        postRecycleView.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(50)
            addItemDecoration(topSpacingDecoration)
            var list = parseJSON("newPostsList")
            postList = list
            //read from file
            postCardAdapter = rvAdapter(postList, object : rvAdapter.Callback {
                override fun onItemClicked(item: PostCard, position : Int) {
                    var viewPostFragment = ViewPostFragment()
                    var args = Bundle()
                    args.putInt("tapedPostId", position)
                    viewPostFragment.setArguments(args)

                    var fragmentManager : FragmentManager? = getFragmentManager()
                    var fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()

                    fragmentTransaction.replace(R.id.host, viewPostFragment , "viewPostFragment ")
                    fragmentTransaction.commit()
                }
            })
            adapter = postCardAdapter
        }
    }
}
