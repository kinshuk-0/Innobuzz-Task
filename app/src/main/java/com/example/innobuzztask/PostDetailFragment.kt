package com.example.innobuzztask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.innobuzztask.databinding.FragmentPostDetailBinding
import com.example.innobuzztask.model.PostsDatabase

class PostDetailFragment : Fragment() {
    lateinit var binding: FragmentPostDetailBinding
    private val db by lazy { PostsDatabase.getDatabase(requireActivity()).dao }
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt("position")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPostDetailBinding.inflate(layoutInflater)

        if(position != null) {
            binding.detailPageTitle.text = db.getPosts()[position!!].title
            binding.detailPageBody.text  = db.getPosts()[position!!].body
        }

        return binding.root
    }

}