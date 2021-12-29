package com.example.helloenglish

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.helloenglish.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val myViewModel:MyViewModel by viewModels() // owner
    val textarr = arrayListOf<String>("영어사전", "단어추가", "단답형", "선택형")
    val iconarr = arrayListOf<Int>(R.drawable.ic_baseline_sort_by_alpha_24, R.drawable.ic_baseline_add_circle_outline_24,
        R.drawable.ic_baseline_border_color_24, R.drawable.ic_baseline_spellcheck_24)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startLoading()
        init()
        myViewModel.getLiveData().observe(this, Observer {

        })

    }

    private fun startLoading(){
        val intent = Intent(this, LoadingActivity::class.java)
        startActivity(intent)
    }

    private fun init(){
        // this = MainActivity (fragmentActivity 상속받음)
        binding.viewPager.adapter = MyFragStateAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
            tab, position ->
            tab.text = textarr[position]
            tab.setIcon(iconarr[position])
        }.attach()
    }

}