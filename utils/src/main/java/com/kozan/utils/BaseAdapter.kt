package com.kozan.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T:Any, DB : ViewDataBinding> (private val clickedItem :(T)->Unit): RecyclerView.Adapter<BaseAdapter.BaseViewHolder<DB>>() {
    private var items: List<T> = emptyList()
    override fun getItemCount() = items.size

    fun setItems(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<DB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<DB>(inflater, getLayoutRes(), parent, false)
        return BaseViewHolder(binding)
    }
    abstract fun getLayoutRes(): Int
    override fun onBindViewHolder(holder: BaseViewHolder<DB>, position: Int) {
        val item = items[position]
        bindViewHolder(holder,item, position)
        holder.itemView.setOnClickListener {
            clickedItem.invoke(item)
        }
    }
    abstract fun bindViewHolder(holder: BaseViewHolder<DB>, item: T, position: Int)
    class BaseViewHolder<DB : ViewDataBinding>( val binding: DB) : RecyclerView.ViewHolder(binding.root)
}