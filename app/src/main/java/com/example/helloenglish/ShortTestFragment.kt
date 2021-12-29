package com.example.helloenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class ShortTestFragment : Fragment() {
    var allData: ArrayList<Vocabulary> = ArrayList() // 100개 들어잇음
    val random = Random()
    val myViewModel: MyViewModel by activityViewModels()
    lateinit var testData : Vocabulary // 문제

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val view = inflater.inflate(R.layout.fragment_short_test, container, false)
        init(view)
        return view
    }

    private fun init(view:View){
        allData = myViewModel.getLiveData().value!! // 전체 데이터 넣기
        testData = nextTest(view, allData)

        val targetMean = view.findViewById<TextView>(R.id.targetMean) // 문제 뜻
        val inputEng = view.findViewById<EditText>(R.id.shortEdit) // 사용자 입력 영단어
        val resultView = view.findViewById<TextView>(R.id.shortResultView) // 결과 출력
        val okBtn = view.findViewById<Button>(R.id.okBtn)

        okBtn.setOnClickListener {
            if(inputEng.text.isNotEmpty()) {
                if (inputEng.text.toString() == testData.word) {
                    resultView.text = "O"

                } else {
                    resultView.text = "X"

                }
                inputEng.text.clear()
                testData = nextTest(view, allData)
            }
            else
                Toast.makeText(activity?.applicationContext, "정답을 입력하세요.", Toast.LENGTH_SHORT).show()
        }


    }

    fun nextTest(view:View, data:ArrayList<Vocabulary>) : Vocabulary{
        val testData : Vocabulary
        val targetMean = view.findViewById<TextView>(R.id.targetMean) // 문제 뜻

        val index = random.nextInt(data.size) // 0~99
        testData = data[index] // 단어 하나 가져옴

        targetMean.text = testData.meaning // 문제 뜻 부착
        return testData
    }

}