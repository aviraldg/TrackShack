package com.aviraldg.trackshack

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.*
import com.aviraldg.trackshack.ui.recyclerview.AssignedItemsAdapter
import com.aviraldg.trackshack.ui.recyclerview.MilestoneAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_pipeline.*
import kotlin.properties.Delegates

class AssignedFragment : Fragment() {
    val TAG = "PipelineFragment"
    var adapter by Delegates.notNull<AssignedItemsAdapter>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_assigned, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        (activity as MainActivity).setToolbar(toolbar)

        adapter = AssignedItemsAdapter(activity as MainActivity)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        activity.toolbar.title = "Assigned Items"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.assigned_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val i = Intent(context, LocationUpdateService::class.java)
        context.startService(i)
        return true
    }
}