package com.example.helloenglish

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


@Suppress("DEPRECATION")
class PlusFragment : Fragment() {
    var plusedData : ArrayList<Vocabulary> = ArrayList()
    val myViewModel:MyViewModel by activityViewModels()
    var isSame = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        var view = inflater.inflate(R.layout.fragment_plus, container, false)
        init(view)
        return view
    }

    private fun init(view:View){
        plusedData = myViewModel.getLiveData().value!!
        val addBtn = view.findViewById<Button>(R.id.addBtn)
        val wordEdit = view.findViewById<EditText>(R.id.wordEdit)
        val meanEdit = view.findViewById<EditText>(R.id.meanEdit)
        addBtn.setOnClickListener {
            if (wordEdit.text.isNotEmpty() && meanEdit.text.isNotEmpty()) { // 에딧텍스트가 비어있지 않을때만 추가 가능
                for(i in 0 until plusedData.size step(1)){
                    if(wordEdit.text.toString() == plusedData[i].word) {
                        Toast.makeText(activity?.applicationContext, "이미 존재하는 단어입니다.", Toast.LENGTH_SHORT).show()
                        isSame = true
                        break
                    }
                }
                if(!isSame){
                    plusedData.add(Vocabulary(wordEdit.text.toString(), meanEdit.text.toString()))
                    Toast.makeText(activity?.applicationContext, "단어가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    myViewModel.setLiveData(plusedData)
                    clearEditText(wordEdit)
                    clearEditText(meanEdit)
                }

            }
            else{
                Toast.makeText(activity?.applicationContext, "단어와 뜻을 모두 입력하세요.", Toast.LENGTH_SHORT).show()
                clearEditText(wordEdit)
                clearEditText(meanEdit)
            }
        }


    }

    fun clearEditText(editText:EditText){
       editText.text.clear()
    }

}