package com.newstoday

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.newstoday.fragment.NewsListFragment
import com.newstoday.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_channel.*

class ChannelActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    lateinit var articleViewModel: ArticleViewModel
//    val uidLiveData: LiveData<Int>?=MutableLiveData<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)
//        validateUserId()
        setSupportActionBar(toolbar)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter
        tabs.setupWithViewPager(container)

        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
//        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
//        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }


//    fun validateUserId() {
//        val uid = FirebaseAuth.getInstance().uid
//        if (uid == null) {
//            val intent = Intent(this, RegisterActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//            findViewById<View>(R.id.action_signout).visibility = View.GONE
//            findViewById<View>(R.id.action_signin).visibility = View.VISIBLE
//            findViewById<View>(R.id.action_bookmarks).visibility = View.GONE
//        } else {
//            findViewById<View>(R.id.action_signout).visibility = View.GONE
//            findViewById<View>(R.id.action_signin).visibility = View.VISIBLE
//            findViewById<View>(R.id.action_bookmarks).visibility = View.VISIBLE
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val uid = FirebaseAuth.getInstance().uid

        if (uid == null) {
            menu.add(0, 0, 0, "Sign In")
        }else{
            menu.add(0,1,0,"Sign Out")
        }
        menuInflater.inflate(R.menu.menu_channel, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("ChannelActivity","USERID= ${FirebaseAuth.getInstance().uid}")

        val id = item.itemId

        when (id) {
            R.id.action_bookmarks -> {
                if(FirebaseAuth.getInstance().uid==null){
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }else {
                    val intent = Intent(this, OfflineActivity::class.java)
                    startActivity(intent)
                }
            }
            0 -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            1-> {
                FirebaseAuth.getInstance().signOut()
                articleViewModel.clearBookmarks()
                val intent = Intent(this, ChannelActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: androidx.fragment.app.FragmentManager) :
        androidx.fragment.app.FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): androidx.fragment.app.Fragment {

            return NewsListFragment.newInstance(getPageTitle(position).toString())
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
