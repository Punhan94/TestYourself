package com.example.testyourself.presentation.ui.fragments.user_fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testyourself.R
import com.example.testyourself.databinding.FragmentUserHomeBinding
import com.example.testyourself.presentation.adapters.LessonsAdapter
import com.example.testyourself.presentation.viewmodels.UserHomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserHomeFragment : Fragment() {
    private val viewModel: UserHomeViewModel by viewModels()
    private val lessonAdapter = LessonsAdapter(this)
    private var _binding : FragmentUserHomeBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userHomeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.userHomeRecyclerView.adapter = lessonAdapter

        observeLiveData()

        lessonAdapter.onItemClick={
            val bundle = bundleOf(
                "lessonId" to it.lessonId
            )
            findNavController().navigate(R.id.action_userHomeFragment_to_examListFragment, bundle)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeLiveData(){

        viewModel.lesson.observe(viewLifecycleOwner) { a->
            a?.let {
                lessonAdapter.differ.submitList(it)
            }
        }
    }

}