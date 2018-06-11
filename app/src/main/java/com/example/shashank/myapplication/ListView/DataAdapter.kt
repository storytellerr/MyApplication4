package com.example.shashank.myapplication.ListView

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shashank.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.*




class DataAdapter (private val dataList : ArrayList<ModelObject>, private val listener : Listener) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    interface Listener {

        fun onItemClick(data : ModelObject)
    }

   // private val colors : Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(dataList[position], listener, position)
    }

    override fun getItemCount(): Int = dataList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(dataModel: ModelObject, listener: Listener, position: Int) {

            itemView.tv_name.text = dataModel.country
            itemView.tv_rank.text = (dataModel.rank).toString()
            itemView.tv_population.text = dataModel.population
            //itemView.imageView           =dataModel.flag
            Picasso.with(itemView.context).load(dataModel.flag).fit().centerCrop()
                    .into(itemView.imageView)


            itemView.setOnClickListener{
                val i = Intent(itemView.context, ImageView::class.java)
                i.putExtra("url", dataModel.flag)
                startActivity(itemView.context,i,null)
            }
        }
    }
}