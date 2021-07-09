package com.example.sendbirdapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.sendbirdapp.databinding.ActivityMainBinding
import com.example.sendbirdapp.ui.bookmark.BookmarkFragment
import com.example.sendbirdapp.ui.new.NewFragment
import com.example.sendbirdapp.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.navView.menu.getItem(position).isChecked = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewpager.adapter = ActivityFragmentStateAdapter(this)
        binding.viewpager.registerOnPageChangeCallback(pageChangeCallback)
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_new -> {
                    binding.viewpager.currentItem = 0
                }
                R.id.navigation_search -> {
                    binding.viewpager.currentItem = 1
                }
                R.id.navigation_bookmark -> {
                    binding.viewpager.currentItem = 2
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}

class ActivityFragmentStateAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> NewFragment()
            1 -> SearchFragment()
            else -> BookmarkFragment()
        }
    }
}