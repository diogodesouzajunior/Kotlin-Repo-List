package br.diogo.kotlinrepolist

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.diogo.kotlinrepolist.repos.AdapterItemsContract

class BindingAdapters {


    companion object {
        @BindingAdapter("items")
        @JvmStatic
        fun setItems(recyclerView: RecyclerView, items: List<Any>) {

            recyclerView.adapter.let {
                if (it is AdapterItemsContract) {
                    it.replaceItems(items)
                }
            }
        }
    }

}