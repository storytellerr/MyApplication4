package com.example.shashank.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.shashank.myapplication.R
import kotlinx.android.synthetic.main.activity_start_up.*

class StartUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_up)
        signIn.setOnClickListener(){
            startActivity(Intent(this,SignInActivity::class.java))
        }
        signUp.setOnClickListener(){
            startActivity(Intent(this,SignUpActivity::class.java))
        }

    }

}
