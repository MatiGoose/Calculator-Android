package com.example.calculatorandroid

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
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
import java.io.ByteArrayOutputStream
import java.util.*


class AddPostFragment : Fragment(), OnBackPressed {

    val REQUEST_CODE = 100
    var currentImageUri : Uri? = null

    override fun onBackPressed() {
        var postListFragment = PostListFragment ()
        var fragmentManager : FragmentManager? = getFragmentManager()
        var fragmentTransaсtion : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaсtion.replace(R.id.host, postListFragment)
        fragmentTransaсtion.commit()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirmButton.setOnClickListener {
            if(currentImageUri == null)
            {
                val builder = AlertDialog.Builder(this.context)
                builder.setTitle("Error")
                builder.setMessage("Select picture.")
                builder.setPositiveButton("Ok", { dialogInterface: DialogInterface, i: Int ->})
                builder.show()
                return@setOnClickListener
            }
            val newPost = PostCard(currentImageUri!!, titleTextEdit.text.toString(), descriptionTextEdit.text.toString())
            PostListFragment.postList.add(0, newPost)

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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            currentImageUri = data?.data!!
            postImageEdit.setImageURI(data.data) // handle chosen image
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
