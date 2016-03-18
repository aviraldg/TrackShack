package com.aviraldg.trackshack

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.aviraldg.trackshack.models.image
import com.aviraldg.trackshack.util.animate
import com.parse.ParseUser
import com.parse.ui.ParseLoginBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.nav_header_main.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var toggle by Delegates.notNull<ActionBarDrawerToggle>()
    var user: ParseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAuth()
        initUi()
    }

    private val RC_AUTH: Int = 0

    private fun checkAuth() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
//        user = ParseUser.getCurrentUser()
//        if(user == null) {
//            val pl = ParseLoginBuilder(this)
//                    .setParseLoginEnabled(true)
//                    .setAppLogo(R.mipmap.ic_logo)
//            val i = pl.build()
//            startActivityForResult(i, RC_AUTH)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_AUTH) {
            if(ParseUser.getCurrentUser() == null) {
                finish()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toggle.setToolbarNavigationClickListener {
            if(drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                drawer_layout.openDrawer(nav_view)
            }
        }
        drawer_layout.setDrawerListener(toggle)
        toggle.syncState()
    }

    fun initUi() {
        nav_view.setNavigationItemSelectedListener(this)

        with(nav_view.getHeaderView(0)) {
            user_username.text = user?.username ?: "unknown"
            user_email.text = user?.email ?: "unknown"
            user_image.setImageURI(user?.image)
        }

        setToolbar(toolbar)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main, DashboardFragment())
        ft.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when(item.itemId) {
            R.id.nav_dashboard -> {
                val ft = supportFragmentManager.beginTransaction()
                animate(ft)
                ft.replace(R.id.main, DashboardFragment())
                ft.addToBackStack("Dashboard")
                ft.commit()
            }

            R.id.nav_pipeline -> {
                val ft = supportFragmentManager.beginTransaction()
                animate(ft)
                ft.replace(R.id.main, PipelineFragment())
                ft.addToBackStack("Pipeline")
                ft.commit()
            }


            R.id.nav_assigned -> {
                val ft = supportFragmentManager.beginTransaction()
                animate(ft)
                ft.replace(R.id.main, AssignedFragment())
                ft.addToBackStack("Assigned")
                ft.commit()
            }

            R.id.nav_triage -> {
                val ft = supportFragmentManager.beginTransaction()
                animate(ft)
                ft.replace(R.id.main, TriageFragment())
                ft.addToBackStack("Triage")
                ft.commit()
            }

            R.id.nav_item_creator -> {
                val ft = supportFragmentManager.beginTransaction()
                animate(ft)
                ft.replace(R.id.main, ItemCreatorFragment())
                ft.addToBackStack("Item Creator")
                ft.commit()
            }
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
