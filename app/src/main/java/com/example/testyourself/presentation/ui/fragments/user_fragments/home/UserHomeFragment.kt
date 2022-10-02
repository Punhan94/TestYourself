package com.example.testyourself.presentation.ui.fragments.user_fragments.home

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
import com.example.testyourself.presentation.adapters.LessonsAdapter
import com.example.testyourself.presentation.viewmodels.HomeViewModelProviderFactory
import com.example.testyourself.presentation.viewmodels.UserHomeViewModel
import kotlinx.android.synthetic.main.fragment_user_home.*

class UserHomeFragment : Fragment() {
    private lateinit var viewModel: UserHomeViewModel
    private val lessonAdapter = LessonsAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userHome_recyclerView.layoutManager = LinearLayoutManager(context)
        userHome_recyclerView.adapter = lessonAdapter
        val viewModelProviderFactory=HomeViewModelProviderFactory(ApiRepository())
        viewModel = ViewModelProviders.of(this, viewModelProviderFactory ).get(UserHomeViewModel::class.java)
        observeLiveData()

        lessonAdapter.onItemClick={
            val bundle = bundleOf(
                "lessonId" to it.lessonId
            )
            findNavController().navigate(R.id.action_userHomeFragment_to_examListFragment, bundle)
        }

    }

    private fun observeLiveData(){

        viewModel.lesson.observe(viewLifecycleOwner, Observer { a->
            a?.let {
                lessonAdapter.differ.submitList(it)
            }
        })
    }

}