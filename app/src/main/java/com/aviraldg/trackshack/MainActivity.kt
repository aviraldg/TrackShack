package com.aviraldg.trackshack

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.activity_main.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var toggle by Delegates.notNull<ActionBarDrawerToggle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUi()
    }

    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar.setDisplayHomeAsUpEnabled(true)
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

        setToolbar(toolbar)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.main, PipelineFragment())
        ft.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when(item.itemId) {
            R.id.nav_dashboard -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            R.id.nav_pipeline -> {
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.main, PipelineFragment())
                ft.addToBackStack("Pipeline")
                ft.commit()
            }


            R.id.nav_assigned -> {
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.main, AssignedFragment())
                ft.addToBackStack("Assigned")
                ft.commit()
            }

            R.id.nav_triage -> {
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.main, TriageFragment())
                ft.addToBackStack("Triage")
                ft.commit()
            }

            R.id.nav_item_creator -> {
                val ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.main, ItemCreatorFragment())
                ft.addToBackStack("Item Creator")
                ft.commit()
            }
        }

        nav_view.setCheckedItem(item.itemId)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
