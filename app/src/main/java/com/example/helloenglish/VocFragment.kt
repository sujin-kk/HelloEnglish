package com.example.helloenglish

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class VocFragment : Fragment() {
    var data : ArrayList<Vocabulary> = ArrayList()
    var likeData : ArrayList<Vocabulary> = ArrayList()
    var isTtsReady = false
    val myViewModel:MyViewModel by activityViewModels()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : MyAdapter
    lateinit var tts : TextToSpeech

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_voc, container, false)
        //binding = FragmentVocBinding.inflate(layoutInflater)
        initData()
        init(view)
        initRecyclerView(view)
        initTTS()
        return view
    }

    private fun init(view:View){
        val searchEdit = view.findViewById<EditText>(R.id.searchEdit)
        searchEdit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.getFilter().filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun initTTS(){
        tts= TextToSpeech(requireContext(), TextToSpeech.OnInitListener {
            isTtsReady = true
            tts.language = Locale.US
        })
    }

    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }

    private fun initRecyclerView(view:View){
        data = myViewModel.getLiveData().value!!
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
        adapter = MyAdapter(data)
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener{
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun OnItemClick(holder: MyAdapter.ViewHolder, view: View, data: Vocabulary, position: Int) { // wordview가 클릭될때만 호출
                if(holder.meanView.visibility == View.GONE){
                    holder.meanView.visibility = View.VISIBLE
                }
                else {
                    holder.meanView.visibility = View.GONE
                }
            }
        }
        adapter.ttsClickListener = object : MyAdapter.OnTTSClickListener{
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun OnTTSClick(
                holder: MyAdapter.ViewHolder,
                view: View,
                data: Vocabulary,
                position: Int
            ) {
                if(isTtsReady)
                    tts.speak(data.word, TextToSpeech.QUEUE_ADD, null, null)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun initData(){
        val scan = Scanner(resources.openRawResource(R.raw.words)) // scan 객체 선언
        while(scan.hasNextLine()){ // 행 단위로 읽기
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(Vocabulary(word, meaning))
        }
        myViewModel.setLiveData(data)
    }


}