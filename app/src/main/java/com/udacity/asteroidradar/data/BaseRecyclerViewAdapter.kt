package com.udacity.asteroidradar.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BaseRecyclerViewAdapter<T : Any>(
    diffCallback: DiffUtil.ItemCallback<T>, private val callback: ((item: T,position : Int) -> Unit)? = null
) : PagingDataAdapter<T, DataBindingViewHolder<T>>(
    diffCallback
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, getLayoutRes(viewType), parent, false
        )
        binding.lifecycleOwner = getLifecycleOwner()
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
        holder.itemView.setOnClickListener {
            callback?.invoke(item,position)
        }
    }

    @LayoutRes
    abstract fun getLayoutRes(viewType: Int): Int


    open fun getLifecycleOwner(): LifecycleOwner? {
        return null
    }
}

class GenericModelCallBack<T : Any>(
    private val _areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val _areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return _areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return _areContentsTheSame(oldItem, newItem)
    }
}

