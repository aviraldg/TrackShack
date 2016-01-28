package com.aviraldg.trackshack.ui.recyclerview

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aviraldg.trackshack.MainActivity
import com.aviraldg.trackshack.MilestoneFragment
import com.aviraldg.trackshack.R
import com.aviraldg.trackshack.models.Milestone
import com.parse.ParseQuery
import kotlinx.android.synthetic.milestone_item.view.*
import java.util.*

class MilestoneAdapter(val activity: MainActivity) : RecyclerView.Adapter<MilestoneAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun setMilestone(milestone: Milestone) {
            itemView.milestone_name.text = milestone.name
            itemView.milestone_index.text = milestone.order.toString()
            itemView.setOnClickListener {
                with(activity) {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.main, MilestoneFragment.newInstance(milestone.objectId))
                    ft.addToBackStack("Milestone")
                    ft.commit()
                }
            }
        }
    }

    val items = arrayListOf<Milestone>()

    init {
        doQuery()
    }

    fun doQuery() {
        val q = ParseQuery.getQuery(Milestone::class.java)
        q.orderByAscending("order")
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

    fun reindex() {
        items.forEachIndexed { i, milestone ->
            milestone.order = i + 1
            milestone.saveInBackground()
            notifyItemChanged(i)
        }
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition..toPosition - 1) {
                Collections.swap(items, i, i + 1);
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        reindex()
    }

    fun onItemDismiss(adapterPosition: Int) {
        items[adapterPosition].deleteInBackground()
        items.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        reindex()
    }
}
