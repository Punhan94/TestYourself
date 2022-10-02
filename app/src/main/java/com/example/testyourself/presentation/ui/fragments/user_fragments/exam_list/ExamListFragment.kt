package com.example.testyourself.presentation.ui.fragments.user_fragments.exam_list
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testyourself.R
import com.example.testyourself.data.repository.ApiRepository
import com.example.testyourself.presentation.adapters.ExamAdapter
import com.example.testyourself.presentation.viewmodels.ExamListViewModel
import com.example.testyourself.presentation.viewmodels.ExamListViewModelProviderFactory
import com.example.testyourself.presentation.viewmodels.HomeViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_exam_list.*


class ExamListFragment : Fragment() {
    private lateinit var viewModel: ExamListViewModel
    private val examAdapter = ExamAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exam_list_rv.layoutManager = LinearLayoutManager(context)
        exam_list_rv.adapter = examAdapter
        val viewModelProviderFactory= ExamListViewModelProviderFactory(ApiRepository())
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory ).get(ExamListViewModel::class.java)
        observeLiveData()

        examAdapter.onItemClick={
            val bundle = bundleOf(
                "examId" to it.examId
            )
            findNavController().navigate(R.id.action_examListFragment_to_examTestFragment,bundle)
        }
    }


    private fun observeLiveData(){
        val argument = arguments?.get("lessonId")
        argument?.let {
            viewModel.arg = it as Int
        }
        viewModel.exam.observe(viewLifecycleOwner, Observer { a->
            a?.let {
                examAdapter.differ.submitList(it)
            }
        })
    }

}