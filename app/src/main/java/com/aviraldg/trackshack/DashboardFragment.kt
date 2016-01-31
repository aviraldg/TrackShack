package com.aviraldg.trackshack

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aviraldg.trackshack.ui.recyclerview.MilestoneAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlin.properties.Delegates

class DashboardFragment : Fragment() {
    val TAG = "DashboardFragment"
    var adapter by Delegates.notNull<MilestoneAdapter>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager.adapter = object: FragmentPagerAdapter(childFragmentManager) {
            val fs = arrayListOf(DashStatsFragment(), DashMapFragment())
            val ts = arrayListOf("Stats", "Map")
            override fun getItem(position: Int): Fragment? {
                return fs[position]
            }

            override fun getCount(): Int {
                return fs.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return ts[position]
            }

        }
        tabs.setupWithViewPager(viewpager)
        activity.toolbar.title = "Dashboard"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}