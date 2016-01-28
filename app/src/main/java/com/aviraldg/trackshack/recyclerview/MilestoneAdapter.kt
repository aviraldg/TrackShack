package com.aviraldg.trackshack.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aviraldg.trackshack.R
import com.aviraldg.trackshack.models.Milestone
import com.parse.ParseQuery
import kotlinx.android.synthetic.milestone_item.view.*

class MilestoneAdapter : RecyclerView.Adapter<MilestoneAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setMilestone(milestone: Milestone) {
            itemView.milestone_name.text = milestone.name
        }
    }

    val items = arrayListOf<Milestone>()

    init {
        doQuery()
    }

    fun doQuery() {
        val q = ParseQuery.getQuery(Milestone::class.java)
        q.setLimit(1000)

        q.findInBackground { mutableList, parseException ->
            items.clear()
            parseException ?: items.addAll(mutableList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val li = LayoutInflater.from(parent?.context)
        val vh = ViewHolder(li.inflate(R.layout.milestone_item, parent, false))
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setMilestone(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
