package com.yellowai.git_pull_requests.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yellowai.git_pull_requests.databinding.HomeScreenFragmentBinding
import com.yellowai.git_pull_requests.models.Repo
import com.yellowai.git_pull_requests.ui.ActionListeners
import com.yellowai.git_pull_requests.ui.repos.RepoAdapter
import com.yellowai.git_pull_requests.ui.viewmodel.HomeScreenViewModel
import com.yellowai.git_pull_requests.utils.loadImage
import com.yellowai.git_pull_requests.utils.navigateToPullRequestsScreen

class HomeScreen : Fragment(), ActionListeners {

    private lateinit var viewModel: HomeScreenViewModel
    lateinit var binding: HomeScreenFragmentBinding
    private var repoAdapter: RepoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeScreenFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViewModelListeners()
        callApi()
    }

    private fun callApi() {
        viewModel.getProfile()
        viewModel.getRepos()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)
    }

    private fun initViewModelListeners() {
        viewModel.profile.observe(viewLifecycleOwner)
        { profile ->
            if (profile != null) {
                profile.avatar_url?.let { avatarUrl ->
                    context?.let { ctx ->
                        loadImage(ctx, avatarUrl, binding.profileImage)
                    }
                }
                profile.name?.let {
                    binding.name.text = it
                }

                profile.public_repos?.let {
                    binding.count.text = it.toString()
                }
            } else {
                binding.header.visibility = View.GONE
                // Profile not found
            }
            showProfile()
        }

        viewModel.repos.observe(viewLifecycleOwner) { repos ->
            if (repos != null) {
                if (repoAdapter == null) {
                    repoAdapter = RepoAdapter(repos, this)
                }
                binding.reposRecyclerView.adapter = repoAdapter
            } else {
                binding.reposRecyclerView.visibility = View.GONE
            }
            hideProgressbar()
            showRepos()
        }

        viewModel.spinner.observe(viewLifecycleOwner)
        {
            if (!it) {
                hideProgressbar()
            } else {
                showProgressbar()
            }
        }
    }

    private fun hideProgressbar() {
        binding.progressbar.visibility = View.GONE
    }

    private fun showProgressbar() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideProfile() {
        binding.header.visibility = View.GONE
    }

    private fun showProfile() {
        binding.header.visibility = View.VISIBLE
    }

    private fun hideRepos() {
        binding.reposRecyclerView.visibility = View.GONE
    }

    private fun showRepos() {
        binding.reposRecyclerView.visibility = View.VISIBLE
    }


    companion object {
        fun newInstance() = HomeScreen()
    }

    override fun onClicked(repo: Repo) {
        activity?.let {
            navigateToPullRequestsScreen(it.supportFragmentManager, "pullRequestFragment", repo)
        }
    }
}