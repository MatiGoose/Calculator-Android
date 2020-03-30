package com.example.calculatorandroid

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_post_list.*

class PostListFragment : Fragment(), OnBackPressed {

    companion object
    {
        var postList = ArrayList<PostCard>()

    }
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
    private fun initRecyclerView()
    {
        postRecycleView.apply {
            layoutManager = LinearLayoutManager(context)
            val topSpacingDecoration = TopSpacingItemDecoration(50)
            addItemDecoration(topSpacingDecoration)
            postCardAdapter = rvAdapter(postList, object : rvAdapter.Callback {
                override fun onItemClicked(item: PostCard, position : Int) {
                    var viewPostFragment = ViewPostFragment()
                    var args = Bundle()
                    args.putInt("tapedPostId", position)
                    viewPostFragment.setArguments(args)

                    var fragmentManager : FragmentManager? = getFragmentManager()
                    var fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()
                    //fragmentTransaction.replace(R.id.host, calculatorFragment)
                    fragmentTransaction.replace(R.id.host, viewPostFragment , "viewPostFragment ")
                    fragmentTransaction.commit()
                }
            })
            adapter = postCardAdapter
        }
    }
}
