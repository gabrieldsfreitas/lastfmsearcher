package br.com.gabrieldsfreitas.lastfmsearcher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.gabrieldsfreitas.lastfmsearcher.databinding.ItemTrackBinding

class HomeAdapter(private var trackList: MutableList<SearchedTrack>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(trackList[position]) {
                binding.nameTextView.text = this.name
                binding.listenersTextView.text = this.listeners
                binding.artistTextView.text = this.artist
            }
        }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun updateData(mutableList: MutableList<SearchedTrack>) {
        trackList = mutableList
    }

    inner class ViewHolder(val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root)
}