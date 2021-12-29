package com.example.helloenglish

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (val items:ArrayList<Vocabulary>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(), Filterable {

    interface OnItemClickListener{
        fun OnItemClick(holder: ViewHolder, view: View, data: Vocabulary, position: Int)
    }
    interface OnTTSClickListener{
        fun OnTTSClick(holder: ViewHolder, view: View, data: Vocabulary, position: Int)
    }


    var itemClickListener:OnItemClickListener?=null
    var ttsClickListener:OnTTSClickListener?=null
    var searchList : ArrayList<Vocabulary>?=null // 검색기능 위한 별도 리스트


    init{
        this.searchList = items
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val wordView : TextView = itemView.findViewById(R.id.wordView)
        val meanView : TextView = itemView.findViewById(R.id.meanView)
        val ttsBtn : ImageView = itemView.findViewById(R.id.ttsImg)
        init{
            meanView.visibility = View.GONE
            wordView.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, searchList!![adapterPosition], adapterPosition)
            }
            ttsBtn.setOnClickListener {
                ttsClickListener?.OnTTSClick(this, it, searchList!![adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return searchList!!.size
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.wordView.text = searchList!![position].word
        holder.meanView.text = searchList!![position].meaning
    }

    override fun getFilter(): Filter {
        return object : Filter()
        {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if(charString.isEmpty()){ // 입력한 것 없을때
                    searchList = items
                }
                else{
                    val filteredList = ArrayList<Vocabulary>()
                    for (row in items){ // 포함하는것 보여줌
                        if(row.word.toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(row)
                        }
                    }
                    searchList = filteredList
                }
                val filterResult = FilterResults()
                filterResult.values = searchList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                searchList = results.values as ArrayList<Vocabulary>
                notifyDataSetChanged()
            }
        }
    }

}