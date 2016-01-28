package com.aviraldg.trackshack

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.aviraldg.trackshack.models.Milestone
import com.aviraldg.trackshack.recyclerview.MilestoneAdapter
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

        adapter = MilestoneAdapter()
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

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