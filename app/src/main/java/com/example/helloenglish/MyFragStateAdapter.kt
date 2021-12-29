package com.example.helloenglish

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragStateAdapter(fragmentActivity:FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->VocFragment()
            1->PlusFragment()
            2->ShortTestFragment()
            3->TestFragment()
            else -> VocFragment()
        }
    }





}