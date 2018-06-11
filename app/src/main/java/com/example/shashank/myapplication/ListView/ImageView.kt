package com.example.shashank.myapplication.ListView

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.shashank.myapplication.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image_view.*


class ImageView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setTitle("hello")
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        val i = intent
        val url = i.getStringExtra("url")
        Picasso.with(this).load(url).fit().centerCrop()
                .into(imageView2)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==android.R.id.home)
        {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
