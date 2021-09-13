package com.android.framework.mvvm.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.framework.mvvm.R
import com.android.framework.mvvm.data.model.User
import com.android.framework.mvvm.interfaces.ViewNavigation
import com.android.framework.mvvm.ui.adapter.MainAdapter
import com.android.framework.mvvm.ui.viewmodel.MainViewModel
import com.android.framework.mvvm.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment(), ViewNavigation {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.navigationEvent.setEventReceiver(viewLifecycleOwner,this)
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MainAdapter(arrayListOf())

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users ->
                        mainViewModel.insertUsers(users).observe(viewLifecycleOwner, { status ->
                            if (status == Status.SUCCESS) {
                                mainViewModel.fetchUsers().observe(requireActivity(), { userList ->
                                    renderList(userList)
                                })
                            }
                        })
                    }
                    recyclerView.visibility = View.VISIBLE
                }

                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                Status.ERROR -> {
                    // Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
    }
}