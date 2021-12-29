package com.example.helloenglish

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private var vocabularys = MutableLiveData<ArrayList<Vocabulary>>() // 영단어배열 전체 저장

    fun getLiveData() : MutableLiveData<ArrayList<Vocabulary>> {
        return vocabularys // 배열 전체 반환
    }
    fun setLiveData(vocs:ArrayList<Vocabulary>){
        vocabularys.value = vocs // 영단어 배열 다른 영단어배열로 갈아끼움
    }

}