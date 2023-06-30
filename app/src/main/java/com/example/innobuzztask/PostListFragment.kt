package com.example.innobuzztask

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.innobuzztask.databinding.FragmentPostListBinding
import com.example.innobuzztask.model.PostResponse
import com.example.innobuzztask.model.Posts
import com.example.innobuzztask.model.PostsDatabase
import com.example.innobuzztask.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostListFragment : Fragment() {

    private val db by lazy { PostsDatabase.getDatabase(requireActivity()).dao }
    lateinit var binding: FragmentPostListBinding
    lateinit var adapter: PostAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPostListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.rvPost
        recyclerView.layoutManager = LinearLayoutManager(context)

        if(context?.let { isInternetAvailable(it) } == true) {
            getPosts()
        } else {
            if(db.getPosts().isNotEmpty()) {
                val postsList: ArrayList<Posts> = db.getPosts() as ArrayList<Posts>
                adapter = PostAdapter(postsList)
                recyclerView.adapter = adapter

                adapter.setOnPostClickListener(object : PostAdapter.PostClickListener {
                    override fun onPostClicked(position: Int) {
                        val bundle = Bundle()
                        bundle.putInt("position", position)
                        Navigation.findNavController(binding.root).navigate(R.id.action_postListFragment_to_postDetailFragment, bundle)
                    }
                })
            } else {
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getPosts() {
        val mProgressDialog = ProgressDialog(context)
        mProgressDialog.setTitle("Please wait...")
        mProgressDialog.show()
        val call = RetrofitClient.apiInterface.getPosts()
        call.enqueue(object : Callback<PostResponse?> {
            override fun onResponse(call: Call<PostResponse?>, response: Response<PostResponse?>) {
                mProgressDialog.dismiss()
                if(response.body() != null) {
                    val postsList: ArrayList<Posts> = response.body()!!

                    adapter = PostAdapter(postsList)
                    recyclerView.adapter = adapter

                    for(post in postsList) {
                        db.updatePost(post)
                    }

                    adapter.setOnPostClickListener(object : PostAdapter.PostClickListener {
                        override fun onPostClicked(position: Int) {
                            val bundle = Bundle()
                            bundle.putInt("position", position)
                            Navigation.findNavController(binding.root).navigate(R.id.action_postListFragment_to_postDetailFragment, bundle)
                        }
                    })

                    Log.d("RESPONSE", "Response Received")
                } else {
                    Log.d("RESPONSE", "FAILED")
                }
            }

            override fun onFailure(call: Call<PostResponse?>, t: Throwable) {
                mProgressDialog.dismiss()
                Log.d("RESPONSE", "FAILED")
            }

        })
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}