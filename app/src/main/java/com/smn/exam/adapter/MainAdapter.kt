package com.smn.exam.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.smn.exam.R
import com.smn.exam.model.DataModel
import kotlinx.android.synthetic.main.activity_main_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(private val items: ArrayList<DataModel>, private val context: AppCompatActivity) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var tempNameVersionList = ArrayList(items)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_main_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: DataModel) {
            with(itemView) {
                name.text = item.name
                Log.v("URL", "URLS is -- " + item.url)
                if (item.url.isNotEmpty()) {
                    Glide
                        .with(itemView)
                        .load(item.url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.active)
                        .error(R.drawable.inactive)
                        .into(itemView.imageViews);
                }
            }
        }
    }

    //Function to set data according to Search Keyword in ListView
    fun filter(text: String?) {


        //Our Search text
        val text = text!!.toLowerCase(Locale.getDefault())
        items.clear()

        if (text.isEmpty()) {
            items.addAll(tempNameVersionList)
        } else {
            for (i in 0 until tempNameVersionList.size) {

                val model = tempNameVersionList[i];
                if (model.name!!.toLowerCase(Locale.getDefault()).contains(text)) {

                    items.add(tempNameVersionList[i])
                }

            }
        }

        //This is to notify that data change in Adapter and Reflect the changes.
        notifyDataSetChanged()


    }
}