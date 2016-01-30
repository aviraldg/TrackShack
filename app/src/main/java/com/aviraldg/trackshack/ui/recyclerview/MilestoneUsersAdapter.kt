package com.aviraldg.trackshack.ui.recyclerview

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aviraldg.trackshack.R
import com.aviraldg.trackshack.models.Milestone
import com.aviraldg.trackshack.models.User
import com.aviraldg.trackshack.models.image
import com.aviraldg.trackshack.util.md5
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.user_item.view.*
import org.json.JSONObject

class MilestoneUsersAdapter(val milestone: Milestone) : RecyclerView.Adapter<MilestoneUsersAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setUser(user: ParseUser) {
            itemView.user_username.text = "${user.username} (${user.email})"
            itemView.user_image.setImageURI(user.image)
        }
    }

    val items = arrayListOf<ParseUser>()

    init {
        doQuery()
    }

    fun doQuery() {
        val q = User.assignedToMilestone(milestone)
        q.setLimit(1000)

        q.findInBackground { mutableList, parseException ->
            Log.i("MUA", "$mutableList")
            items.clear()
            parseException ?: items.addAll(mutableList)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.setUser(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        val li = LayoutInflater.from(parent?.context)
        val vh = ViewHolder(li.inflate(R.layout.user_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun onItemDismiss(adapterPosition: Int) {
        val item = items[adapterPosition]
        item.put("milestone", JSONObject.NULL)
        item.saveInBackground()

        items.removeAt(adapterPosition)
    }

}