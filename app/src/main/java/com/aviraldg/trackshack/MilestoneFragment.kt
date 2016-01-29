package com.aviraldg.trackshack

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.aviraldg.trackshack.models.Milestone
import com.aviraldg.trackshack.models.User
import com.aviraldg.trackshack.ui.recyclerview.MilestoneAdapter
import com.aviraldg.trackshack.ui.recyclerview.MilestoneUsersAdapter
import com.parse.GetCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_milestone.*
import kotlin.properties.Delegates

class MilestoneFragment : Fragment() {
    val TAG = "MilestoneFragment"

    var recycler_adapter by Delegates.notNull<MilestoneUsersAdapter>()
    var adapter by Delegates.notNull<ArrayAdapter<String>>()
    var milestone: Milestone by Delegates.notNull()

    companion object {
        fun newInstance(id: String): MilestoneFragment {
            val mf = MilestoneFragment()
            val b = Bundle()
            b.putString("id", id)
            mf.arguments = b
            return mf
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_milestone, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setToolbar(toolbar)

        recycler_view.layoutManager = LinearLayoutManager(context)
        ItemTouchHelper(object: ItemTouchHelper.Callback() {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
                return true
            }

            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                return makeMovementFlags(0,
                        ItemTouchHelper.START or ItemTouchHelper.END)
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                recycler_adapter.onItemDismiss(viewHolder.adapterPosition)
            }


            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }
        }).attachToRecyclerView(recycler_view)
        adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1)
        milestone_username.setAdapter(adapter)
        milestone_username.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                milestone_username.error = null
                Log.i(TAG, s.toString())
                User.usernameLike(s.toString())
                    .findInBackground { mutableList, parseException ->
                        Log.i(TAG, "$mutableList")
                        adapter.clear()
                        adapter.addAll(mutableList.map {
                            it.username
                        })
                    }
            }

        })

        milestone_user_add.setOnClickListener {
            val q = ParseUser.getQuery()
            q.whereEqualTo("username", milestone_username.text.toString())
            q.findInBackground { mutableList, parseException ->
                if(mutableList.isEmpty()) {
                    milestone_username.error = "User not found"
                } else {
                    val user = mutableList.first()
                    user.put("milestone", milestone)
                    user.saveInBackground {
                        recycler_adapter.doQuery()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ParseQuery.getQuery(Milestone::class.java)
            .getInBackground(arguments.getString("id"), object: GetCallback<Milestone> {
                override fun done(m: Milestone, e: ParseException?) {
                    milestone = m

                    Log.i(TAG, "${m.name}")

                    activity?.runOnUiThread {
                        recycler_adapter = MilestoneUsersAdapter(milestone)
                        recycler_view.adapter = recycler_adapter
                    }
                }
            })
    }
}