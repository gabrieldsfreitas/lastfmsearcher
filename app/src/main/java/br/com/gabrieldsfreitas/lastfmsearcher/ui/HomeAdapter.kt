package br.com.gabrieldsfreitas.lastfmsearcher.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.gabrieldsfreitas.lastfmsearcher.databinding.ItemTrackBinding
import br.com.gabrieldsfreitas.lastfmsearcher.model.TrackModel

class HomeAdapter(
    private var context: Context,
    private var trackModelList: MutableList<TrackModel>
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrackBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(trackModelList[position]) {
                binding.nameTextView.text = this.name
                binding.listenersTextView.text = this.listeners
                binding.artistTextView.text = this.artist

                holder.itemView.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(this.url))
                    startActivity(context, browserIntent, null)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return trackModelList.size
    }

    fun updateData(mutableList: MutableList<TrackModel>) {
        trackModelList = mutableList
    }

    inner class ViewHolder(val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root)
}