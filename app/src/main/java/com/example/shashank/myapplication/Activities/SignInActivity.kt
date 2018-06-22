package com.example.shashank.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.shashank.myapplication.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        login.setOnClickListener() {
            if (email.text.length >= 0) {
                if (password.text.length >= 0) {
                    auth = FirebaseAuth.getInstance()
                    auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            val editor = getSharedPreferences("mypref",0).edit()
                            editor.putString("loged_in", "true")
                            editor.apply()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "inccorect id / password", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                 else {
                    Toast.makeText(this, "please enter a password", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "please enter a email", Toast.LENGTH_LONG).show()
            }
        }
    }
}
