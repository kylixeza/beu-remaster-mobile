package com.kylix.common.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<VB: ViewBinding, ListType>
    : RecyclerView.Adapter<BaseRecyclerViewAdapter<VB, ListType>.BaseViewHolder>() {

    val itemList = arrayListOf<ListType>().toMutableList()

    protected abstract fun inflateViewBinding(
        parent: ViewGroup
    ): VB

    open fun VB.bind(item: ListType) { }
    open fun VB.bindWithPosition(item: ListType, position: Int) { }

    open fun buildDiffUtilCallback(
        oldList: List<ListType>,
        newList: List<ListType>
    ): DiffUtil.Callback? = null

    var position: Int? = null
    var specificItemPosition: Int = 0
    val size get() = itemList.size
    lateinit var itemView: View

    fun submitList(data: List<ListType>) {
        //check diffUtilBuilder is null or not
        val diffUtilCallback = buildDiffUtilCallback(itemList, data)
        if (diffUtilCallback != null) {
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            itemList.clear()
            itemList.addAll(data)
            diffResult.dispatchUpdatesTo(this)
        } else {
            itemList.clear()
            itemList.addAll(data)
            notifyDataSetChanged()
        }
    }

    inner class BaseViewHolder(val view: VB): RecyclerView.ViewHolder(view.root) {
        fun bind(item: ListType, position: Int) {
            this@BaseRecyclerViewAdapter.itemView = itemView
            view.bind(item)
            view.bindWithPosition(item, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = inflateViewBinding(parent)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        this@BaseRecyclerViewAdapter.position = position
        holder.bind(itemList[position], position)
    }

    override fun getItemCount(): Int = itemList.size
    
}