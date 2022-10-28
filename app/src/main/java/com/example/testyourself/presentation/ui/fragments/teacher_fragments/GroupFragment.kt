package com.example.testyourself.presentation.ui.fragments.teacher_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testyourself.databinding.FragmentGroupBinding
import com.example.testyourself.presentation.adapters.StudentAdapter
import com.example.testyourself.presentation.viewmodels.GroupFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GroupFragment : Fragment() {
    private var _binding: FragmentGroupBinding?=null
    private val binding get() = _binding!!
    private val studentAdapter = StudentAdapter()
    private val viewModel: GroupFragmentViewModel by viewModels()
    private lateinit var myGroupName:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argument = arguments?.get("myGroup")
        myGroupName = argument as String
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGroupBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.studentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.studentRecyclerView.adapter = studentAdapter
        observeLiveData()
        binding.myGroupName.text = myGroupName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeLiveData(){
        viewModel.userProfile.observe(viewLifecycleOwner, Observer { a->
            a?.let {
                studentAdapter.differ.submitList(it)
            }
        })
    }
}