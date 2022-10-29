package com.example.testyourself.presentation.ui.fragments.user_fragments.exam_list
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
import com.example.testyourself.databinding.FragmentExamListBinding
import com.example.testyourself.presentation.adapters.ExamAdapter
import com.example.testyourself.presentation.viewmodels.ExamListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ExamListFragment : Fragment() {
    private var _binding: FragmentExamListBinding?=null
    private val binding get() = _binding!!
    private val viewModel: ExamListViewModel by viewModels()
    private val examAdapter = ExamAdapter(true)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExamListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.examListRv.layoutManager = LinearLayoutManager(context)
        binding.examListRv.adapter = examAdapter
        observeLiveData()

        examAdapter.onItemClick={
            val bundle = bundleOf(
                "examId" to it.examId
            )
            findNavController().navigate(R.id.action_examListFragment_to_examTestFragment,bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeLiveData(){
        val argument = arguments?.get("lessonId")
        argument?.let {
            viewModel.arg = it as Int
        }
        viewModel.exam.observe(viewLifecycleOwner) { a ->
            a?.let {
                examAdapter.differ.submitList(it)
            }
        }
    }



}