package com.aviraldg.trackshack

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.aviraldg.trackshack.models.Item
import com.aviraldg.trackshack.ui.recyclerview.MilestoneAdapter
import com.google.zxing.ResultPoint
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
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

        barcode_scanner.decodeContinuous(callback)
        activity.toolbar.title = "Triage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun doTriage(id: String) {
        val q = ParseQuery.getQuery(Item::class.java)
        q.getInBackground(id, object: GetCallback<Item> {
            override fun done(item: Item?, e: ParseException?) {
                if(item == null) return

                item.put("milestone", (activity as MainActivity).user?.getParseObject("milestone"))
                item.saveInBackground {
                    Toast.makeText(context, "Item triaged: ${item.name}", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()

        barcode_scanner.resume()
    }

    override fun onPause() {
        super.onPause()

        barcode_scanner.pause()
    }

    fun pause(view: View) {
        barcode_scanner.pause()
    }

    fun resume(view: View) {
        barcode_scanner.resume()
    }

    fun triggerScan(view: View) {
        barcode_scanner.decodeSingle(callback)
    }

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return barcode_scanner.onKeyDown(keyCode, event) || activity.onKeyDown(keyCode, event)
    }


    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text != null) {
                barcode_scanner.setStatusText(result.text)
                doTriage(result.text)
            }
            barcodePreview.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW))
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
        }
    }
}
