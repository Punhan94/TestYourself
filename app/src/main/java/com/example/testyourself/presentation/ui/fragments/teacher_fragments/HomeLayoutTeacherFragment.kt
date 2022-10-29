package com.example.testyourself.presentation.ui.fragments.teacher_fragments

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
import com.example.testyourself.databinding.FragmentHomeLayoutTeacherBinding
import com.example.testyourself.presentation.adapters.ExamAdapter
import com.example.testyourself.presentation.adapters.GroupsAdapter
import com.example.testyourself.presentation.viewmodels.HomeLayoutTeacherFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeLayoutTeacherFragment : Fragment() {
    private var _binding:FragmentHomeLayoutTeacherBinding?=null
    private val binding get() = _binding!!
    private val examAdapter = ExamAdapter(false)
    private val groupAdapter = GroupsAdapter()
    private val viewModel: HomeLayoutTeacherFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeLayoutTeacherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.teacherExamsRecyclerView.layoutManager = LinearLayoutManager(
            context,LinearLayoutManager.HORIZONTAL,false)
        binding.teacherExamsRecyclerView.adapter = examAdapter
        binding.teacherGroupRecyclerView.layoutManager = LinearLayoutManager(
            context,LinearLayoutManager.HORIZONTAL,false)
        binding.teacherGroupRecyclerView.adapter = groupAdapter
        observeLiveData()

        binding.addNewExam.setOnClickListener {
            findNavController().navigate(R.id.action_homeLayoutTeacherFragment_to_newExamFragment)
        }

        binding.addNewGroup.setOnClickListener {
            findNavController().navigate(R.id.action_homeLayoutTeacherFragment_to_studentAddGroupFragment)
        }

        examAdapter.onItemClick={
            val bundle = bundleOf(
                "examID" to it.examId
            )
            findNavController().navigate(R.id.action_homeLayoutTeacherFragment_to_changeOrNewTestFragment, bundle)
        }

        groupAdapter.onItemClick={
            val bundle = bundleOf(
                "myGroup" to it.groupName
            )
            findNavController().navigate(R.id.action_homeLayoutTeacherFragment_to_groupFragment,bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeLiveData(){
        viewModel.exams.observe(viewLifecycleOwner) { a->
            a?.let {
                examAdapter.differ.submitList(it)
            }
        }
        viewModel.groups.observe(viewLifecycleOwner) { a->
            a?.let {
                groupAdapter.differ.submitList(it)
            }
        }
    }

}