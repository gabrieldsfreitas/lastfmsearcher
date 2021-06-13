package br.com.gabrieldsfreitas.lastfmsearcher.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gabrieldsfreitas.lastfmsearcher.*
import br.com.gabrieldsfreitas.lastfmsearcher.databinding.ActivityHomeBinding
import br.com.gabrieldsfreitas.lastfmsearcher.ui.viewmodel.TrackViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var homeAdapter: HomeAdapter
    private var trackList: MutableList<SearchedTrack> = arrayListOf()

    private val trackViewModel: TrackViewModel by viewModel()
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        homeAdapter = HomeAdapter(trackList)
        setSearchView()
        setFilterView()
        setRecyclerView()
        topTracks()
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
                    val sortData = trackList.sortedWith { p0, p1 ->
                        (p1?.listeners?.toInt() ?: 0) - (p0?.listeners?.toInt() ?: 0)
                    }
                    homeAdapter.updateData(sortData as MutableList)
                    homeAdapter.notifyDataSetChanged()

                    true
                }
                R.id.unpopular -> {
                    val sortData = trackList.sortedWith { p0, p1 ->
                        (p0?.listeners?.toInt() ?: 0) - (p1?.listeners?.toInt() ?: 0)
                    }
                    homeAdapter.updateData(sortData as MutableList)
                    homeAdapter.notifyDataSetChanged()

                    true
                }
                else -> false
            }
        }
    }

    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        binding.recyclerView.adapter = homeAdapter
    }

    private fun topTracks() {
        AnimationUtils.replaceView(binding.nestedScrollView, binding.progressBar)
        trackViewModel.topTracks().observe(this) { searchTrackResponse ->
            searchTrackResponse?.let { searchTrack ->
                when (searchTrack) {
                    is ApiResult.OnSuccess -> {
                        searchTrack.data.let { track ->
                            binding.totalTextView.text =
                                getString(R.string.total_result, track?.totalResults)
                            track?.tracks?.forEach() {
                                Log.e("#find", " #find it: ${it.name}")
                            }
                            trackList = track?.tracks ?: arrayListOf()
                            homeAdapter.updateData(trackList)
                            homeAdapter.notifyDataSetChanged()
                            AnimationUtils.replaceView(binding.progressBar, binding.nestedScrollView)
                            true
                        } ?: false
                    }
                    is ApiResult.OnError -> {
                        Snackbar.make(
                            binding.coordinatorLayout,
                            searchTrack.exception.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        false
                    }
                }
            } ?: false
        }
    }

    private fun searchTrack(wordTyped: String) {
        AnimationUtils.replaceView(binding.nestedScrollView, binding.progressBar)
        trackViewModel.searchTrack(wordTyped).observe(this) { searchTrackResponse ->
            searchTrackResponse?.let { searchTrack ->
                when (searchTrack) {
                    is ApiResult.OnSuccess -> {
                        searchTrack.data.let { track ->
                            binding.totalTextView.text =
                                getString(R.string.total_result, track?.totalResults)
                            track?.tracks?.forEach() {
                                Log.e("#find", " #find it: ${it.name}")
                            }
                            trackList = track?.tracks ?: arrayListOf()
                            homeAdapter.updateData(trackList)
                            homeAdapter.notifyDataSetChanged()
                            AnimationUtils.replaceView(binding.progressBar, binding.nestedScrollView)
                            true
                        } ?: false
                    }
                    is ApiResult.OnError -> {
                        Snackbar.make(
                            binding.coordinatorLayout,
                            searchTrack.exception.message.toString(),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        false
                    }
                }
            } ?: false
        }
    }
}
