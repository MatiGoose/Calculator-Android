package com.example.calculatorandroid

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_add_post.*

import kotlinx.android.synthetic.main.fragment_view_post.*
import java.util.UUID.randomUUID
import android.provider.MediaStore
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.security.SecureRandom
import java.util.*
import java.util.prefs.Preferences
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList


class AddPostFragment : Fragment(), OnBackPressed {

    val REQUEST_CODE = 100
    var currentImageUri : String = ""

    override fun onBackPressed() {
        var postListFragment = PostListFragment ()
        var fragmentManager : FragmentManager? = getFragmentManager()
        var fragmentTransaсtion : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaсtion.replace(R.id.host, postListFragment)
        fragmentTransaсtion.commit()
    }
    /*
    private fun saveToJSON(list : ArrayList<PostCard>, fileName : String)
    {
        var json = JSONObject()
        json.put("posts", addCards(list))
        saveJSON(json.toString(), fileName)
    }
    private fun saveJSON(jsonString : String, fileName: String)
    {
        val output: Writer
        val file = createFile(fileName)
        output = BufferedWriter(FileWriter(file))
        output.write(jsonString)
        output.close()
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun createFile(fileName: String) : File
    {
        val storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        if(!storageDir!!.exists())
        {
            storageDir.mkdir()
        }
        return File.createTempFile(fileName, ".json", storageDir)
    }
    private fun addCards(cards : ArrayList<PostCard>) : JSONArray
    {
        var cardsJSON = JSONArray()
        cards.forEach {
            cardsJSON
                .put(
                    JSONArray()
                        .put(it.imageUri)
                        .put(it.title)
                        .put(it.description))
        }
        return cardsJSON
    }
    */

    private fun saveToJSON(list : ArrayList<PostCard>, filename : String)
    {
        var sharedPreferences = activity!!.getSharedPreferences(filename, MODE_PRIVATE)
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirmButton.setOnClickListener {
            if(currentImageUri.equals(""))
            {
                val builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Select picture.")
                builder.setPositiveButton("Ok", { dialogInterface: DialogInterface, i: Int ->})
                builder.show()
                return@setOnClickListener
            }
            val path = getRealPathFromURI(Uri.parse(currentImageUri))
            val newPost = PostCard(path, titleTextEdit.text.toString(), descriptionTextEdit.text.toString())
            PostListFragment.postList.add(0, newPost)


            saveToJSON(PostListFragment.postList, "newPostsList")

            var postListFragment = PostListFragment()
            var fragmentManager : FragmentManager? = getFragmentManager()
            var fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.host, postListFragment, "postListFragment")
            fragmentTransaction.commit()
        }
        postImageEdit.setOnClickListener {
            openGalleryForImage()
        }
    }

    fun getRealPathFromURI(contentUri: Uri): String {

        // can post image
        val proj = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = activity!!.managedQuery(
            contentUri,
            proj, // WHERE clause selection arguments (none)
            null,// Which columns to return
            null,// WHERE clause; which rows to return (all rows)
            null// Order-by clause (ascending by name)
        )

        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()

        return cursor.getString(column_index)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            currentImageUri = data?.data.toString()
            postImageEdit.setImageURI(data?.data) // handle chosen image
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.fragment_add_post, container, false)





        return rootView
    }

}
