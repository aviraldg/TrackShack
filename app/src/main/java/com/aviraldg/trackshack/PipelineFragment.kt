package com.aviraldg.trackshack

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.aviraldg.trackshack.models.Milestone
import com.aviraldg.trackshack.ui.recyclerview.MilestoneAdapter
import kotlinx.android.synthetic.main.fragment_pipeline.*
import kotlin.properties.Delegates

class PipelineFragment : Fragment() {
    val TAG = "PipelineFragment"
    var adapter by Delegates.notNull<MilestoneAdapter>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pipeline, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setToolbar(toolbar)

        adapter = MilestoneAdapter(activity as MainActivity)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)
        ItemTouchHelper(object: ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
                return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                        ItemTouchHelper.START or ItemTouchHelper.END)
            }

            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.onItemDismiss(viewHolder.adapterPosition)
            }

            override fun isLongPressDragEnabled(): Boolean {
                return true
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return true
            }
        }).attachToRecyclerView(recycler_view)

        fab.setOnClickListener { view ->
            val editText = EditText(context)
            editText.hint = "Name"

            val dialog = AlertDialog.Builder(context)
                .setView(editText)
                .setPositiveButton("Create", { dialog, button ->
                    with(Milestone()) {
                        name = editText.text.toString()
                        Log.i(TAG, "$name}")
                        saveEventually({
                            adapter.doQuery()
                        })
                    }
                })
                .setNegativeButton("Cancel", { dialog, button ->

                })
                .setCancelable(true)
                .setTitle("Create Milestone")
            dialog.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}