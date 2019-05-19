package com.newstoday

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.newstoday.fragment.PlaceholderFragment
import kotlinx.android.synthetic.main.activity_channel.*

class ChannelActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter
        tabs.setupWithViewPager(container)
//        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
//        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_channel, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): androidx.fragment.app.Fragment {

            return PlaceholderFragment.newInstance(getPageTitle(position).toString())
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> "business"
                1 -> "technology"
                2 -> "entertainment"
                else -> ""
            }

        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */

}
