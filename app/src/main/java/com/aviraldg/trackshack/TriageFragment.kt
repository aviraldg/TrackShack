package com.aviraldg.trackshack

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aviraldg.trackshack.models.Item
import com.aviraldg.trackshack.ui.recyclerview.MilestoneAdapter
import com.google.zxing.integration.android.IntentIntegrator
import com.parse.GetCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_triage.*
import kotlin.properties.Delegates

class TriageFragment : Fragment() {
    val TAG = "TriageFragment"
    var adapter by Delegates.notNull<MilestoneAdapter>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_triage, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        triage_scan.setOnClickListener {
            IntentIntegrator.forSupportFragment(this).initiateScan()
        }

        activity.toolbar.title = "Triage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val r = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val c = r.contents ?: return
        val q = ParseQuery.getQuery(Item::class.java)
        q.getInBackground(c, object: GetCallback<Item> {
            override fun done(item: Item?, e: ParseException?) {
                val q = ParseUser.getQuery()
                q.whereEqualTo("username", "testing")
                val users = q.find()
                val u = users.first()
                item?.put("milestone", u.getParseObject("milestone"))
                item?.saveInBackground {
                    Toast.makeText(context, "Item triaged", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}