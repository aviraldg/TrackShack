package com.aviraldg.trackshack

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.print.PrintHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aviraldg.trackshack.models.Item
import com.aviraldg.trackshack.ui.recyclerview.MilestoneAdapter
import kotlinx.android.synthetic.main.fragment_item_creator.*
import net.glxn.qrgen.android.QRCode
import kotlin.properties.Delegates

class ItemCreatorFragment : Fragment() {
    val TAG = "ItemCreatorFragment"
    var adapter by Delegates.notNull<MilestoneAdapter>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_item_creator, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).setToolbar(toolbar)

        item_create.setOnClickListener {
            val i = Item()
            i.name = item_name.text.toString()
            i.cemail = item_cemail.text.toString()
            i.cphone = item_cphone.text.toString()
            i.desc = item_description.text.toString()
            i.saveInBackground {
                val bitmap = QRCode.from(i.objectId).bitmap()
                val ph = PrintHelper(activity)
                ph.scaleMode = PrintHelper.SCALE_MODE_FIT
                ph.printBitmap("TrackShack: ${i.name}}", bitmap)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}