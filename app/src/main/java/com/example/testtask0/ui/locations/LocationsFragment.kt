package com.example.testtask0.ui.locations

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.testtask0.R
import com.example.testtask0.data.network.api.response.LocationResponse
import com.example.testtask0.ui.ContainerActivity
import com.example.testtask0.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE

class LocationsFragment : BaseFragment(R.layout.fragment_locations) {

    private val viewModel by viewModels<LocationsViewModel>()

    private val listAdapter = LocationsListAdapter(::onListItemSelected)

    private val refreshLayout: SwipeRefreshLayout
        get() = view(R.id.refreshLayout)
    private val list: RecyclerView
        get() = view(R.id.list)
    private val paginationProgress: ProgressBar
        get() = view(R.id.paginationProgress)
    private val errorSnackbar by lazy { Snackbar.make(requireView(), R.string.error_unknown, LENGTH_INDEFINITE) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshLayout.setOnRefreshListener(viewModel::onRefresh)
        (requireActivity() as ContainerActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        list.adapter = listAdapter
    }

    override fun onDestroyView() {
        list.adapter = null
        super.onDestroyView()
    }

    override fun observeLiveData() = viewModel.run {
        error.observe(::showError)
        initialProgress.observe { refreshLayout.isRefreshing = it }
        pagingProgress.observe { paginationProgress.isVisible = it }
        locations.observe(listAdapter::submitList)
    }

    private fun onListItemSelected(location: LocationResponse) {
        Toast.makeText(requireContext(), "$location", Toast.LENGTH_SHORT).show()
    }

    private fun showError(error: Throwable) = errorSnackbar.run {
        setText(error.message ?: getString(R.string.error_unknown))
        setAction(R.string.retry) {
            viewModel.onRetry()
            dismiss()
        }
        show()
    }
}