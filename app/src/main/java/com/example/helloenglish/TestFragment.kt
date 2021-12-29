package com.example.helloenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class TestFragment : Fragment() {
    var allData: ArrayList<Vocabulary> = ArrayList() // 100개 들어잇음
    var testData: ArrayList<Vocabulary> = ArrayList() // 문제 1 + 보기 3 = 4개 voc로 구성
    val random = Random()
    val myViewModel: MyViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_test, container, false)
        init(view)
        return view
    }


    private fun init(view: View) {
        allData = myViewModel.getLiveData().value!!
        testData = nextTest(view, allData)

        val result = view.findViewById<TextView>(R.id.resultView)
        val mean_1 = view.findViewById<Button>(R.id.mean_1)
        val mean_2 = view.findViewById<Button>(R.id.mean_2)
        val mean_3 = view.findViewById<Button>(R.id.mean_3)
        val mean_4 = view.findViewById<Button>(R.id.mean_4)

        mean_1.setOnClickListener {
            if(testData[0].meaning.equals(mean_1.text)){ // 정답을 누르면
               result.text = "O"
            }
            else{
                result.text = "X"
            }
            testData = nextTest(view, allData)
        }
        mean_2.setOnClickListener {
            if(testData[0].meaning.equals(mean_2.text)){ // 정답을 누르면
                result.text = "O"
            }
            else{
                result.text = "X"
            }
            testData = nextTest(view, allData)
        }
        mean_3.setOnClickListener {
            if(testData[0].meaning.equals(mean_3.text)){ // 정답을 누르면
                result.text = "O"
            }
            else{
                result.text = "X"
            }
            testData = nextTest(view, allData)
        }
        mean_4.setOnClickListener {
            if(testData[0].meaning.equals(mean_4.text)){ // 정답을 누르면
                result.text = "O"
            }
            else{
                result.text = "X"
            }
            testData = nextTest(view, allData)
        }

    }

    fun nextTest(view: View, data: ArrayList<Vocabulary>) : ArrayList<Vocabulary> {
        val testData : ArrayList<Vocabulary> = arrayListOf() // testData[0] ~ testData[3]
        val targetWord = view.findViewById<TextView>(R.id.targetWord)
        val mean_1 = view.findViewById<Button>(R.id.mean_1)
        val mean_2 = view.findViewById<Button>(R.id.mean_2)
        val mean_3 = view.findViewById<Button>(R.id.mean_3)
        val mean_4 = view.findViewById<Button>(R.id.mean_4)

        for (i in 0..4 step (1)) { // 4번 동안
            var index = random.nextInt(data.size) // 0 ~ 99
            testData.add(data[index])
        }

        // 문제 text 부착
        targetWord.text = testData[0].word
        // 정답 testData[0].meaning
        var index = random.nextInt(4) // 0~3 -> 2 정답 넣을 인덱스
        if (index == 0) {
            mean_1.text = testData[0].meaning
            mean_2.text = testData[1].meaning
            mean_3.text = testData[2].meaning
            mean_4.text = testData[3].meaning
        }

        else if (index == 1) {
            mean_1.text = testData[1].meaning
            mean_2.text = testData[0].meaning
            mean_3.text = testData[2].meaning
            mean_4.text = testData[3].meaning
        }

        else if (index == 2) {
            mean_1.text = testData[1].meaning
            mean_2.text = testData[2].meaning
            mean_3.text = testData[0].meaning
            mean_4.text = testData[3].meaning
        }

        else {
            mean_1.text = testData[1].meaning
            mean_2.text = testData[2].meaning
            mean_3.text = testData[3].meaning
            mean_4.text = testData[0].meaning
        }
        return testData
    }
}