package com.aviraldg.trackshack

import android.os.Bundle
import android.support.v4.app.Fragment
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
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.Parse
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.fragment_dashmap.*
import java.util.*
import kotlin.properties.Delegates

class DashMapFragment : Fragment() {
    val TAG = "DashMapFragment"
    var adapter by Delegates.notNull<MilestoneAdapter>()
    var mapFragment by Delegates.notNull<SupportMapFragment>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_dashmap, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val opt = GoogleMapOptions()
        opt.mapToolbarEnabled(true)
        opt.zoomControlsEnabled(true)
        opt.zoomGesturesEnabled(true)
        mapFragment = SupportMapFragment.newInstance(opt)
        val ft = childFragmentManager.beginTransaction()
        ft.replace(R.id.map, mapFragment)
        ft.commit()
        mapFragment.getMapAsync {
            it.isMyLocationEnabled = true
            updateMap(it)
        }
    }

    private fun updateMap(gmap: GoogleMap) {
        val q = ParseQuery.getQuery(Milestone::class.java)
            .whereEqualTo("owner", (activity as MainActivity).user)

        val q2 = ParseUser.getQuery()
            .whereMatchesQuery("milestone", q)
            .setLimit(1000)

        q2.findInBackground { mutableList, parseException ->
            mutableList.forEach {
                val loc = it.getParseGeoPoint("location") ?: return@forEach
                val marker = MarkerOptions()
                    .position(LatLng(loc.latitude, loc.longitude))
                    .title(it.username)
                gmap.addMarker(marker)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}