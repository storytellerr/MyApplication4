package com.example.shashank.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.shashank.myapplication.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        createaccount.setOnClickListener(){
            if(emailId.text.isEmpty()||passwo.text.isEmpty())
                Toast.makeText(this,"please fill all entities",Toast.LENGTH_LONG).show()
            else{
                auth.createUserWithEmailAndPassword(emailId.text.toString(), passwo.text.toString()).
                        addOnCompleteListener { task: Task<AuthResult> ->
                            if (task.isSuccessful) {
                               startActivity(Intent(this,StartUp::class.java))
                                finish()
                            }
                            else{
                                Toast.makeText(this,"error",Toast.LENGTH_LONG).show()

                            }

                        }
            }
        }
    }
}
