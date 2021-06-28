package com.smn.exam

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.smn.exam.adapter.MainAdapter
import com.smn.exam.adapter.ViewPagerAdapter
import com.smn.exam.databinding.ActivityMainBinding
import com.smn.exam.utils.MyViewModelFactory
import com.smn.exam.viewmodel.MainViewModel
import com.smn.exam.viewmodel.ViewItem


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var listViews: ArrayList<ViewItem>
    var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        viewModel = ViewModelProvider(this, MyViewModelFactory()).get(MainViewModel::class.java)

        var adapter = MainAdapter(viewModel.getAllRVData(), this)

        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.recyclerview.isNestedScrollingEnabled = true
        binding.recyclerview.adapter = adapter

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    binding.tabLayout.visibility = View.GONE
                } else {
                    // Scrolling down
                    binding.tabLayout.visibility = View.VISIBLE

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                if (listViews.contains(query)) {
                adapter.filter(query)
                //} else {
                //   Toast.makeText(this@MainActivity, "No Match found", Toast.LENGTH_LONG).show()
                //}
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter(newText)
                return false
            }
        })
    }

    fun initUI() {
        listViews = ArrayList<ViewItem>()
        listViews.add(ViewItem(1, R.drawable.ic_photo))
        listViews.add(ViewItem(2, R.drawable.ic_photo))
        listViews.add(ViewItem(3, R.drawable.ic_photo))
        listViews.add(ViewItem(4, R.drawable.ic_photo))
        listViews.add(ViewItem(5, R.drawable.ic_photo))

        val adapter = ViewPagerAdapter(this, listViews)
        binding.pager.adapter = adapter
        addPageIndicators()
        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                updatePageIndicator(position)
            }
        })
    }

    fun addPageIndicators() {
        binding.lytPageIndicator.removeAllViews()
        for (i in listViews.indices) {
            val view = ImageView(applicationContext)
            view.setImageResource(R.drawable.inactive)

            binding.lytPageIndicator.addView(view)
        }
        updatePageIndicator(currentIndex)
    }

    fun updatePageIndicator(position: Int) {
        var imageView: ImageView

        val lp =
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        lp.setMargins(5, 0, 5, 0)
        for (i in 0 until binding.lytPageIndicator.childCount) {
            imageView = binding.lytPageIndicator.getChildAt(i) as ImageView
            imageView.layoutParams = lp
            if (position == i) {
                imageView.setImageResource(R.drawable.active)
            } else {
                imageView.setImageResource(R.drawable.inactive)
            }
        }
    }
}