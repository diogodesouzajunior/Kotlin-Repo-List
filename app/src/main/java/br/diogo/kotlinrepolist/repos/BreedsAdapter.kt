package br.diogo.kotlinrepolist.repos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import br.diogo.kotlinrepolist.R
import br.diogo.kotlinrepolist.data.model.Repository
import br.diogo.kotlinrepolist.data.model.interfaces.IAdapterDataSource
import br.diogo.kotlinrepolist.databinding.RepoItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.repo_item.view.*
import kotlinx.android.synthetic.main.row_load_more.view.*


class ReposAdapter(val context: Context?, var items: MutableList<Repository>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AdapterItemsContract {

    private var adapterInteractionListener: IAdapterDataSource? = null

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private var hasMoreData = true
    var offline: Boolean = false

    private var loadProgressBar: ProgressBar? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == VIEW_TYPE_ITEM) {
            val binding: RepoItemBinding = RepoItemBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_load_more, parent, false)
            return LoadViewHolder(view)
        }
    }

    override fun replaceItems(items: List<*>) {
        this.items = items as ArrayList<Repository>
        notifyDataSetChanged()
    }

    fun addElements(newElements: List<Repository>) {
        val lastIndex = items.size
        items.addAll(newElements)
        notifyItemRangeInserted(lastIndex, newElements.size)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (items.isNotEmpty() && items.size - 1 == position && !offline)
            return VIEW_TYPE_LOADING
        return VIEW_TYPE_ITEM
    }

    fun setHasMoreData(hasMoreData: Boolean) {
        this.hasMoreData = hasMoreData
        if (loadProgressBar != null) {
            val visibility = if (hasMoreData) View.VISIBLE else View.GONE
            loadProgressBar!!.visibility = visibility
        }
    }

    private fun userWantsLoadMore(progressBar: ProgressBar) {
        loadProgressBar = progressBar
        if (hasMoreData && adapterInteractionListener != null) {
            loadProgressBar!!.visibility = View.VISIBLE
            adapterInteractionListener!!.adapterUserWantsLoadMoreData(this)
        }
    }

    fun setAdapterInteractionListener(adapterInteractionListener: IAdapterDataSource) {
        this.adapterInteractionListener = adapterInteractionListener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is LoadViewHolder) {
            if (adapterInteractionListener != null) {
                val visibility = if (hasMoreData) View.VISIBLE else View.GONE

                val loadViewHolder = holder
                loadViewHolder.progressBar.visibility = visibility
                userWantsLoadMore(loadViewHolder.progressBar)
            }
        } else if (holder is ViewHolder) {
            holder.bind(items[position])
            val entity = items[position]
            context?.let {
                Glide.with(context)
                    .load(entity.owner.avatar_url?.let { entity.owner.avatar_url })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivAvatar)
            }
        }

    }

    internal class LoadViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val progressBar = view.row_load_more_progress_bar
    }

    class ViewHolder(val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val ivAvatar = itemView.ivAvatar

        fun bind(repository: Repository) {
            binding.repository = repository
            binding.executePendingBindings()
        }
    }
}