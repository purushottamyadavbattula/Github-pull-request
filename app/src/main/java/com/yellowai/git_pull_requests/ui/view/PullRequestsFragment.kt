package com.yellowai.git_pull_requests.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yellowai.git_pull_requests.databinding.FragmentPullRequestsBinding
import com.yellowai.git_pull_requests.models.Repo
import com.yellowai.git_pull_requests.ui.pulls.PullRequestAdapter
import com.yellowai.git_pull_requests.ui.viewmodel.PullRequestViewModel
import com.yellowai.git_pull_requests.utils.REPO

class PullRequestsFragment : Fragment() {
    private var repo: Repo? = null
    private lateinit var binding: FragmentPullRequestsBinding
    private lateinit var viewModel: PullRequestViewModel
    private var pullRequestAdapter: PullRequestAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            repo = it.getParcelable(REPO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPullRequestsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViewModelListeners()
        callAPI()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(PullRequestViewModel::class.java)
    }

    private fun initViewModelListeners() {
        viewModel.pullRequests.observe(viewLifecycleOwner)
        {
            if (it.isNullOrEmpty()) {
                hideProgressbar()
                showNoPullsReq()
                return@observe
            }
            it?.let {
                if (pullRequestAdapter == null) {
                    pullRequestAdapter = PullRequestAdapter(it)
                }
                binding.pullReqRecyclerView.adapter = pullRequestAdapter
                showContentView()
                hideProgressbar()
            }
        }
    }

    private fun callAPI() {
        repo?.let {
            it.name?.let { name ->
                viewModel.getClosedPullRequests(name)
            }
        }
    }

    private fun showProgressbar() {
        binding.progressbar.visibility = View.VISIBLE
    }

    private fun hideProgressbar() {
        binding.progressbar.visibility = View.GONE
    }

    private fun showContentView() {
        binding.contentView.visibility = View.VISIBLE
    }

    private fun hideContentView() {
        binding.contentView.visibility = View.GONE
    }

    private fun showNoPullsReq() {
        binding.noPullReq.visibility = View.VISIBLE
    }

    private fun hideNoPullsReq() {
        binding.noPullReq.visibility = View.GONE
    }


    companion object {
        @JvmStatic
        fun newInstance(repo: Repo) =
            PullRequestsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(REPO, repo)
                }
            }
    }
}