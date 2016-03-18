package com.aviraldg.trackshack.ui.recyclerview

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aviraldg.trackshack.MainActivity
import com.aviraldg.trackshack.R
import com.aviraldg.trackshack.models.Item
import com.aviraldg.trackshack.models.Milestone
import com.aviraldg.trackshack.models.User
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.item_item.view.*

class AssignedItemsAdapter(val activity: MainActivity) : RecyclerView.Adapter<AssignedItemsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item: Item) {
            itemView.item_name.text = item.name
        }
    }

    val items = arrayListOf<Item>()

    init {
        doQuery()
    }

    fun doQuery() {
        val m = activity.user?.getParseObject("milestone")
        val q = ParseQuery.getQuery(Item::class.java)
            .whereEqualTo("milestone", m)
        q.findInBackground { mutableList, parseException ->
            items.clear()
            items.addAll(mutableList)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: AssignedItemsAdapter.ViewHolder?, position: Int) {
        holder?.setItem(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AssignedItemsAdapter.ViewHolder {
        val li = LayoutInflater.from(parent?.context)
        val vh = ViewHolder(li.inflate(R.layout.item_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
