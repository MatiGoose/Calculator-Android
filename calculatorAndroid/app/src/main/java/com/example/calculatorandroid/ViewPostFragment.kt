package com.example.calculatorandroid

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_view_post.*

class ViewPostFragment : Fragment(), OnBackPressed {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        //here is your list array
        val tapedPostId = bundle!!.getInt("tapedPostId")
        postImageView.setImageURI(PostListFragment.postList[tapedPostId].imageUri)
        titleTextView.setText(PostListFragment.postList[tapedPostId].title)
        descriptionTextView.setText(PostListFragment.postList[tapedPostId].description)
        descriptionTextView.setMovementMethod(ScrollingMovementMethod())
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
