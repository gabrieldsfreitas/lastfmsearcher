package br.com.gabrieldsfreitas.lastfmsearcher.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gabrieldsfreitas.lastfmsearcher.R
import br.com.gabrieldsfreitas.lastfmsearcher.databinding.ActivityHomeBinding
import br.com.gabrieldsfreitas.lastfmsearcher.model.SearchedTracksModel
import br.com.gabrieldsfreitas.lastfmsearcher.repository.ApiResult
import br.com.gabrieldsfreitas.lastfmsearcher.ui.viewmodel.TrackViewModel
import br.com.gabrieldsfreitas.lastfmsearcher.util.AnimationUtils
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        const val SAVE_INSTANCE_SEARCHED_TRACKS_MODEL = "SAVE_INSTANCE_SEARCHED_TRACKS_MODEL"
    }

    private var homeAdapter: HomeAdapter = HomeAdapter(this, arrayListOf())
    private var searchedTracksModel: SearchedTracksModel? = null

    private val trackViewModel: TrackViewModel by viewModel()
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSearchView()
        setFilterView()
        setRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SAVE_INSTANCE_SEARCHED_TRACKS_MODEL, searchedTracksModel)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchedTracksModel = savedInstanceState.getParcelable(SAVE_INSTANCE_SEARCHED_TRACKS_MODEL)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        searchedTracksModel?.let {
            onSearchTrackList(it)
        } ?: topTracks()
    }

    private fun setSearchView() {
        val searchView = binding.topAppBar.findViewById<SearchView>(R.id.search)
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchTrack(query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setFilterView() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.most_popular -> {
                    sortMostPopular()
                    true
                }
                R.id.lest_popular -> {
                    sortLeastPopular()
                    true
                }
                else -> false
            }
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = homeAdapter
    }

    private fun topTracks() {
        AnimationUtils.replaceView(binding.nestedScrollView, binding.progressBar)
        trackViewModel.topTracks().observe(this) { result ->
            when (result) {
                is ApiResult.OnSuccess -> {
                    onTopTrackList(result.data)
                }

                is ApiResult.OnError -> {
                    onError(result)
                }
            }
        }
    }

    private fun searchTrack(wordTyped: String) {
        AnimationUtils.replaceView(binding.nestedScrollView, binding.progressBar)
        trackViewModel.searchTrack(wordTyped).observe(this) { result ->
            when (result) {
                is ApiResult.OnSuccess -> {
                    onSearchTrackList(result.data)
                }

                is ApiResult.OnError -> {
                    onError(result)
                }
            }
        }
    }

    private fun sortMostPopular() {
        searchedTracksModel?.trackModels?.let {
            if (it.isNotEmpty()) {
                val sortData = it.sortedWith { p0, p1 ->
                    (p1?.listeners?.toInt() ?: 0) - (p0?.listeners?.toInt() ?: 0)
                }
                homeAdapter.updateData(sortData as MutableList)
                homeAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun sortLeastPopular() {
        searchedTracksModel?.trackModels?.let {
            if (it.isNotEmpty()) {
                val sortData = it.sortedWith { p0, p1 ->
                    (p0?.listeners?.toInt() ?: 0) - (p1?.listeners?.toInt() ?: 0)
                }
                homeAdapter.updateData(sortData as MutableList)
                homeAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onTopTrackList(data: SearchedTracksModel) {
        searchedTracksModel = data
        binding.totalTextView.text = getString(R.string.top_tracks_result, data.totalResults)
        updateAdapter(data)
    }

    private fun onSearchTrackList(data: SearchedTracksModel) {
        searchedTracksModel = data
        binding.totalTextView.text = getString(R.string.total_result, data.totalResults)
        updateAdapter(data)
    }

    private fun updateAdapter(data: SearchedTracksModel) {
        homeAdapter.updateData(data.trackModels)
        homeAdapter.notifyDataSetChanged()
        AnimationUtils.replaceView(binding.progressBar, binding.nestedScrollView)
    }

    private fun onError(result: ApiResult.OnError) {
        Snackbar.make(
            binding.coordinatorLayout,
            result.exception.message.toString(),
            Snackbar.LENGTH_LONG
        ).show()
        onSearchTrackList(SearchedTracksModel(arrayListOf(), getString(R.string.no_result)))
    }
}
