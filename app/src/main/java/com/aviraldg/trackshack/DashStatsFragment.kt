package com.aviraldg.trackshack

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.print.PrintHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aviraldg.trackshack.models.Item
import com.aviraldg.trackshack.models.Milestone
import com.aviraldg.trackshack.ui.recyclerview.MilestoneAdapter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashstats.*
import kotlinx.android.synthetic.main.fragment_item_creator.*
import net.glxn.qrgen.android.QRCode
import java.util.*
import kotlin.properties.Delegates

class DashStatsFragment : Fragment() {
    val TAG = "DashStatsFragment"
    var adapter by Delegates.notNull<MilestoneAdapter>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_dashstats, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateMilestoneInfo()
    }

    private fun updateMilestoneInfo() {
        val q = ParseQuery.getQuery(Milestone::class.java)
                .whereEqualTo("owner", (activity as MainActivity).user)
        q.findInBackground { mutableList, parseException ->
            if(view == null) return@findInBackground
            val q2 = ParseQuery.getQuery(Item::class.java)
                    .whereMatchesQuery("milestone", q)
            val milestoneMap = mutableList.associate {
                Pair(it.objectId, it)
            }

            val items = q2.find()
            val countMap = linkedMapOf<String, Int>()
            items.forEach {
                Log.i(TAG, it.name)
                val mKey = it.getParseObject("milestone").objectId
                if(!countMap.containsKey(mKey)) {
                    countMap[mKey] = 1
                } else {
                    countMap[mKey] = (countMap[mKey] ?: 0) + 1
                }
            }

            val keys = milestoneMap.keys

            val pds = PieDataSet(keys.mapIndexed { i, it ->
                Entry((countMap[it] ?: 0).toFloat(), i)
            }, "#")
            Log.i(TAG, Arrays.toString(pds.yVals.toTypedArray()))
            val pd = PieData(keys.map {
                milestoneMap[it]?.name ?: "Unknown"
            }, pds)
            pds.colors = ColorTemplate.JOYFUL_COLORS.toList()
            pds.sliceSpace = 2f
            pds.selectionShift = 5f
            chart.holeRadius = 50f
            chart.data = pd
            chart.invalidate()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}